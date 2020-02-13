package net.lelyak.edu.additional_tasks.word_counter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Nazar Lelyak.
 */
@FunctionalInterface
public interface SearchWord {


    void walk(String dirToScan, String wordToFind);

    default String readFile(File file) {
        // or use reading by path -> new String(Files.readAllBytes(Paths.get("duke.java")));
        String result = "";
        try {
            result = FileUtils.readFileToString(file);
        } catch (IOException e) {
            System.out.println("Exception with reading a file: " + file.getName());
        }
        return result;
    }
}
