package org.cetus.astro;

import static org.junit.Assert.*;

import org.junit.Test;

public class NutationTest {

  @Test
  public void testCalculateNutation() {
    Nutation nutation = new Nutation(2446895.5);
    assertEquals(-3.788, nutation.getDeltaLongitude(), 0.5);
    assertEquals(9.443, nutation.getDeltaObliquity(), 0.1);
  }

}
