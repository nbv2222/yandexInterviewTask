package ru.yandex.bunyatt;

import java.util.Objects;

import static ru.yandex.bunyatt.util.Conditions.checkNotNull;

class RegularFile extends CommonFileInfo {
  public static final String FILE_PATH_REGEX = "^([A-z0-9])+$";

  private final byte[] data;

  RegularFile(String name, byte[] data) {
    super(name, FILE_PATH_REGEX);
    this.data = checkNotNull(data);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    RegularFile that = (RegularFile) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
