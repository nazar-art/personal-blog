package net.lelyak.edu.additional_tasks.file_change;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * @author Nazar Lelyak.
 */
public class WatchFileChange {
    public static void main(String[] args) throws IOException, InterruptedException {
        final Path path = Paths.get(".");
        final WatchService watchService = path.getFileSystem().newWatchService();

        path.register(watchService, ENTRY_MODIFY);

        System.out.println("Report any file changed within next 1 minute...");


        final WatchKey watchKey = watchService.poll(1, TimeUnit.MINUTES);
        if (watchKey != null) {
            watchKey.pollEvents()
                    .stream()
                    .filter(e -> e.kind().equals(ENTRY_MODIFY))
                    .forEach(event -> System.out.println(event.context()));
        }
    }
}
