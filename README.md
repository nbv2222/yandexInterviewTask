## Yandex interview task

**Bunyat Teymur** 01.09.2019


____________________
Java in memory file system by the RB-Tree and HashMap

___________________

CreateDir
```
MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
Directory myDir = myInMemoryFileSystem.createDirectory("/", "myDir");
```

CreateFile
```
MyInMemoryFileSystem myInMemoryFileSystem = new MyInMemoryFileSystem();
Directory myDir = myInMemoryFileSystem.createDirectory("/", "myDir");
RegularFile firstFile = myDir2.registerFile(new RegularFile("firstFile", new byte[5]));
```

________________________
