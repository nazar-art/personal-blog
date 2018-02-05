package net.lelyak.edu.additional_tasks.concurrency.test;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.additional_tasks.concurrency.solution.MainWithCallableFuture;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = MainWithCallableFuture.class)
public class MainWithCallableTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testSaveReportSuccess() throws Exception {
        File folder = tempFolder.getRoot();
        String destinationPath = folder.getAbsolutePath();
        String reportNumber = "2";

        MainWithCallableFuture.main(new String[]{destinationPath, reportNumber});

        Files.list(Paths.get(destinationPath)).forEach(file -> {
            log.info("FILE_INFO: {}", file.toAbsolutePath());
            File reportFile = file.toFile();
            String reportFileName = reportFile.getName();
            String content = null;

            try {
                content = FileUtils.readFileToString(reportFile);
            } catch (IOException e) {
                log.error("Error during reading the file: {}, details: {}", reportFileName, e.getMessage());
            }

            Assert.assertTrue(reportFile.exists());
            Assert.assertTrue(reportFileName.startsWith("report_"));

            log.debug("FILE: {} has CONTENT: {}", reportFileName, content);

            assert content != null;
            Assert.assertTrue(content.startsWith(String.format("This is report [%s] content generated at",
                    FilenameUtils.removeExtension(reportFileName))));
        });
    }
}
