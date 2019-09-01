package ru.yandex.bunyatt.util;

public class Conditions {
  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    } else {
      return reference;
    }
  }
}
