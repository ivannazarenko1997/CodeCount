package com.mkyong.hashing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import com.codeminders.enums.UserCommand;
import com.codeminders.services.CLIService;
import com.codeminders.services.CalculateSourceCodeService;
import com.codeminders.services.FileSystemService;
import com.codeminders.services.InputOutputService;


public class CLIServiceTest {
  private static final String CURRENT_PATH = "C:\\dev\\data";
  private static final String CURRENT_PATH_DOWN = "C:\\dev";
  private static final String PATH = "level1";
  private static final String EXIT = new String("exit");
  private static final String LS = new String("ls");
  private static final String PWD = new String("pwd");
  private static final String VI = new String("vi data");
  private static final String CD_UP = new String("cd ..");
  private static final String ERROR_COMMAND = new String("error error");
  private static final String CD_ROOT = new String("cd /");
  private static final String STING_PATH = new String("cd data");
  private static final String STING_SMALL_PATH = new String("data");
  private static final List<String> LIST_CHILDREN =
      Arrays.asList(new String[] {"level2a", "level2b"});
  private CalculateSourceCodeService calculateSourceCodeService =
      mock(CalculateSourceCodeService.class);
  private InputOutputService inputOutputService = mock(InputOutputService.class);
  private FileSystemService fileSystemService = mock(FileSystemService.class);
  private CLIService service = new CLIService();

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();


  @Before
  public void setUp() throws Exception {
    service.changeParrentPath(PATH);
    service.setCalculateSourceCodeService(calculateSourceCodeService);
    service.setFileSystemService(fileSystemService);
    service.setInputOutputService(inputOutputService);
  }

