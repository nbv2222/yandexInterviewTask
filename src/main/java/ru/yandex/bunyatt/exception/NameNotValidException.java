package ru.yandex.bunyatt.exception;

public class NameNotValidException extends RuntimeException {
  public NameNotValidException(String regex, String name) {
    super(String.format("Name not match regex. regex= %s -> name= %s", regex, name));
  }
}
