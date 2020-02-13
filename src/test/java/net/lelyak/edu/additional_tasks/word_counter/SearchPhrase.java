package net.lelyak.edu.additional_tasks.word_counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Nazar Lelyak.
 */
class SearchPhrase {

    // walk to root way
    public void walk(String path, String whatFind) throws IOException {

        File root = new File(path);
        File[] list = root.listFiles();
        for (File titleName : list) {
            if (titleName.isDirectory()) {
                walk(titleName.getAbsolutePath(), whatFind);
            } else {
                if (read(titleName.getAbsolutePath()).contains(whatFind)) {
                    System.out.println("File:" + titleName.getAbsolutePath());
                }
            }
        }
    }

    // Read file as one line
    public static String read(String fileName) {
        StringBuilder strBuider = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(
                    fileName)));
            String strInput;
            while ((strInput = in.readLine()) != null) {
                strBuider.append(strInput);
                strBuider.append("\n");
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strBuider.toString();
    }

    public static void main(String[] args) {
        SearchPhrase example = new SearchPhrase();
        example.askUserPathAndWord();
    }

    public void askUserPathAndWord() {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(System.in));
        String path, whatFind;
        try {
            System.out.println("Please, enter a Path and Word"
                    + "(which you want to find):");
            System.out.println("Please enter a Path:");
            path = bufferedReader.readLine();
            System.out.println("Please enter a Word:");
            whatFind = bufferedReader.readLine();

            if (path != null && whatFind != null) {
                walk(path, whatFind);
                System.out.println("Thank you!");
            } else {
                System.out.println("You did not enter anything");
            }

        } catch (IOException | RuntimeException e) {
            System.out.println("Wrong input!");
            e.printStackTrace();
        }
    }
}