  @Test
  public void shouldExitApplicationWhenExitInputted() {

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.eq(EXIT))).thenReturn(true);
    service.parseInput();
    Mockito.verify(inputOutputService, Mockito.times(1)).getUserInputFromScreen();
  }

  @Test
  public void shouldRunLSCommandThenInputLSAndAfterExit() {

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(LS).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.anyString())).thenReturn(false)
        .thenReturn(true);
    Mockito.when(inputOutputService.parceFirstCommand(Mockito.eq(LS))).thenReturn(UserCommand.LS);
    Mockito.when(fileSystemService.getPathChildren(Mockito.eq(PATH))).thenReturn(LIST_CHILDREN);

    service.parseInput();
    Mockito.verify(fileSystemService, Mockito.times(1)).getPathChildren(Mockito.eq(PATH));
    Mockito.verify(inputOutputService, Mockito.times(1))
        .printListOfDataToUser(Mockito.eq(LIST_CHILDREN));

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.eq(EXIT))).thenReturn(true);
  }

  @Test
  public void shouldRunPWDCommandThenInputPWDAndAfterExit() {

    Mockito.when(fileSystemService.getCurrentWorkPath()).thenReturn(CURRENT_PATH);
    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(PWD).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.anyString())).thenReturn(false)
        .thenReturn(true);
    Mockito.when(inputOutputService.parceFirstCommand(Mockito.eq(PWD))).thenReturn(UserCommand.PWD);
    Mockito.when(fileSystemService.getPathChildren(Mockito.eq(PATH))).thenReturn(LIST_CHILDREN);

    service.changeParrentPath(CURRENT_PATH);
    service.parseInput();
    Mockito.verify(inputOutputService, Mockito.times(1)).printDataToUser(CURRENT_PATH);

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.eq(EXIT))).thenReturn(true);
  }

  @Test
  public void shouldRunCD_DOWN_CommandThenInputCD_DOWNAndAfterExit() {

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(CD_UP).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.anyString())).thenReturn(false)
        .thenReturn(true);
    Mockito.when(inputOutputService.parceFirstCommand(Mockito.eq(CD_UP)))
        .thenReturn(UserCommand.CD);
    Mockito.when(inputOutputService.parceSecondCommand(Mockito.eq(CD_UP)))
        .thenReturn(UserCommand.UP);
    Mockito.when(fileSystemService.getParentPath(CURRENT_PATH)).thenReturn(CURRENT_PATH_DOWN);

    service.parseInput();
    Mockito.verify(inputOutputService, Mockito.times(1)).parceFirstCommand(Mockito.eq(CD_UP));
    Mockito.verify(inputOutputService, Mockito.times(1)).parceSecondCommand(Mockito.eq(CD_UP));

    String exeptedPath = fileSystemService.getParentPath(CURRENT_PATH);
    assertEquals(exeptedPath, CURRENT_PATH_DOWN);
    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.eq(EXIT))).thenReturn(true);
  }

  @Test
  public void shouldRunCD_ROOT_CommandThenInputCD_ROOTAndAfterExit() {
    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(CD_ROOT).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.anyString())).thenReturn(false)
        .thenReturn(true);
    Mockito.when(inputOutputService.parceFirstCommand(Mockito.eq(CD_ROOT)))
        .thenReturn(UserCommand.CD);
    Mockito.when(inputOutputService.parceSecondCommand(Mockito.eq(CD_ROOT)))
        .thenReturn(UserCommand.ROOT);
    Mockito.when(fileSystemService.getRootWorkPath()).thenReturn(CURRENT_PATH_DOWN);

    service.parseInput();
    Mockito.verify(inputOutputService, Mockito.times(1)).parceFirstCommand(Mockito.eq(CD_ROOT));
    Mockito.verify(inputOutputService, Mockito.times(1)).parceSecondCommand(Mockito.eq(CD_ROOT));
    Mockito.verify(fileSystemService, Mockito.times(1)).getRootWorkPath();

    String exeptedPath = fileSystemService.getRootWorkPath();
    assertEquals(exeptedPath, CURRENT_PATH_DOWN);
    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.eq(EXIT))).thenReturn(true);
  }

  @Test
  public void shouldRunSTING_PATH_CommandThenInputSTING_PATHAndAfterExit() {

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(STING_PATH)
        .thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.anyString())).thenReturn(false)
        .thenReturn(true);
    Mockito.when(inputOutputService.parceFirstCommand(Mockito.eq(STING_PATH)))
        .thenReturn(UserCommand.CD);
    Mockito.when(inputOutputService.parceSecondCommand(Mockito.eq(STING_PATH)))
        .thenReturn(UserCommand.STING_PATH);
    Mockito.when(fileSystemService.checkIsDirectory(STING_SMALL_PATH)).thenReturn(true);

    service.parseInput();
    Mockito.verify(inputOutputService, Mockito.times(1)).parceFirstCommand(Mockito.eq(STING_PATH));
    Mockito.verify(inputOutputService, Mockito.times(1)).parceSecondCommand(Mockito.eq(STING_PATH));
    Mockito.verify(fileSystemService, Mockito.times(1)).checkIsDirectory(STING_SMALL_PATH);

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.eq(EXIT))).thenReturn(true);
  }

  @Test
  public void shouldRunVI_CommandThenInputVIdiectoryAndAfterExit() {

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(VI).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.anyString())).thenReturn(false)
        .thenReturn(true);
    Mockito.when(inputOutputService.parceFirstCommand(Mockito.eq(VI))).thenReturn(UserCommand.VI);
    Mockito.when(fileSystemService.checkIsDirectory(CURRENT_PATH)).thenReturn(true);

    service.changeParrentPath(CURRENT_PATH_DOWN);
    service.parseInput();

    Mockito.verify(inputOutputService, Mockito.times(1)).parceFirstCommand(Mockito.eq(VI));
    Mockito.verify(fileSystemService, Mockito.times(1)).checkIsDirectory(CURRENT_PATH);

    // calculate in directory, not in single file
    Mockito.verify(calculateSourceCodeService, Mockito.times(1)).printRootDirectory(CURRENT_PATH);
    Mockito.verify(calculateSourceCodeService, Mockito.times(0)).printDataFile(CURRENT_PATH);

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.eq(EXIT))).thenReturn(true);

  }

  @Test
  public void shouldRunVI_CommandThenInputVIfileAndAfterExit() {

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(VI).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.anyString())).thenReturn(false)
        .thenReturn(true);
    Mockito.when(inputOutputService.parceFirstCommand(Mockito.eq(VI))).thenReturn(UserCommand.VI);
    Mockito.when(fileSystemService.checkIsDirectory(CURRENT_PATH)).thenReturn(false);
    Mockito.when(fileSystemService.checkIsFile(CURRENT_PATH)).thenReturn(true);


    service.changeParrentPath(CURRENT_PATH_DOWN);
    service.parseInput();

    Mockito.verify(inputOutputService, Mockito.times(1)).parceFirstCommand(Mockito.eq(VI));
    Mockito.verify(fileSystemService, Mockito.times(1)).checkIsDirectory(CURRENT_PATH);

    // calculate in single file, not in directory
    Mockito.verify(calculateSourceCodeService, Mockito.times(0)).printRootDirectory(CURRENT_PATH);
    Mockito.verify(calculateSourceCodeService, Mockito.times(1)).printDataFile(CURRENT_PATH);

    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.eq(EXIT))).thenReturn(true);

  }

  @Test
  public void shouldRunVI_ERROR_CommandThenInputVIAndAfterExit() {
    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(ERROR_COMMAND)
        .thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.anyString())).thenReturn(false)
        .thenReturn(true);
    Mockito.when(inputOutputService.parceFirstCommand(Mockito.eq(ERROR_COMMAND)))
        .thenReturn(UserCommand.NOT_EXIST);

    service.parseInput();

    Mockito.verify(inputOutputService, Mockito.times(1))
        .parceFirstCommand(Mockito.eq(ERROR_COMMAND));
    Mockito.when(inputOutputService.getUserInputFromScreen()).thenReturn(EXIT);
    Mockito.when(inputOutputService.checkExitCommand(Mockito.eq(EXIT))).thenReturn(true);
  }
}
