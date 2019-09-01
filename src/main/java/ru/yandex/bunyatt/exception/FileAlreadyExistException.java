package ru.yandex.bunyatt.exception;

public class FileAlreadyExistException extends RuntimeException {
  public FileAlreadyExistException(String filePath, String fileName) {
    super(String.format("File already exist. directory= %s fileName= %s", filePath, fileName));
  }

  public FileAlreadyExistException(String fileName) {
    super(String.format("File already exist. fileName= %s", fileName));
  }
}
