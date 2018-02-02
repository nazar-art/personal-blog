package net.lelyak.edu.additional_tasks.concurrency.solution;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.additional_tasks.concurrency.solution.writer.FileWriter;
import net.lelyak.edu.additional_tasks.concurrency.solution.writer.Writer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            log.error("Incorrect usage:");
            log.error("Please provide output file full path and number of final reports");
            log.error("Example: /tmp/reports_folder 500");
            System.exit(0);
        }

        String destination = args[0];
        int reportsNumber = Integer.valueOf(args[1]);

        ExecutorService executor = Executors.newFixedThreadPool(reportsNumber);

        Writer fileWriter = new FileWriter(destination);
        CallableReportTask reportTask = new CallableReportTask(fileWriter);

        for (int i = 0; i < reportsNumber; i++) {
            executor.submit(reportTask);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
        }

        log.info("ALL_THREADS_DONE");
    }
}
