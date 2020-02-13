package net.lelyak.edu.additional_tasks.word_counter;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Nazar Lelyak.
 */
public class WordCounter implements SearchWord {

    private ExecutorService exec = Executors.newWorkStealingPool();

    /**
     * Callable<Long> task = () -> { .., return count; }
     * Future<Long> result = exec.submit(task);
     */
    @Override
    public void walk(String dirToScan, String wordToFind) {

        Callable<Long> task = () -> {
            return null;
        };

    }
}
