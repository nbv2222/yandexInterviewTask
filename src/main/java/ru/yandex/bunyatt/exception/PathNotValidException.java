package ru.yandex.bunyatt.exception;

public class PathNotValidException extends RuntimeException {
  public PathNotValidException(String path) {
    super("Path not valid. path=" + path);
  }
}
