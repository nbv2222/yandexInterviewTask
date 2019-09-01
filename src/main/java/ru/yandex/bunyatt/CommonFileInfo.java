package ru.yandex.bunyatt;

import lombok.Data;
import ru.yandex.bunyatt.exception.NameNotValidException;

@Data
public abstract class CommonFileInfo {
  public static final String DELIMITER = "/";

  protected final String name;

  protected long creationTime;
  protected long lastAccessTime;
  protected long lastModifiedTime;

  CommonFileInfo(String name, String regex) {
    this.name = name;

    if (!name.matches(regex)) throw new NameNotValidException(regex, name);

    long now = System.currentTimeMillis();
    this.creationTime = now;
    this.lastAccessTime = now;
    this.lastModifiedTime = now;
  }

  public static String getFullName(String path, String fileName) {
    if (path.endsWith("/")) return path + fileName;
    return path.trim() + DELIMITER + fileName.trim();
  }
}
