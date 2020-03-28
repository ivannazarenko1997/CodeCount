package com.codeminders.services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSystemService {

  public String getRootWorkPath() {
    Path path = Paths.get(getCurrentWorkPath());
    return path.getRoot().toString();
  }

  public String getCurrentWorkPath() {
    return System.getProperty("user.dir");
  }

  public Boolean checkIsDirectory(String path) {
    File newFile = new File(path);
    return newFile.exists() && newFile.isDirectory();
  }

  public Boolean checkIsFile(String path) {
    File newFile = new File(path);
    return newFile.exists() && newFile.isFile();
  }

  public String getParentPath(String path) {
    File f = new File(path);
    return f.getParentFile().getAbsolutePath();
  }

  public List<String> getPathChildren(String dirName) {
    File currentFileData = new File(dirName);
    return (currentFileData.list().length > 0) ? Arrays.asList(currentFileData.list())
        : new ArrayList<>();
  }

}
