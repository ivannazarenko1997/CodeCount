package com.codeminders.exeption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codeminders.services.CLIService;

public class InvalidFileParcerExeption extends Exception {
  private static final Logger LOGGER = LoggerFactory.getLogger(CLIService.class);

  public InvalidFileParcerExeption(String message) {
    LOGGER.info(message);
  }

}
