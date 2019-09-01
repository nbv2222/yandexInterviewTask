package ru.yandex.bunyatt;

import ru.yandex.bunyatt.exception.DirectoryAlreadyExistException;
import ru.yandex.bunyatt.exception.NameNotValidException;
import ru.yandex.bunyatt.exception.NoDirectoryFoundException;
import ru.yandex.bunyatt.exception.NoParentDirectoriesExistException;
import ru.yandex.bunyatt.exception.PathNotValidException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static ru.yandex.bunyatt.CommonFileInfo.DELIMITER;
import static ru.yandex.bunyatt.CommonFileInfo.getFullName;
import static ru.yandex.bunyatt.Directory.ROOT_DIR;

public class MyInMemoryFileSystem {
  public static final String DIRECTORY_PATH_REGEX = "^\\/(([A-z0-9-_+]+\\/)*([A-z0-9])+)*$";

  private Map<String, Directory> fileSystem;

  public MyInMemoryFileSystem() {
    this.fileSystem = new TreeMap<>();
    fileSystem.put("/", ROOT_DIR);
  }

  //  CREATE

    /**
     * Create a file in a given directory
     * @param directoryPath full path to the directory
     * @param file a file
     * @return
     * @throws NoDirectoryFoundException if no directory by the given dirPath exist
     */
  public RegularFile createFile(String directoryPath, RegularFile file)
      throws NoDirectoryFoundException {
    Directory dir = fileSystem.get(directoryPath);
    if (dir == null) throw new NoDirectoryFoundException(directoryPath);

      return dir.registerFile(file);
  }

  public Directory createDirectory(String path, String directoryName)
      throws DirectoryAlreadyExistException, PathNotValidException,
          NoParentDirectoriesExistException, NameNotValidException {
    checkPath(path);
    checkParentDirectories(path);
    checkDirectoryExist(getFullName(path, directoryName));

    Directory directory = new Directory(directoryName);
    fileSystem.put(getFullName(path, directoryName), directory);
    return directory;
  }

  private void checkPath(String path) throws PathNotValidException {
    if (!path.matches(DIRECTORY_PATH_REGEX)) throw new PathNotValidException(path);
  }

  private void checkParentDirectories(String path) throws NoParentDirectoriesExistException {
    List<String> split = splitByDelimiter(path, DELIMITER);
    Set<String> strings = fileSystem.keySet();
    for (String s : split) {
      if (!strings.contains(s)) throw new NoParentDirectoriesExistException(s);
    }
  }

  private List<String> splitByDelimiter(String path, String delimiter) {
    int lastDelimiter = path.lastIndexOf(delimiter);

    if (lastDelimiter == 0) return Collections.singletonList(path);

    List<String> nodes = new ArrayList<>();
    while (lastDelimiter != 0) {
      String substring = path.substring(0, lastDelimiter);
      lastDelimiter = substring.lastIndexOf(delimiter);
      nodes.add(substring);
    }
    return nodes;
  }

  private void checkDirectoryExist(String path) throws DirectoryAlreadyExistException {
    if (fileSystem.get(path) != null) throw new DirectoryAlreadyExistException(path);
  }

  // GET

    /**
     * Get directory by a full path and directoryName
     * @param path
     * @param directoryName
     * @return
     * @throws NoDirectoryFoundException
     */
  public Directory getDirectory(String path, String directoryName)
      throws NoDirectoryFoundException {
    Directory directory = fileSystem.get(getFullName(path, directoryName));
    if (directory == null) throw new NoDirectoryFoundException(path, directoryName);
    directory.setLastAccessTime(System.currentTimeMillis());
    return directory;
  }

  /**
   * This method return a single file by the given path
   *
   * @param path full path to the directory
   * @param fileName qualified file name
   * @return File or null if not exist
   * @throws NoDirectoryFoundException if given path does not exist
   */
  public RegularFile getFile(String path, String fileName) {
    Directory directory = fileSystem.get(path);
    if (directory == null) throw new NoDirectoryFoundException(path);

    return directory.getFile(fileName);
  }

  // FIND

    /**
     * This method find all directories which equals a given directoryName
     * @param directoryName name of a directory
     * @return List with all directories found
     */
  public List<Directory> findDirectory(String directoryName) {
      Set<Map.Entry<String, Directory>> entries = fileSystem.entrySet();
      List<Directory> dirs = new ArrayList<>();
      for (Map.Entry<String, Directory> entry : entries) {
          Directory directory = entry.getValue();
          if (directory.getName().equals(directoryName)) {
              directory.setLastAccessTime(System.currentTimeMillis());
              dirs.add(directory);
          }
      }
      return dirs;
  }

  /**
   * This method return all files in a fileSystem which equals a given fileName
   *
   * @param fileName file name
   * @return List of files
   */
  public List<RegularFile> findAllFiles(String fileName) {
    Set<Map.Entry<String, Directory>> entries = fileSystem.entrySet();
    List<RegularFile> files = new ArrayList<>();
    for (Map.Entry<String, Directory> entry : entries) {
      RegularFile file = entry.getValue().getFile(fileName);
      if (file != null) {
        file.setLastAccessTime(System.currentTimeMillis());
        files.add(file);
      }
    }
    return files;
  }

  @Override
  public String toString() {
    return "fileSystem=" + fileSystem;
  }
}
