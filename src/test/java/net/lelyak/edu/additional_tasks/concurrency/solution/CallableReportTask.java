package net.lelyak.edu.additional_tasks.concurrency.solution;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.additional_tasks.concurrency.ReportingApiClient;
import net.lelyak.edu.additional_tasks.concurrency.SlowReportingApiClient;
import net.lelyak.edu.additional_tasks.concurrency.solution.writer.Writer;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
public class CallableReportTask implements Callable<String> {

    public static final String REPORT_PREFIX = "report";

    private AtomicInteger counter;
    private Writer writer;
    private SlowReportingApiClient client;

    public CallableReportTask(Writer writer) {
        this.writer = writer;
        this.counter = new AtomicInteger(1);
        this.client = new SlowReportingApiClient();
    }

    @Override
    public String call() throws Exception {
        String currentThread = Thread.currentThread().getName();
        log.debug("{} thread started", currentThread);

        String reportName = String.format("%s_%d", REPORT_PREFIX, counter.getAndIncrement());

        ReportingApiClient.Report report = client.getReport(reportName);

        log.info("Get report: {}", report);

        writer.write(report);

        return reportName;
    }
}
