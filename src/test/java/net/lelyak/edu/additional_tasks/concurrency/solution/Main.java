package net.lelyak.edu.additional_tasks.concurrency.solution;

import lombok.extern.slf4j.Slf4j;

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
            log.error("Please provide output file full path and number of working threads");
            log.error("Example: /tmp/reports_folder 500");
            System.exit(0);
        }

        String destination = args[0];
        int threadsNumber = Integer.valueOf(args[1]);

        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        FileWriter fileWriter = new FileWriter(destination);
        CallableReportTask reportTask = new CallableReportTask(fileWriter);

        for (int i = 0; i < threadsNumber; i++) {
            executor.submit(reportTask);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
        }

        log.info("ALL_THREADS_DONE");
    }
}
