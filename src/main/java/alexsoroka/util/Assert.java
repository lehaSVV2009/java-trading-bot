package alexsoroka.util;

/**
 * Utilities for simple asserts with exceptions
 */
public final class Assert {

  /**
   * Assert object is not null.
   *
   * @param object object to check
   * @param message text in exception if exception is thrown
   * @throws IllegalArgumentException if object is null
   */
  public static void nonNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Assert expression is true.
   *
   * @param expression expression to check
   * @param message text in exception if exception is thrown
   * @throws IllegalArgumentException if expression is not true
   */
  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Assert expression is true for states.
   *
   * @param expression expression to check
   * @param message text in exception if exception is thrown
   * @throws IllegalStateException if expression is not true
   */
  public static void state(boolean expression, String message) {
    if (!expression) {
      throw new IllegalStateException(message);
    }
  }
}
