package com.codeminders.services;

import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codeminders.enums.CommandPart;
import com.codeminders.enums.UserCommand;

public class InputOutputService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CLIService.class);
  private static Scanner inputScanner = new Scanner(System.in);

  public void setScanner(Scanner inputScanner) {
    this.inputScanner = inputScanner;
  }

  public InputOutputService() {
    LOGGER.info("<<Initialize screen interface");
  }

  public String getUserInputFromScreen() {
    return inputScanner.nextLine();
  }

  public void printDataToUser(String data) {
    System.out.println(data);
  }

  public void printListOfDataToUser(List<String> data) {
    data.forEach(dataid -> {
      printDataToUser(dataid);
    });
  }

  public Boolean checkExitCommand(String data) {
    return UserCommand.EXIT.equals(parceFirstCommand(data));
  }

  public UserCommand parceFirstCommand(String cmd) {
    if (cmd == null || cmd.split(" ") == null || cmd.split(" ").length == 0) {
      return UserCommand.NOT_EXIST;
    }
    String[] cmdParts = cmd.split(" ");
    String firstCommand = cmdParts[CommandPart.PARTONE.getType()];

    return parceUserCommand(firstCommand);

  }

  public UserCommand parceSecondCommand(String cmd) {
    if (cmd == null || cmd.split(" ") == null || cmd.split(" ").length != 2) {
      return UserCommand.NOT_EXIST;
    }

    String[] cmdParts = cmd.split(" ");
    String secondCommand = cmdParts[CommandPart.PARTTWO.getType()];

    UserCommand command = parceUserCommand(secondCommand);

    if (UserCommand.NOT_EXIST.equals(command) && secondCommand != null
        && secondCommand.length() > 0) {
      return UserCommand.STING_PATH;
    }

    return command;

  }

  public UserCommand parceUserCommand(String inputData) {
    LOGGER.info("<< inputOutputService");

    switch (inputData) {
      case "ls":
        return UserCommand.LS;
      case "pwd":
        return UserCommand.PWD;
      case "vi":
        return UserCommand.VI;
      case "cd":
        return UserCommand.CD;
      case "exit":
        return UserCommand.EXIT;
      case "/":
        return UserCommand.ROOT;
      case "..":
        return UserCommand.UP;
      default:
        return UserCommand.NOT_EXIST;
    }
  }
}
