package com.codeminders.file;

import com.codeminders.services.CLIService;

public class App {
  public static void main(String[] args) {
    CLIService service = new CLIService();
    service.printCommandData();
    service.parseInput();
  }
}
