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
public class AngleUtils {

  @SuppressWarnings("unused")
  private static Logger log = Logger.getRootLogger();

  public static double hmsToDeg(int hour, int minute, double second) {
    return sexagesimalToDeg(hour, minute, second) * 15;
  }
  
  /**
   * Normalizes an angle value between a given range
   * 
   * @param value
   *          the angle value
   * @param minValue
   *          minimum value of the range
   * @param maxValue
   *          maximum value of the range
   */
  public static double normalizeAngle(double value, double minValue,
      double maxValue) {
    double delta = maxValue - minValue;
    return ((value - minValue) % delta + delta) % delta + minValue;
  }
  
  public static double sexagesimalToDeg(int degree, int arcminute,
      double arcsecond) {
    return (double) degree + (double) arcminute / 60 + (double) arcsecond
        / (60 * 60);
  }
}