package org.cetus.astro;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

public class JulianDayTest {

  @Test
  public void testJulianDayCalendar() {
    try {
      DateFormat dfmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      dfmt.setTimeZone(TimeZone.getTimeZone("GMT"));
      Date date = dfmt.parse("27/01/0333 12:00:00");
      Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
      calendar.setTime(date);
      assertEquals(1842713.0, new JulianDay(calendar).getJD(), 0.1);
    } catch (ParseException e) {
      fail("Error parsing date: " + e.getMessage());
    }
  }

  @Test
  public void testJulianDayDate() {
    try {
      DateFormat dfmt = new SimpleDateFormat("dd/MM/yyyy");
      dfmt.setTimeZone(TimeZone.getTimeZone("GMT"));
      Date date = dfmt.parse("01/01/1999");
      assertEquals(2451179.5, new JulianDay(date).getJD(), 0.1);
    } catch (ParseException e) {
      fail("Error parsing date: " + e.getMessage());
    }
  }

  @Test
  public void testJulianDayIntIntIntIntIntIntInt() {
    assertEquals(1842713.0, new JulianDay(333, 1, 27, 12, 0, 0, 0).getJD(), 0.1);
  }

  @Test
  public final void testJulianDayIntIntDouble() {
    assertEquals(2436116.31, new JulianDay(1957, 10, 4.81).getJD(), 0.01);
    assertEquals(0.0, new JulianDay(-4712, 1, 1.5).getJD(), 0.1);
  }
}