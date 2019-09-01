package ru.yandex.bunyatt.exception;

public class NoParentDirectoriesExistException extends RuntimeException {
  public NoParentDirectoriesExistException(String dirPath) {
    super("Parent directory not found dirPath= " + dirPath);
  }
}
