package ru.yandex.bunyatt.exception;

public class NoDirectoryFoundException extends RuntimeException {
  public NoDirectoryFoundException(String dirPath, String dirName) {
    super(String.format("No directory name=%s found for path=%s", dirPath, dirName));
  }

    public NoDirectoryFoundException(String dirFullName) {
        super(String.format("No directory found for path=%s", dirFullName));
    }
}
