package org.cetus.astro;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

public class DateTimeUtilsTest {


  @Test
  public final void testConvertJulianDayToCalendarDouble() {
    try {
      Calendar calendar = DateTimeUtils.convertJulianDayToCalendar(2436116.31);
      // convert calendar to string date
      DateFormat dfmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      dfmt.setTimeZone(TimeZone.getTimeZone("GMT"));      
      String date = dfmt.format(calendar.getTime());      
      assertEquals("1957/10/04 19:26:24", date);
      
    } catch (ParseException e) {
      fail("Error parsing date" + e.getMessage());
    }
  }
}