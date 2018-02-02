package net.lelyak.edu.additional_tasks.concurrency.solution.writer;

import net.lelyak.edu.additional_tasks.concurrency.SlowReportingApiClient;

/**
 * @author Nazar Lelyak.
 */
public interface Writer {
    void write(SlowReportingApiClient.Report report);
}
