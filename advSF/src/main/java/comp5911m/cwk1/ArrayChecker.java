package comp5911m.cwk1;

import java.util.Arrays;


/**
 * Class for COMP5911M Coursework 1, Task 3.
 *
 * @author Nick Efford
 */
public class ArrayChecker {
  private int[] expected;
  private int max;
  private int delta;

  public ArrayChecker(int[] exp, int max, int delta) {
    expected = Arrays.copyOf(exp, exp.length);
    this.max = max;
    this.delta = delta;
  }

//  public boolean check(int[] d) {
//    // Check for length difference
//    if (d.length != expected.length) {
//      return false;
//    }
//
//    // Check if any values are too small / too large
//    for (int i = 0; i < d.length; ++i) {
//      if (d[i] < 0 || d[i] > max) {
//        return false;
//      }
//    }
//
//    // Check that each entry is within +/- delta of expected
//    for (int i = 0; i < d.length; ++i) {
//      if (Math.abs(expected[i] - d[i]) > delta) {
//        return false;
//      }
//    }
//
//    return true;
//  }
  public boolean checkLength(int[] d){
    // Check for length difference
    if (d.length != expected.length) {
      return false;
    }
    return true;
  }
  public boolean checkTooSmallOrLarge(int[] d){
    // Check if any values are too small / too large
    for (int i = 0; i < d.length; ++i) {
      if (d[i] < 0 || d[i] > max) {
        return false;
      }
    }
    return true;
  }
  public boolean checkWithinExpected(int[] d){
    // Check that each entry is within +/- delta of expected
    for (int i = 0; i < d.length; ++i) {
      if (Math.abs(expected[i] - d[i]) > delta) {
        return false;
      }
    }
    return true;
  }
}
