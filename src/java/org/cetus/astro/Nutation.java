/*
 * Copyright (C) 2011-2012 Inaki Ortiz de Landaluce Saiz
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

import org.apache.log4j.Logger;

/**
 * @author Inaki Ortiz de Landaluce Saiz
 *
 */
public abstract class Nutation {

  protected Logger log = Logger.getRootLogger();
  
  protected double deltaLon;
  protected double deltaEps;
  
  /**
   * Creates a Nutation instance for a given julian day. The julian day value
   * may be standard Julian Day (JD, referred to Universal Time) or Julian
   * Ephemeris Day (JDE, referred to) Dynamical/Terrestrial Time).
   * 
   * @param jd
   *          the julian day value
   */
  public Nutation(double jd) {
    calculateNutation(jd);
  }
  
  /**
   * Returns the delta component along the ecliptic due to nutation 
   * @return the nutation in longitude
   */
  public double getDeltaLongitude() {
    return deltaLon;
  }

  /**
   * Returns the delta component perpendicular to the ecliptic due to nutation 
   * @return the nutation in obliquity
   */  
  public double getDeltaObliquity() {
    return deltaEps;
  }
  
  protected abstract void calculateNutation(double jd);
}