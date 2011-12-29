package org.cetus.astro;

import static org.junit.Assert.*;

import org.junit.Test;

public class EclipticObliquityTest {

  @Test
  public final void testCalculateMeanObliquityDouble() {
    // Obliquity on 1987 April 10 at 0h TD (2446895.5 JDE) is 23 26' 27".407
    assertEquals(AngleUtils.dmsToDeg(23, 26, 27.407), 
        EclipticObliquity.calculateMeanObliquity(-0.127296372348), 0.00001);
  }

  @Test
  public final void testCalculateTrueObliquityDouble() {
    // Obliquity on 1987 April 10 at 0h TD (2446895.5 JDE) is 23 26' 27".407
    assertEquals(AngleUtils.dmsToDeg(23, 26, 36.850), 
        EclipticObliquity.calculateTrueObliquity(-0.127296372348), 0.00001);
  }

}
