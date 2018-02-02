package net.lelyak.edu.additional_tasks.concurrency.solution;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.additional_tasks.concurrency.ReportingApiClient;
import net.lelyak.edu.additional_tasks.concurrency.SlowReportingApiClient;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
public class CallableReportTask implements Callable<String> {
    public static final String REPORT_PREFIX = "report";

    private AtomicInteger counter = new AtomicInteger(1);

    private Writer writer;

    public CallableReportTask(Writer writer) {
        this.writer = writer;
    }

    @Override
    public String call() throws Exception {
        String currentThread = Thread.currentThread().getName();
        log.debug("{} thread started", currentThread);

        String reportName = String.format("%s_%d", REPORT_PREFIX, counter.getAndIncrement());

        SlowReportingApiClient client = new SlowReportingApiClient();
        ReportingApiClient.Report report = client.getReport(reportName);

        log.info("Get report: {}", report);

        writer.write(report);

        return reportName;
    }
}
