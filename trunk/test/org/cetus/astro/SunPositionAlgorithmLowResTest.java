package org.cetus.astro;

import static org.junit.Assert.*;

import java.util.TimeZone;

import org.junit.Test;

public class SunPositionAlgorithmLowResTest {

  @Test
  public final void testCalculateSunPosition() {
    // Time zone for Ault, Colorado, USA
    TimeZone aultColorado = TimeZone.getTimeZone("US/Mountain");
    SunPosition position = new SunPositionAlgorithmLowRes(2012, 12, 21, 12, 12,
        12, aultColorado, 104.7416667, 40.6027778).calculateSunPosition();
    assertEquals(25.90, position.getAltitude(), 0.0);
    assertEquals(183.77, position.getAzimuth(), 0.0);
  }

}
