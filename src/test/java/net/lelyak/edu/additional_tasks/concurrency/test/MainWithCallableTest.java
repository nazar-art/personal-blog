package net.lelyak.edu.additional_tasks.concurrency.test;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.additional_tasks.concurrency.solution.MainWithCallableFuture;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = MainWithCallableFuture.class)
public class MainWithCallableTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    public void testSaveReportSuccess() throws Exception {
        File folder = tempFolder.getRoot();
        String destinationPath = folder.getAbsolutePath();
        String reportNumber = "2";

        MainWithCallableFuture.main(new String[]{destinationPath, reportNumber});

        Files.list(Paths.get(destinationPath)).forEach(file -> {
            log.debug("FILE_LOCATION: {}", file.toAbsolutePath());

            File reportFile = file.toFile();
            String reportFileName = reportFile.getName();
            String content = StringUtils.EMPTY;

            Assert.assertTrue(reportFile.exists());
            Assert.assertTrue(reportFile.isFile());
            Assert.assertTrue(reportFileName.startsWith("report_"));

            try {
                content = FileUtils.readFileToString(reportFile);
            } catch (IOException e) {
                log.error("Error during reading the file: {}, details: {}", reportFileName, e.getMessage());
            }

            log.info("FILE: {} has CONTENT: {}", reportFileName, content);

            Assert.assertFalse(content.isEmpty());
            Assert.assertTrue(content.startsWith(String.format("This is report [%s] content generated at %s",
                    FilenameUtils.removeExtension(reportFileName), LocalDateTime.now().format(formatter))));
        });
    }
}
