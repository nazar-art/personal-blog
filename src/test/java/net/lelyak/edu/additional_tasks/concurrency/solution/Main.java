package net.lelyak.edu.additional_tasks.concurrency.solution;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.additional_tasks.concurrency.solution.writer.FileWriter;
import net.lelyak.edu.additional_tasks.concurrency.solution.writer.Writer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        if (args.length != 2) {
            log.error("Incorrect usage:");
            log.error("Please provide output folder full path and number of final reports");
            log.error("Example: /tmp/reports_folder 500");
            System.exit(0);
        }

        String destination = args[0];
        int reportsNumber = Integer.valueOf(args[1]);

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores + 1);

        Writer fileWriter = new FileWriter(destination);
        CallableReportTask reportTask = new CallableReportTask(fileWriter);

        for (int i = 0; i < reportsNumber; i++) {
            executor.submit(reportTask);
        }

        executor.shutdown();
        executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

        log.info("ALL_THREADS_DONE");
    }
}
