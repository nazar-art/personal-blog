package net.lelyak.edu.additional_tasks.concurrency;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This is a fake implementation that generates report with random delay for slow API simulation.
 */
public class SlowReportingApiClient implements ReportingApiClient {

    private static final int REPORT_MAX_GENERATION_TIME_MILLIS = 5000;

    private Random random = new Random();

    @Override
    public Report getReport(String name) {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(REPORT_MAX_GENERATION_TIME_MILLIS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Report(name,
                String.format("This is report [%s] content generated at %s",
                        name, LocalDateTime.now().toString()));
    }
}
