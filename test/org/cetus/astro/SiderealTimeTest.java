package org.cetus.astro;

import static org.junit.Assert.*;

import org.cetus.astro.util.AngleUtils;
import org.junit.Test;

public class SiderealTimeTest {

  @Test
  public final void testCalculateApparentSiderealTime() {
    JulianDay jd = new JulianDay(1987, 4, 10);
    assertEquals(2446895.5, jd.getJD(), 0.1);
    assertEquals(
        AngleUtils.hmsToDeg(13, 10, 46.1351),
        AngleUtils.normalizeAngle(
            SiderealTime.calculateApparentSiderealTime(jd), 0, 360), 0.0001);
  }

  @Test
  public final void testCalculateMeanSiderealTime() {
    JulianDay jd = new JulianDay(1987, 4, 10, 19, 21, 0, 0);
    assertEquals(2446896.30625, jd.getJD(), 0.00001);
    assertEquals(-1677831.2621266, SiderealTime.calculateMeanSiderealTime(jd),
        0.000001);
  }

}
