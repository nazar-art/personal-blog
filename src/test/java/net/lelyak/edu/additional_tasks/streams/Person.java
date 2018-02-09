package net.lelyak.edu.additional_tasks.streams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Nazar Lelyak.
 */
@Data
@AllArgsConstructor
public class Person {
    private String name;
    private int age;
//    private String nationality;

    public int ageDifference(Person person) {
        return this.age - person.age;
    }
}
