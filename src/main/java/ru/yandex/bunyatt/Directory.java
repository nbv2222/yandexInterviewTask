package ru.yandex.bunyatt;

import lombok.Data;
import ru.yandex.bunyatt.exception.FileAlreadyExistException;

import java.util.HashMap;
import java.util.Map;

@Data
class Directory extends CommonFileInfo implements Comparable<Directory> {
  public static final Directory ROOT_DIR = new Directory("");
  public static final String DIRECTORY_NAME_REGEX = "^([A-z0-9-_+])*$";

  private Map<String, RegularFile> fileNameFileMap;

  Directory(String name) {
    super(name, DIRECTORY_NAME_REGEX);
    this.fileNameFileMap = new HashMap<>();
  }

    /**
     * Register a file in a HashMap
     * @param file already created File
     * @return a given file
     * @throws FileAlreadyExistException if file already exist in this directory
     */
  public RegularFile registerFile(RegularFile file) {
    RegularFile regularFile = fileNameFileMap.get(file.getName());
    if (regularFile != null) throw new FileAlreadyExistException(file.getName());
    fileNameFileMap.put(file.getName(), file);
    return file;
  }

    /**
     * Get file if present
     * @param fileName common file name
     * @return file associated with the given name or null if no file found
     */
  public RegularFile getFile(String fileName) {
    return fileNameFileMap.get(fileName);
  }

  public int compareTo(Directory o) {
    return name.compareTo(o.name);
  }

  @Override
  public String toString() {
    return "Directory{" + "fileNameFileMap=" + fileNameFileMap + ", name='" + name + '\'' + '}';
  }
}
