package ru.yandex.bunyatt;

import lombok.SneakyThrows;
import org.junit.Test;
import ru.yandex.bunyatt.exception.DirectoryAlreadyExistException;
import ru.yandex.bunyatt.exception.NameNotValidException;
import ru.yandex.bunyatt.exception.NoDirectoryFoundException;
import ru.yandex.bunyatt.exception.NoParentDirectoriesExistException;
import ru.yandex.bunyatt.exception.PathNotValidException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MyInMemoryFileSystemTest {

  // ########### POSITIVE TESTS ###########

  @SneakyThrows
  @Test
  public void findFileByFileName_shouldReturn_file() {
    String nameToFind = "validName";
    String path = "/myDir/myDir2";

    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    Directory myDir2 = myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
    myDir2.registerFile(new RegularFile(nameToFind, new byte[5]));
    RegularFile file = myInMemoryFileSystem.getFile(path, nameToFind);

    assertNotNull(file);
  }

  @SneakyThrows
  @Test
  public void findNonExistingFile_shouldReturn_null() {
    String nameToFind = "validName";
    String notValidNameToFind = "notValid";
    String path = "/myDir/myDir2";

    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    Directory myDir2 = myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
    myDir2.registerFile(new RegularFile(nameToFind, new byte[5]));
    RegularFile file = myInMemoryFileSystem.getFile(path, notValidNameToFind);

    assertNull(file);
  }

  @SneakyThrows
  @Test
  public void findFiles_shouldReturn_oneFile() {
    String nameToFind = "validName";

    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    Directory myDir2 = myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
    myDir2.registerFile(new RegularFile(nameToFind, new byte[5]));
    List<RegularFile> fileList = myInMemoryFileSystem.findAllFiles(nameToFind);

    assertEquals(1, fileList.size());
  }

  @SneakyThrows
  @Test
  public void createFile_shouldComplete_withoutErrors() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    Directory myDir2 = myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
    myDir2.registerFile(new RegularFile("firstFile", new byte[5]));
  }

  @SneakyThrows
  @Test
  public void getDirectory_shouldReturn_directory() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
    Directory myDir2 = myInMemoryFileSystem.getDirectory("/myDir", "myDir2");
    assertNotNull(myDir2);
  }

  @SneakyThrows
  @Test
  public void createDirectory_shouldComplete_withoutErrors() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
  }

  // ########### NEGATIVE TESTS ###########

  @SneakyThrows
  @Test(expected = NameNotValidException.class)
  public void createFileWithInvalidName_shouldThrow_NameNotValidException() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    Directory myDir2 = myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
    myDir2.registerFile(new RegularFile("русскиеБуквы", new byte[5]));
  }

  @SneakyThrows
  @Test(expected = NullPointerException.class)
  public void createFileWithNoData_shouldThrow_NullPointerException() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    Directory myDir2 = myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
    myDir2.registerFile(new RegularFile("validName", null));
  }

  @SneakyThrows
  @Test(expected = NoDirectoryFoundException.class)
  public void getNonExistingDirectory_shouldThrow_noDirectoryFoundException() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
    myInMemoryFileSystem.getDirectory("/null", "myDir21");
  }

  @SneakyThrows
  @Test(expected = NullPointerException.class)
  public void getNullDirectory_shouldThrow_nullPointerException() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    myInMemoryFileSystem.createDirectory("/myDir", "myDir2");
    myInMemoryFileSystem.getDirectory(null, "myDir2");
  }

  @SneakyThrows
  @Test(expected = DirectoryAlreadyExistException.class)
  public void createSameDirectoryTwice_shouldThrow_directoryAlreadyExistException() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/", "myDir");
    myInMemoryFileSystem.createDirectory("/", "myDir");
  }

  @SneakyThrows
  @Test(expected = PathNotValidException.class)
  public void createDirectoryWithInvalidPath_shouldThrow_pathNotValidException() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("awd///aw123", "myDir");
  }

  @SneakyThrows
  @Test(expected = PathNotValidException.class)
  public void createDirectoryWithInvalidPath_shouldThrow_pathNotValidException2() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("///aw123", "myDir");
  }

  @SneakyThrows
  @Test(expected = PathNotValidException.class)
  public void createDirectoryWithInvalidPath_shouldThrow_pathNotValidException3() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("", "myDir");
  }

  @SneakyThrows
  @Test(expected = NoParentDirectoriesExistException.class)
  public void createDirectoryWithoutParent_shouldThrow_noParentDirectoriesExistException() {
    MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
    myInMemoryFileSystem.createDirectory("/parent1/parent2/parent3", "myDir");
  }
}
