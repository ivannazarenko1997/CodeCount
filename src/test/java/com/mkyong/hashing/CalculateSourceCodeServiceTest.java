package com.mkyong.hashing;

import static org.junit.Assert.assertEquals;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.codeminders.services.CalculateSourceCodeService;

public class CalculateSourceCodeServiceTest {
  private static final String FILE1 = "Test1.java";
  private static final String FILE2 = "Test2.java";
  private static final String ROOT = "root";
  private static final String SUBFOLDER1 = "subfolder1";
  private static final String SUBFOLDER2 = "subfolder2";
  private static final String SUBFOLDER3 = "subfolder3";

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Rule
  public TemporaryFolder folderTree = new TemporaryFolder();

  private CalculateSourceCodeService service = new CalculateSourceCodeService();

  @Test
  public void shouldReturn3linesCodeFromSecondFile() {

    try {
      File level1file = folder.newFile(FILE1);
      fillFileOfData(FILE1, level1file);
      Integer expectedLength = service.getCountOfRowsInFile(level1file.getAbsolutePath());

      assertEquals(expectedLength, new Integer(3));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldReturn8linesCodeFromSecondFile() {
    try {
      File level1file = folder.newFile(FILE2);
      fillFileOfData(FILE2, level1file);

      Integer expectedLength = service.getCountOfRowsInFile(level1file.getAbsolutePath());

      assertEquals(expectedLength, new Integer(8));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldReturn11LinesCountOfCodeFromFolderTree() {
    try {
      File level1file = folder.newFile(FILE1);
      fillFileOfData(FILE1, level1file);

      File level2file = folder.newFile(FILE2);
      fillFileOfData(FILE2, level2file);

      Integer expectedLengthf = service.listRootDirectory(folder.getRoot().getAbsolutePath(), 0);
      assertEquals(expectedLengthf, new Integer(11));

      level1file.delete();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldReturn14LinesCountOfCodeFromFolderTree() {
    try {
      File subfolder1 = folder.newFolder(ROOT, SUBFOLDER1);

      File subfolder1file1 = new File(subfolder1, FILE1);
      fillFileOfData(FILE1, subfolder1file1);

      File subfolder1file2 = new File(subfolder1, FILE2);
      fillFileOfData(FILE2, subfolder1file2);

      File subfolder2 = folder.newFolder(ROOT, SUBFOLDER2);

      File subfolder2file1 = new File(subfolder2, FILE1);
      fillFileOfData(FILE1, subfolder2file1);

      folder.newFolder(ROOT, SUBFOLDER3);

      Integer expectedLengthf = service.listRootDirectory(folder.getRoot().getAbsolutePath(), 0);
      assertEquals(expectedLengthf, new Integer(14));


    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void fillFileOfData(String fileFromName, File fileToName) {
    ClassLoader classLoader = getClass().getClassLoader();
    List<String> fileData = new ArrayList<String>();
    try (InputStream inputStream = classLoader.getResourceAsStream(fileFromName)) {
      String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
      fileData.add(result);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToName))) {
      fileData.forEach(data -> {

        try {
          writer.write(data);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      });
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

}
