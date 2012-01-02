/*
 * Copyright (C) 2011-2011 Inaki Ortiz de Landaluce Saiz
 * 
 * This program is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program. If not, see 
 * <http://www.gnu.org/licenses/>
 */
package org.cetus.astro;

import java.util.Calendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * Implementation of a low accuracy algorithm to calculate the sun position as
 * described by Jean Meeus on Astronomical Algorithms, Willmann-Bell, 2nd Ed.,
 * 2005 (ISBN 978-0943396613).
 * 
 * The algorithm calculates the sun position for the given date assuming purely
 * elliptical motion of the Earth (Keplerian ellipse). Hence, perturbations by
 * the Moon (others than nutation) and the planets are neglected. Corrections by
 * aberration and atmosphere refraction are applied.
 * 
 * This leads to an accuracy of 0.01 degree, approximately fifty times smaller
 * than the sun diameter (~0.5416 degrees).
 * 
 * @author Inaki Ortiz de Landaluce Saiz
 */
public class SunPositionAlgorithmLowRes extends SunPositionAlgorithm {

  private Logger log = Logger.getRootLogger();

  /**
   * Creates an instance of a SunPositionAlgorithm to calculate the sun position
   * for a given date and assuming calendar is Gregorian.
   * 
   * @param year
   *          year
   * @param month
   *          month of the year (first month is 1)
   * @param day
   *          day of the month (first day is 1)
   * @param hour
   *          hour of the day for the 24-hour clock
   * @param minute
   *          minute value within the hour
   * @param second
   *          second value within the minute
   * @param zone
   *          datetime's zone
   * @param longitude
   *          geographical longitude in degrees of the observer's location
   * @param latitude
   *          geographical latitude in degrees of the observer's location
   */
  public SunPositionAlgorithmLowRes(int year, int month, int day, int hour,
      int minute, int second, TimeZone zone, double longitude, double latitude) {
    super(year, month, day, hour, minute, second, zone, longitude, latitude);
  }

  /**
   * Creates an instance of a SunPositionAlgorithm to calculate the sun position
   * in different resolutions assuming date time zone is GMT+0.
   * 
   * @param year
   *          year
   * @param month
   *          month of the year (first month is 1)
   * @param day
   *          day of the month (first day is 1)
   * @param hour
   *          hour of the day for the 24-hour clock
   * @param minute
   *          minute value within the hour
   * @param second
   *          second value within the minute
   * @param longitude
   *          geographical longitude in degrees of the observer's location
   * @param latitude
   *          geographical latitude in degrees of the observer's location
   */
  public SunPositionAlgorithmLowRes(int year, int month, int day, int hour,
      int minute, int second, double longitude, double latitude) {
    super(year, month, day, hour, minute, second, longitude, latitude);
  }

  /**
   * Calculates the sun position for the given date assuming purely elliptical
   * motion of the Earth.
   * 
   * @return the sun position
   */
  @Override
  public SunPosition calculateSunPosition() {
    log.debug("Into SunPositionAlgorithmLowRes.calculateSunPosition");

    // calculate julian day
    Calendar calendar = DateTimeUtils.parseCalendar(this.year, this.month,
        this.day, this.hour, this.minute, this.second, 0, this.timeZone);
    JulianDay jd = new JulianDay(calendar);
    log.debug("Date=" + jd.getJD() + "JD");

    // calculate time in Julian centuries from epoch J2000.0
    double t = jd.getTimeFromJ2000();
    double t2 = t * t;
    log.debug("Julian centuries since J2000.0=" + t);

    // calculate geometric mean longitude of the Sun referred to the mean
    // equinox of the date o(t^3)
    double mlon = 280.46646 + 36000.76983 * t + 0.0003032 * t2;
    log.debug("Mean longitude=" + mlon + " degrees");
    // calculate the mean anomaly o(t^3)
    double mano = 357.52911 + 35999.05029 * t - 0.0001537 * t2;
    double manoRadians = Math.toRadians(mano);
    log.debug("Mean anomaly=" + mano + " degrees");

    // use sun's equation of the center to calculate true geometric longitude
    double c = (1.914602 - 0.004817 * t - 0.000014 * t2)
        * Math.sin(manoRadians) + (0.019993 - 0.000101 * t)
        * Math.sin(2 * manoRadians) + 0.000289 * Math.sin(3 * manoRadians);
    log.debug("Center=" + c + " degrees");
    double tlon = mlon + c;
    log.debug("True Longitude=" + tlon + " degrees");

    // calculate the apparent longitude, taking nutation in longitude and
    // aberration into account
    double aberration = -0.00569;
    double nutationLon = new Nutation(t).getDeltaLongitude() / 3600;
    double lambda = tlon + aberration + nutationLon;
    double lambdaRadians = Math.toRadians(lambda);
    log.debug("Apparent longitude=" + lambda + " degrees = "
        + AngleUtils.normalizeAngle(lambda, 0, 360) + " degrees");

    // calculate the true obliquity of the eclipse corrected for nutation o(t^4)
    double epsilon = EclipticObliquity.calculateTrueObliquity(t);
    double epsilonRadians = Math.toRadians(epsilon);
    log.debug("Epsilon=" + epsilon + " degrees");

    // convert from ecliptic to equatorial assuming latitude is zero (valid for
    // low accuracy calculation only)
    double rasRadians = Math.atan2(
        (Math.sin(lambdaRadians) * Math.cos(epsilonRadians)),
        Math.cos(lambdaRadians));
    double decRadians = Math.asin(Math.sin(epsilonRadians)
        * Math.sin(lambdaRadians));
    log.debug("Ras=" + Math.toDegrees(rasRadians) + " degrees" + " = "
        + AngleUtils.formatDegToHms(Math.toDegrees(rasRadians), 0, 360));
    log.debug("Dec=" + Math.toDegrees(decRadians) + " degrees" + " = "
        + AngleUtils.formatDegToDms(Math.toDegrees(decRadians), -180, 180));

    // convert sun coordinates from equatorial to horizontal
    double hourAngle = SiderealTime.calculateApparentSiderealTime(jd)
        - longitudeInDegrees - Math.toDegrees(rasRadians);
    double hourAngleRadians = Math.toRadians(hourAngle);
    double latRadians = Math.toRadians(this.latitudeInDegrees);

    double azimuth = Math.atan2(
        Math.sin(hourAngleRadians),
        (Math.cos(hourAngleRadians) * Math.sin(latRadians) - Math
            .tan(decRadians) * Math.cos(latRadians)));
    double alt = Math.asin(Math.sin(latRadians) * Math.sin(decRadians)
        + Math.cos(latRadians) * Math.cos(decRadians)
        * Math.cos(hourAngleRadians));
    
    // correct altitude from atmospheric refraction
    double altCorrected = new AtmosphericRefraction(Math.toDegrees(alt))
        .getApparentAltitude();
    
    return new SunPosition(Math.toDegrees(azimuth), altCorrected);
  }
}
