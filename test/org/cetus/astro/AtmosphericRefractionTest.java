package org.cetus.astro;

import static org.junit.Assert.*;

import org.junit.Test;

public class AtmosphericRefractionTest {

  @Test
  public final void testAtmosphericRefractionDouble() {
    assertEquals(24.618, new AtmosphericRefraction(0.5541).getRefraction(),
        0.001);
    assertEquals(57.864 / 60,
        new AtmosphericRefraction(0.5541).getApparentAltitude(), 0.001);
  }

}
