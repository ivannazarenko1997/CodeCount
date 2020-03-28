package com.codeminders.services;

import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codeminders.enums.UserCommand;
import com.codeminders.exeption.InvalidCommandExeption;
import com.codeminders.exeption.InvalidFileParcerExeption;

public class CLIService {
  private static final String EXIT = "exit";
  private static final Logger LOGGER = LoggerFactory.getLogger(CLIService.class);
  private static final Scanner SCINPUT = new Scanner(System.in);
  private FileSystemService fileSystemService = new FileSystemService();
  private CalculateSourceCodeService calculateSourceCodeService = new CalculateSourceCodeService();
  private InputOutputService inputOutputService = new InputOutputService();

  private String currentPath = "";

  public CLIService() {
    currentPath = fileSystemService.getCurrentWorkPath();
  }

  public void setCalculateSourceCodeService(CalculateSourceCodeService calculateSourceCodeService) {
    this.calculateSourceCodeService = calculateSourceCodeService;
  }

  public void setInputOutputService(InputOutputService inputOutputService) {
    this.inputOutputService = inputOutputService;
  }

  public void setFileSystemService(FileSystemService fileSystemService) {
    this.fileSystemService = fileSystemService;
  }

  public void printCommandData() {
    inputOutputService.printDataToUser("Use command pwd - for display current path");
    inputOutputService
        .printDataToUser("Use command ls - for display all children subfolders and filles");
    inputOutputService.printDataToUser("Use command cd .. - for go up to header folder ");
    inputOutputService.printDataToUser("Use command cd / - for go to root catalog ");
    inputOutputService
        .printDataToUser("Use command vi - calculate string in file or display subfolder tree");
    inputOutputService.printDataToUser("Use command exit - for stop application ");
  }

  public void parseInput() {
    LOGGER.info("ParceInput");
    inputOutputService.printDataToUser(currentPath + ">");
    String inputStr = inputOutputService.getUserInputFromScreen();
    Boolean isExitCommand = inputOutputService.checkExitCommand(inputStr);

    if (!isExitCommand) {
      try {
        parseCommand(inputStr);
      } catch (InvalidCommandExeption e) {
        LOGGER.info("Error in application");
      }
      parseInput();
    } else {
      inputOutputService.printDataToUser("Bye - bye");
    }
  }

  public void changeParrentPath(String path) {
    currentPath = path;
  }

  public String getCurrentPath() {
    return currentPath;
  }

  public void parseCommand(String cmd) throws InvalidCommandExeption {

    UserCommand command = inputOutputService.parceFirstCommand(cmd);

    switch (command) {
      case LS:
        lsCommand();
        break;

      case PWD:
        pwdCommand();
        break;

      case CD:
        cdCommand(cmd);
        break;

      case VI:
        viCommand(cmd);
        break;

      case NOT_EXIST:
        inputOutputService.printDataToUser("Invalid command ");
        throw new InvalidCommandExeption("Invalid command ");
    }
  }

  public void lsCommand() {
    List<String> listOfChildren = fileSystemService.getPathChildren(getCurrentPath());
    inputOutputService.printListOfDataToUser(listOfChildren);
  }

  public void pwdCommand() {
    inputOutputService.printDataToUser(getCurrentPath());
  }

  public void viCommand(String pathValue) throws InvalidCommandExeption {

    if (!checkCorrectnessDataForVI(pathValue)) {
      inputOutputService.printDataToUser("Invalid command ");
      throw new InvalidCommandExeption("Invalid command ");
    }

    String checkPossiblePath = currentPath + "\\" + pathValue.split(" ")[1];

    if (fileSystemService.checkIsDirectory(checkPossiblePath)) {
      calculateSourceCodeService.printRootDirectory(checkPossiblePath);
    } else if (fileSystemService.checkIsFile(checkPossiblePath)) {
      calculateSourceCodeService.printDataFile(checkPossiblePath);
    } else {
      inputOutputService.printDataToUser("Invalid folder " + pathValue);
    }
  }

  public Boolean checkCorrectnessDataForVI(String cmd) {
    return (cmd != null && cmd.split(" ") != null && cmd.split(" ").length == 2
        && cmd.split(" ")[1] != null && cmd.split(" ")[1].length() != 0);

  }

  public void cdCommand(String cmdCommand) {
    UserCommand command = inputOutputService.parceSecondCommand(cmdCommand);

    try {
      switch (command) {
        case ROOT:
          String workPath = fileSystemService.getRootWorkPath();
          changeParrentPath(workPath);
          break;

        case UP:
          String parentPath = fileSystemService.getParentPath(getCurrentPath());
          changeParrentPath(parentPath);
          break;

        case STING_PATH:
          String secondComand = cmdCommand.split(" ")[1];
          if (!(secondComand.contains("/") || secondComand.contains("\\"))
              && fileSystemService.checkIsDirectory(secondComand)) {
            changeParrentPath(cmdCommand);
            break;
          }

          String checkPossiblePath = currentPath + "\\" + secondComand;
          if (fileSystemService.checkIsDirectory(checkPossiblePath)) {
            changeParrentPath(checkPossiblePath);
            break;
          }

          inputOutputService.printDataToUser("Invalid folder " + cmdCommand);
          break;

        case NOT_EXIST:
          inputOutputService.printDataToUser("Invalid command ");
          throw new InvalidCommandExeption("Invalid command ");

      }

    } catch (Exception e) {
      try {
        throw new InvalidFileParcerExeption("Invalid command");
      } catch (InvalidFileParcerExeption e1) {
      }
    }

  }

}
