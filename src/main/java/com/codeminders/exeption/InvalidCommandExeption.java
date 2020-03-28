package com.codeminders.exeption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codeminders.services.CLIService;

public class InvalidCommandExeption extends Exception {
  private static final Logger LOGGER = LoggerFactory.getLogger(CLIService.class);

  public InvalidCommandExeption(String message) {
    LOGGER.info(message);
  }

}
