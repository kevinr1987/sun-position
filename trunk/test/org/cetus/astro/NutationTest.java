package org.cetus.astro;

import static org.junit.Assert.*;

import org.junit.Test;

public class NutationTest {

  @Test
  public void testCalculateNutation() {
    Nutation nutation = new Nutation(new JulianDay(1987, 4, 10.0));
    assertEquals(-3.788, nutation.getDeltaLongitude(), 0.2);
    assertEquals(9.443, nutation.getDeltaObliquity(), 0.1);
  }

}
