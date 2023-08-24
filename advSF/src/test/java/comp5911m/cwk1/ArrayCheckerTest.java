package comp5911m.cwk1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests for COMP5911M Coursework 1, Task 3.
 *
 * @author Nick Efford
 */
public class ArrayCheckerTest {
  private int[] expected;
  private int maxValue, delta;
  private ArrayChecker checker;
  @BeforeEach
  void setUp() {
    expected = new int[] {10, 50, 30, 98};
    maxValue = 100;
    delta = 5;
    checker = new ArrayChecker(expected, maxValue, delta);
  }

//  @Test
//  public void testCheck() {
//    int[] expected = new int[] {10, 50, 30, 98};
//    int maxValue = 100;
//    int delta = 5;
//    ArrayChecker checker = new ArrayChecker(expected, maxValue, delta);
//
//    int[] data = new int[] {10, 50, 30};
//    assertFalse(checker.check(data));
//
//    data = new int[] {10, 50, 30, 98, 10};
//    assertFalse(checker.check(data));
//
//    data = new int[] {10, 50, 30, 98};
//    assertTrue(checker.check(data));
//
//    data = new int[] {12, 55, 25, 101};
//    assertFalse(checker.check(data));
//
//    data = new int[] {12, 55, 25, 94};
//    assertTrue(checker.check(data));
//
//    data = new int[] {10, 56, 30, 92};
//    assertFalse(checker.check(data));
//  }
  @Test
  public void testCheckLength(){
    int[] data = new int[] {10, 50, 30};
    assertFalse(checker.checkLength(data));

    data = new int[] {10, 50, 30, 98, 10};
    assertFalse(checker.checkLength(data));

    data = new int[] {10, 50, 30, 98};
    assertTrue(checker.checkLength(data));
  }
  @Test void testCheckTooSmallOrLarge(){
    int[] data = new int[] {12, 55, 25, 101};
    assertFalse(checker.checkTooSmallOrLarge(data));

    data = new int[] {-1, 50, 30, 98};
    assertFalse(checker.checkTooSmallOrLarge(data));

    data = new int[] {10, 50, 30, 98};
    assertTrue(checker.checkTooSmallOrLarge(data));
  }
  @Test void testCheckWithinExpected(){
    int[] data = new int[] {12, 55, 25, 94};
    assertTrue(checker.checkWithinExpected(data));

    data = new int[] {10, 56, 30, 92};
    assertFalse(checker.checkWithinExpected(data));
  }

}
