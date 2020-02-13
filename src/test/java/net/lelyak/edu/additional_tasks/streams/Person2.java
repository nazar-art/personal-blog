package net.lelyak.edu.additional_tasks.streams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Nazar Lelyak.
 */
@Data
@Builder
@AllArgsConstructor
public class Person2 {
    private String name;
    private String lastName;
    private int age;

    public int ageDifference(Person2 person) {
        return this.age - person.age;
    }
}
