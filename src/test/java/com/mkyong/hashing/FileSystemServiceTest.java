package com.mkyong.hashing;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.codeminders.services.FileSystemService;



public class FileSystemServiceTest {

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();
  private File level1;
  private File level2a;
  private File level2b;
  private File level2bfile;

  @Before
  public void setUp() throws Exception {
    level1 = folder.newFolder("level1");
    level2a = folder.newFolder("level1", "level2a");
    level2b = folder.newFolder("level1", "level2b");
    level2bfile = folder.newFile("file.txt");
  }

  private FileSystemService service = new FileSystemService();

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void shouldReturnTrueThanCheckDirectoryExistance() {
    Boolean expectedExistanse = service.checkIsDirectory(level2a.getAbsolutePath());
    assertEquals(expectedExistanse, Boolean.TRUE);
  }

  @Test
  public void shouldReturnFalseThenCheckDirectoryExistance() {
    Boolean expectedExistanse = service.checkIsDirectory(level2bfile.getAbsolutePath());
    assertEquals(expectedExistanse, Boolean.FALSE);
  }

  @Test
  public void shouldReturnTrueThanFileExistse() {
    Boolean expectedExistanse = service.checkIsFile(level2bfile.getAbsolutePath());
    assertEquals(expectedExistanse, Boolean.TRUE);
  }

  @Test
  public void shouldReturnFalseThanFileExists() {
    Boolean expectedExistanse = service.checkIsDirectory(level2bfile.getAbsolutePath());
    assertEquals(expectedExistanse, Boolean.FALSE);
  }

  @Test
  public void shouldReturnParentPath() {
    String expectedParentPath = service.getParentPath(level2a.getAbsolutePath());
    assertEquals(expectedParentPath, level1.getAbsolutePath());
  }

  @Test
  public void shouldReturnPathChildren() {
    List<String> expectedList = service.getPathChildren(level1.getAbsolutePath());
    assertEquals(expectedList, Arrays.asList(new String[] {"level2a", "level2b"}));
  }

  @Test
  public void shouldReturnNonePathChildren() {
    List<String> expectedList = service.getPathChildren(level2a.getAbsolutePath());
    assertEquals(expectedList.size(), 0);
  }

  @Test
  public void shouldReturnCurrentWorkPath() {
    System.setProperty("user.dir", "C:/DEV/DATA");
    String expectedWorkPath = service.getCurrentWorkPath();
    assertEquals(expectedWorkPath, "C:/DEV/DATA");
    System.clearProperty("user.dir");
  }


}
