package net.lelyak.edu.additional_tasks.word_counter;

/**
 * @author Nazar Lelyak.
 */

import java.io.IOException;

public class SearchDemo {

    private SearchPhrase example = new SearchPhrase();

    public void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            example.walk("E:\\Document\\!Nazar\\АНГЛІЙСЬКА\\"
                    + "Effortless English\\Effortless English-New method learning english\\Level 3", "spot");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }
}
