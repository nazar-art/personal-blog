package net.lelyak.edu.additional_tasks.concurrency.solution;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.additional_tasks.concurrency.ReportingApiClient;
import net.lelyak.edu.additional_tasks.concurrency.SlowReportingApiClient;
import net.lelyak.edu.additional_tasks.concurrency.solution.writer.FileWriter;
import net.lelyak.edu.additional_tasks.concurrency.solution.writer.Writer;
import org.apache.commons.lang3.Validate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
public class MainWithCallableFuture {

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
        int reportsNumber = Integer.parseInt(args[1]);


        Writer writer = new FileWriter(destination);

        AtomicInteger counter = new AtomicInteger(1);
        SlowReportingApiClient client = new SlowReportingApiClient();

        ExecutorService executor = Executors.newWorkStealingPool();
        for (int i = 0; i < reportsNumber; i++) {
            CompletableFuture
                    .supplyAsync(() -> {
                        log.debug("Action ran in: " + Thread.currentThread().getName());

                        String reportName = String.format("report_%d", counter.getAndIncrement());

                        return client.getReport(reportName);
                    }, executor)
                    .thenAccept(writer::write);
        }

        executor.shutdown();
        executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

        log.info("ALL_THREADS_DONE");
    }
}
