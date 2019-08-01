package net.lelyak.edu.additional_tasks;

/**
 * @author Nazar Lelyak.
 */
public class Ch01FizzBuzz {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            checkNumber(i);
        }
    }

    private static void checkNumber(int number) {
        boolean ifDivideBy3 = number % 3 == 0;
        boolean ifDivideBy5 = number % 5 == 0;

        String res = (ifDivideBy3 && ifDivideBy5) ? "FizzBuzz"
                : (ifDivideBy3 ? "Fizz"
                : (ifDivideBy5 ? "Buzz"
                : String.valueOf(number)));

        System.out.println(res);
    }
}
