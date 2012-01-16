package org.cetus.astro.coords;

import static org.junit.Assert.*;

import org.cetus.astro.JulianDay;
import org.cetus.astro.SiderealTime;
import org.junit.Test;

public class EquatorialCoordinatesTest {

  @Test
  public final void testToHorizontal() {
    // coordinates of Venus on 1897 April 10 at 19h 21m 00s UT at U.S. Naval
    // Observatory at Washington D.C. (longitude=+77d03'56",latitude=+38d55'17")
    double rasHours = 23 + 9 / 60.0 + 16.641 / (60.0 * 60.0);
    double decDeg = -(6 + 43 / 60.0 + 11.61 / (60.0 * 60.0));
    JulianDay jd = new JulianDay(1987, 4, 10, 19, 21, 0, 0);
    double siderealTime = SiderealTime.calculateApparentSiderealTime(jd);
    double geoLon = 77 + 3 / 60.0 + 56 / (60d * 60.0);
    double geoLat = 38 + 55 / 60.0 + 17 / (60.0 * 60.0);

    EquatorialCoordinates eq = new EquatorialCoordinates(rasHours, decDeg);
    HorizontalCoordinates h = eq.toHorizontal(siderealTime, geoLon, geoLat);

    assertEquals(68.0337, h.getAzimuth(), 0.0002);
    assertEquals(15.1249, h.getAltitude(), 0.0001);
  }
}
