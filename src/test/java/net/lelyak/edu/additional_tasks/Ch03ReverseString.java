package net.lelyak.edu.additional_tasks;

/**
 * @author Nazar Lelyak.
 */
public class Ch03ReverseString {
    public static void main(String[] args) {
        System.out.println(reverseString("hello"));
        System.out.println(reverseString("корок"));
        System.out.println(reverseString("this!!!"));
    }

    private static String reverseString(String str) {
        StringBuilder builder = new StringBuilder();

        for (int i = str.length(); i > 0; i--) {
            builder.append(str.substring(i - 1, i));
        }
        return builder.toString();
    }
}
