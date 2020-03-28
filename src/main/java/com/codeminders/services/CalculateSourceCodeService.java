package com.codeminders.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CalculateSourceCodeService {
  private static final String WHITE_SPACE = " ";
  private static final String ROW_SEPARATOR = "\n";
  private static final String ROW_SEP = " : ";
  private static final String REGEX_DELETE_COMMENTS =
      "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/";
  private static final String REGEX_DELETE_EMPTY = "\\s+";

  private static final String[] CODE_FILE = {".java"};

  public Integer getCountOfRowsInFile(String pathForFile) {
    Integer countOfRows = 0;
    File file = new File(pathForFile);
    ArrayList<String> arraysOfRows = getLinesFromTextFile(file);
    StringBuilder builderFulledRows = new StringBuilder();

    arraysOfRows.forEach(iteratedLine -> {
      String cleanedNotEmptyRow =
          iteratedLine.replaceAll(REGEX_DELETE_COMMENTS, "$1 ").replaceAll(REGEX_DELETE_EMPTY, "");
      if (cleanedNotEmptyRow.length() > 0) {
        builderFulledRows.append(cleanedNotEmptyRow).append(ROW_SEPARATOR);
      }
    });

    String returnData = builderFulledRows.toString().replaceAll(REGEX_DELETE_COMMENTS, "$1 ");
    for (String separatedLine : returnData.split(ROW_SEPARATOR)) {
      if (separatedLine.replaceAll(REGEX_DELETE_EMPTY, "").length() > 0) {
        countOfRows++;
      }
    }

    return countOfRows;
  }

  private ArrayList<String> getLinesFromTextFile(File file) {
    ArrayList<String> returnSting = new ArrayList<>();
    BufferedReader br;
    String st;
    try {
      br = new BufferedReader(new FileReader(file));
      while ((st = br.readLine()) != null) {
        returnSting.add(st);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return returnSting;
  }

  public void listDirectory(String dirPath, int level) {
    File dir = new File(dirPath);
    File[] firstLevelFiles = dir.listFiles();
    if (firstLevelFiles != null && firstLevelFiles.length > 0) {
      for (File aFile : firstLevelFiles) {
        StringBuilder printDataOfFile = getTextBuilder(level);

        if (aFile.isDirectory()) {
          Integer localCount = listRootDirectory(aFile.getAbsolutePath(), level + 1);
          printDataOfFile.append(aFile.getName()).append(ROW_SEP).append(localCount);
          System.out.println(printDataOfFile);
          listDirectory(aFile.getAbsolutePath(), level + 1);
        } else if (isCorrectEndOfFile(aFile.getName())) {
          Integer localCount = getCountOfRowsInFile(aFile.getAbsolutePath());
          printDataOfFile.append(aFile.getName()).append(ROW_SEP).append(localCount);
          System.out.println(printDataOfFile);
        }
      }
    }
  }

  public boolean isCorrectEndOfFile(String isData) {
    for (int i = 0; i < CODE_FILE.length; i++) {
      if (isData.endsWith(CODE_FILE[i])) {
        return true;
      }
    }
    return false;
  }

  public StringBuilder getTextBuilder(int level) {
    StringBuilder printDataOfFile = new StringBuilder();
    for (int i = 0; i < level; i++) {
      printDataOfFile.append(WHITE_SPACE);
    }
    return printDataOfFile;
  }

  public int listRootDirectory(String dirPath, int level) {
    int count = 0;
    File dir = new File(dirPath);
    File[] firstLevelFiles = dir.listFiles();
    if (firstLevelFiles != null && firstLevelFiles.length > 0) {
      for (File aFile : firstLevelFiles) {
        if (aFile.isDirectory()) {
          Integer localCount = listRootDirectory(aFile.getAbsolutePath(), level + 1);
          count = count + localCount;
        } else if (isCorrectEndOfFile(aFile.getName())) {
          count = count + getCountOfRowsInFile(aFile.getAbsolutePath());
        }
      }
    }
    return count;
  }

  public void printRootDirectory(String dirPath) {
    System.out.println("root : " + listRootDirectory(dirPath, 0));
    listDirectory(dirPath, 1);
  }

  public void printDataFile(String dirPath) {
    Integer countRows = getCountOfRowsInFile(dirPath);
    File dir = new File(dirPath);
    System.out.println(dir.getName() + ROW_SEP + countRows);
  }
}
