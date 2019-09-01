package ru.yandex.bunyatt.exception;

public class DirectoryAlreadyExistException extends RuntimeException {
  public DirectoryAlreadyExistException(String dirFullName) {
    super("Directory already exist. directory= " + dirFullName);
  }
}
