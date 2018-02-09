package net.lelyak.edu.additional_tasks.streams;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
public class StreamTests {

    @Test
    public void transformShouldConvertCollectionElementsToUpperCase() {
        List<String> collection = asList("My", "name", "is", "John", "Doe");
        List<String> expected = asList("MY", "NAME", "IS", "JOHN", "DOE");

        assertThat(transform(collection)).hasSameElementsAs(expected);
    }

    private List<String> transform(List<String> collection) {
        return collection.stream()
                .map(String::toUpperCase)
                .collect(toList());
    }

    @Test
    public void transformShouldFilterCollection() {
        List<String> collection = asList("My", "name", "is", "John", "Doe");
        List<String> expected = asList("My", "is", "Doe");

        assertThat(transformFilter(collection)).hasSameElementsAs(expected);
    }

    private List<String> transformFilter(List<String> collection) {
        return collection.stream()
                .filter(word -> word.length() < 4)
                .collect(toList());
    }

    @Test
    public void transformShouldFlattenCollection() {
        List<List<String>> collection = asList(asList("Viktor", "Farcic"), asList("John", "Doe", "Third"));
        List<String> expected = asList("Viktor", "Farcic", "John", "Doe", "Third");

        assertThat(transformFlatMap(collection)).hasSameElementsAs(expected);
    }

    private List<String> transformFlatMap(List<List<String>> collection) {
        return collection.stream()
                .flatMap(Collection::stream)
                .collect(toList());
    }

    @Test
    public void getOldestPersonShouldReturnOldestPerson() {
        Person sara = new Person("Sara", 4);
        Person viktor = new Person("Viktor", 40);
        Person eva = new Person("Eva", 42);
        List<Person> collection = asList(sara, eva, viktor);

        assertThat(getOldestPerson(collection)).isEqualToComparingFieldByField(eva);
    }

    private Person getOldestPerson(List<Person> collection) {
        return collection.stream().max(Person::ageDifference)
                .orElseThrow(IllegalArgumentException::new);
    }


    @Test
    public void calculateShouldConvertCollectionElementsToSum() {
        List<Integer> numbers = asList(1, 2, 3, 4, 5);

        assertThat(calculate(numbers)).isEqualTo(1 + 2 + 3 + 4 + 5);
    }

    private Integer calculate(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }


    @Test
    public void getKidNameShouldReturnNamesOfAllKidsYoungerThan18() {
        Person sara = new Person("Sara", 4);
        Person viktor = new Person("Viktor", 40);
        Person eva = new Person("Eva", 42);
        Person anna = new Person("Anna", 5);
        List<Person> collection = asList(sara, eva, viktor, anna);

        assertThat(getKidNames(collection))
                .contains("Sara", "Anna")
                .doesNotContain("Viktor", "Eva");
    }

    private Set<String> getKidNames(List<Person> collection) {
        return collection.stream()
                .filter(person -> person.getAge() < 18)
                .map(Person::getName)
                .collect(Collectors.toSet());
    }


    @Test
    public void peopleStatsSpec() throws Exception {
        Person sara = new Person("Sara", 4);
        Person viktor = new Person("Viktor", 40);
        Person eva = new Person("Eva", 42);
        List<Person> collection = asList(sara, eva, viktor);

        assertThat(getStats(collection).getAverage()).isEqualTo((double) (4 + 40 + 42) / 3);

        assertThat(getStats(collection).getCount()).isEqualTo(3);

        assertThat(getStats(collection).getMax()).isEqualTo(42);
        
        assertThat(getStats(collection).getMin()).isEqualTo(4);
        
        assertThat(getStats(collection).getSum()).isEqualTo(40 + 42 + 4);
    }

    private IntSummaryStatistics getStats(List<Person> collection) {
        return collection.stream()
                .mapToInt(Person::getAge)
                .summaryStatistics();
    }

    @Test
    public void partitionAdultsShouldSeparateKidsFromAdults() {
        Person sara = new Person("Sara", 4);
        Person viktor = new Person("Viktor", 40);
        Person eva = new Person("Eva", 42);
        List<Person> collection = asList(sara, eva, viktor);
        
        Map<Boolean, List<Person>> result = partitionAdults(collection);
        assertThat(result.get(true)).hasSameElementsAs(asList(viktor, eva));
        assertThat(result.get(false)).hasSameElementsAs(asList(sara));
    }

    private Map<Boolean, List<Person>> partitionAdults(List<Person> collection) {
        return collection.stream()
                .collect(partitioningBy(person -> person.getAge() > 18));
    }

    /*@Test
    public void partitionAdultsShouldGroupKids() {
        Person sara = new Person("Sara", 4, "Norwegian");
        Person viktor = new Person("Viktor", 40, "Serbian");
        Person eva = new Person("Eva", 42, "Norwegian");
        List<Person> collection = asList(sara, eva, viktor);

        Map<String, List<Person>> result = groupByNationality(collection);
        assertThat(result.get("Norwegian")).hasSameElementsAs(asList(sara, eva));
        assertThat(result.get("Serbian")).hasSameElementsAs(asList(viktor));
    }

    private Map<String, List<Person>> groupByNationality(List<Person> collection) {
        return collection.stream()
                .collect(groupingBy(Person::getNationality));
    }*/

    @Test
    public void toStringShouldReturnPeopleNamesSeparatedByComma() {
        Person sara = new Person("Sara", 4);
        Person viktor = new Person("Viktor", 40);
        Person eva = new Person("Eva", 42);
        List<Person> collection = asList(sara, viktor, eva);

        assertThat(namesToString(collection))
                .isEqualTo("Names: Sara, Viktor, Eva.");
    }

    private String namesToString(List<Person> collection) {
        return collection.stream()
                .map(Person::getName)
                .collect(joining(", ", "Names: ", "."));
    }


}
