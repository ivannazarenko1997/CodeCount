package com.mkyong.hashing;

import static org.junit.Assert.assertEquals;
import java.util.Scanner;
import org.junit.Test;
import com.codeminders.enums.UserCommand;
import com.codeminders.services.InputOutputService;

public class InputOutputServiceTest {
  private static final String EXIT = "exit";
  private static final String NOT_CORRECT_DATA = "error error";
  private static final String STING_DATA = "cd folder";
  private static Scanner inputScanner = new Scanner(System.in);

  private InputOutputService service = new InputOutputService();

  public void setScanner(Scanner inputScanner) {
    this.inputScanner = inputScanner;
  }

  @Test
  public void testExitInput() {
    Scanner sc = new Scanner(EXIT);
    service.setScanner(sc);
    String expectedExit = service.getUserInputFromScreen();
    assertEquals(expectedExit, EXIT);
    sc.close();
  }

  @Test
  public void shouldReturnExit() {
    Scanner sc = new Scanner(EXIT);
    service.setScanner(sc);
    Boolean expectedExit = service.checkExitCommand(EXIT);
    assertEquals(expectedExit, Boolean.TRUE);
    sc.close();
  }

  @Test
  public void shouldReturnNotExit() {
    Boolean expectedExit = service.checkExitCommand(NOT_CORRECT_DATA);
    assertEquals(expectedExit, Boolean.FALSE);
  }

  @Test
  public void shouldReturnLsFirstCommand() {
    UserCommand expectedLs = service.parceFirstCommand("ls");
    assertEquals(expectedLs, UserCommand.LS);
  }

  @Test
  public void shouldReturnPwdFirstCommand() {
    UserCommand expectedPwd = service.parceFirstCommand("pwd");
    assertEquals(expectedPwd, UserCommand.PWD);
  }

  @Test
  public void shouldReturnCdFirstCommand() {
    UserCommand expectedLs = service.parceFirstCommand("cd");
    assertEquals(expectedLs, UserCommand.CD);
  }

  @Test
  public void shouldReturnViFirstCommand() {
    UserCommand expectedVi = service.parceFirstCommand("vi");
    assertEquals(expectedVi, UserCommand.VI);
  }

  @Test
  public void shouldReturnRootFirstCommand() {
    UserCommand expectedUp = service.parceFirstCommand("/");
    assertEquals(expectedUp, UserCommand.ROOT);
  }

  @Test
  public void shouldReturnDownFirstCommand() {
    UserCommand expectedDown = service.parceFirstCommand("..");
    assertEquals(expectedDown, UserCommand.UP);
  }

  @Test
  public void shouldReturnExitFirstCommand() {
    UserCommand expectedExit = service.parceFirstCommand("exit");
    assertEquals(expectedExit, UserCommand.EXIT);

  }

  @Test
  public void shouldReturnLsSecondCommand() {
    UserCommand expectedLs = service.parceSecondCommand("ls");
    assertEquals(expectedLs, UserCommand.NOT_EXIST);
  }

  @Test
  public void shouldReturnPwdSecondCommand() {
    UserCommand expectedPwd = service.parceSecondCommand("pwd");
    assertEquals(expectedPwd, UserCommand.NOT_EXIST);
  }


  @Test
  public void shouldReturnNotExistsThenCdSecondCommand() {
    UserCommand expectedLs = service.parceSecondCommand("cd");
    assertEquals(expectedLs, UserCommand.NOT_EXIST);
  }

  @Test
  public void shouldReturnNotExistsThenViSecondCommand() {
    UserCommand expectedVi = service.parceSecondCommand("vi");
    assertEquals(expectedVi, UserCommand.NOT_EXIST);
  }

  @Test
  public void shouldReturnNotExistsThenUpSecondCommand() {
    UserCommand expectedUp = service.parceSecondCommand("/");
    assertEquals(expectedUp, UserCommand.NOT_EXIST);
  }

  @Test
  public void shouldReturnNotExistsThenDownSecondCommand() {
    UserCommand expectedDown = service.parceSecondCommand("..");
    assertEquals(expectedDown, UserCommand.NOT_EXIST);

  }

  @Test
  public void shouldReturnNotExistsThenExitSecondCommand() {
    UserCommand expectedExit = service.parceSecondCommand("exit");
    assertEquals(expectedExit, UserCommand.NOT_EXIST);
  }

  @Test
  public void shouldReturnNotExistsFirstCommand() {
    UserCommand expectedNOT_EXIST = service.parceFirstCommand(NOT_CORRECT_DATA);
    assertEquals(expectedNOT_EXIST, UserCommand.NOT_EXIST);
  }

  @Test
  public void shouldReturnNotExistsFirstCommandThenEmptyString() {
    UserCommand expectedNOT_EXIST = service.parceFirstCommand(" ");
    assertEquals(expectedNOT_EXIST, UserCommand.NOT_EXIST);
  }

  @Test
  public void shouldReturnNotExistsSecondCommandThenEmptyString() {
    UserCommand expectedNOT_EXIST = service.parceSecondCommand(" ");
    assertEquals(expectedNOT_EXIST, UserCommand.NOT_EXIST);
  }

  @Test
  public void shouldReturnStringPathSecondCommandThenEmptyString() {
    UserCommand expectedNOT_EXIST = service.parceSecondCommand(STING_DATA);
    assertEquals(expectedNOT_EXIST, UserCommand.STING_PATH);
  }

}
