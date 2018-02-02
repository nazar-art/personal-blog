package net.lelyak.edu.additional_tasks.concurrency.solution;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.additional_tasks.concurrency.SlowReportingApiClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@AllArgsConstructor
public class FileWriter implements Writer {

    private String outputDir;

    @Override
    public void write(SlowReportingApiClient.Report report) {

        Path path = Paths.get(outputDir.concat("/").concat(report.getName()).concat(".txt"));

        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
            writer.write(report.getContent());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        log.info("write report: {} to file: {}", report.getName(), path);
    }

}
