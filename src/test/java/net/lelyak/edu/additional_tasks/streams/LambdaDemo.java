package net.lelyak.edu.additional_tasks.streams;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * @author Nazar Lelyak.
 */
public class LambdaDemo {
    public static void main(String[] args) {
        List<Person2> people = Arrays.asList(
                new Person2("Charles", "Dickens", 60),
                new Person2("Lewis", "Carroll", 42),
                new Person2("Thomas", "Carlyle", 51),
                new Person2("Charlotte", "Bronte", 45),
                new Person2("Matthew", "Arnold", 45)
        );


        // sort list by last name
        System.out.println("SORTED BY LAST NAME:");
        people.stream()
                .sorted(comparing(Person2::getLastName))
                .forEach(System.out::println);

//        System.out.println(sortedByLastName);

        // create a method that prints all element in a list
        printAll(people);

        // method that print all people which last name starts with C
        printWithCondition(people);

    }

    private static void printWithCondition(List<Person2> people) {
        System.out.println("\nLAST NAMES WHICH START WITH \"C\":");
        people.stream()
                .filter(p -> p.getLastName().startsWith("C"))
                .forEach(System.out::println);
    }

    private static void printAll(List<Person2> people) {
        System.out.println("\nALL ELEMENTS:");
        people.forEach(System.out::println);
    }
}
