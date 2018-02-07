package net.lelyak.edu.additional_tasks.concurrency.solution;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.additional_tasks.concurrency.solution.writer.FileWriter;
import net.lelyak.edu.additional_tasks.concurrency.solution.writer.Writer;
import org.apache.commons.lang3.Validate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
public class Main {

    @SneakyThrows
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        if (args.length != 2) {
            log.error("Incorrect usage:");
            log.error("Please provide output folder full path and number of final reports");
            log.error("Example: /tmp/reports_folder 500");
            System.exit(0);
        }

        Validate.notBlank(args[0], "folder location can't be null or empty");
        Validate.notBlank(args[1], "report number can't be null or empty");

        String destination = args[0];
        int reportsNumber = Integer.valueOf(args[1]);

        ExecutorService executor = Executors.newWorkStealingPool();

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
