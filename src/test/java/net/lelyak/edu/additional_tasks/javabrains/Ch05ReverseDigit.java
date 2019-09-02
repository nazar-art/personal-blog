package net.lelyak.edu.additional_tasks.javabrains;

/**
 * @author Nazar Lelyak.
 */
public class Ch05ReverseDigit {

    public static long reverse(int input) {
        long reversed = 0;

        while (input != 0) {
            reversed = reversed * 10 + input % 10;
            input /= 10;

            if (reversed > Integer.MAX_VALUE || reversed < Integer.MIN_VALUE) {
                return 0;
            }
        }

        return reversed;
    }

    public static void main(String[] args) {
        System.out.println(reverse(54321));
    }
}
