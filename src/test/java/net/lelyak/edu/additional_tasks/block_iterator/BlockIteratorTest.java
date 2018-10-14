package net.lelyak.edu.additional_tasks.block_iterator;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Треба написати клас BlockIterator - реалізацію інтерфейсу Iterator.
 * 
 * class BlockIterator implements Iterator<List<String>> {
 * BlockIterator(Iterator<String> lines, String regexp);
 * boolean hasNext() { … } List<String>
 * next() {
 * …
 * }
 * }
 * 
 * Конструктор на вхід приймає ітератор по колекції рядків та регулярний вираз.
 * 
 * Для пояснення постановки задачі та для написання тестів використовується такий приклад:
 * 
 * Це колекція рядків (перший параметр конструктора):
 * lines = [ "123", "- test -", "start", "end", "test123", ]
 * 
 * Це регексп:
 * regexp = ".*test.*"
 * 
 * Перший виклик методу next() на колекції lines дає таку колекцію (підсписок - від першого входження регекспу і до наступного, але не включаючи його):
 * next() --> [ "- test -", "start", "end", ]
 * 
 * Другий виклик методу next() повертає ось такий підсписок (від наступного входження регекспу і до кінця, оскільки наступного входження вже немає):
 * next() --> [ "test123" ]
 * 
 * Бізнес-логіка методу hasNext() подібна до next(), але замість списку повертається true/false
 * 
 * Код потрібно покрити юніт-тестами (JUnit)
 * 
 * Час на реалізацію (разом з тестами) - 40-50 хв
 * 
 * Важливий коментар – конструктор приймає на вхід не колекцію, а ітератор: BlockIterator(Iterator<String> lines, String regexp); Багатьох кандидатів це конф’юзить
 * 
 * Другий важливий коментар – в конструкторі вхідні дані не можна заганяти в пам’ять (список, масив і т.д.), чи робити ще якісь перетворення. Відні дані можуть бути дуже великими (десятки мегабайт) – завантажувати в пам’ять не можна, треба обробляти на льоту.
 * 
 * Третій і останній важливий нюанс – на співбесіді з Пейманом треба зробити так, щоб у нього склалось враження, що ти це завдання бачиш перший раз :)
 * 
 * І ще один момент: Метод hasNext() - ідемпотентний: якщо його викликати один, два, десять разів – результат має бути однаковий
 */
class BlockIterator implements Iterator<List<String>> {

    private final Iterator<List<String>> iterator;
    private final Pattern pattern;

    private Boolean hasNext;
    private List<String> next = null;
    private String startNext = null;

    public BlockIterator(Iterator<List<String>> iterator, String regex) {
        this.iterator = iterator;
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean hasNext() {
        if (hasNext == null) {
            hasNext = checkNext();
        }
        return hasNext;
    }

    private boolean checkNext() {
        List<String> result = Lists.newArrayList();
        String matchWord = null;

        if (null != startNext) {
            result.add(startNext);
        }

        while (iterator.hasNext()) {
            List<String> line = iterator.next();
            for (String word : line) {
                Matcher matcher = pattern.matcher(word);
                if (matcher.find()) {
                    if (null != matchWord || null != startNext) {
                        next = result;
                        startNext = word;
                        return true;
                    } else {
                        matchWord = word;
                    }
                }
                if (null != matchWord || null != startNext) {
                    result.add(word);
                }
            }
        }
        next = result;
        startNext = null;
        return !next.isEmpty();
    }

    @Override
    public List<String> next() {
        hasNext = checkNext();
        return next;
    }
}

class ImprovedAlternateIterator<E> implements Iterator {

    /**
     * Stores the iterators which are to be alternated on.
     */
    private Iterator<E>[] iterators;

    /**
     * The index of iterator, which has the next element.
     */
    private int nextIterator = 0;

    /**
     * Initializes a new AlternatingIterator object.
     * Stores the iterators in the iterators field.
     * Finds the first iterator with an available element.
     */
    public ImprovedAlternateIterator(Iterator<E> ... iterators) {
        this.iterators = iterators;

        if (!iterators[0].hasNext())
            findNextIterator();
    }

    @Override
    public boolean hasNext() {
        return iterators[nextIterator].hasNext();
    }

    @Override
    public Object next() {
        E element = iterators[nextIterator].next();

        findNextIterator();

        return element;
    }

    /**
     * Steps on iterators, until one has next element.
     * It does not step on them infinitely, stops when
     * the lastly used iterator is reached.
     */
    private void findNextIterator() {
        int currentIterator = nextIterator;

        // Finding iterator with element remaining.
        do {
            stepNextIterator();
        } while (!iterators[nextIterator].hasNext() && nextIterator != currentIterator);
        // If it gets around to the same iterator, then there is no iterator with element.
    }

    /**
     * Increases the nextIterator value without indexing out of bounds.
     */
    private void stepNextIterator() {
        nextIterator = (nextIterator + 1) % iterators.length;
    }
}


public class BlockIteratorTest {

    public static final List<List<String>> lines = Lists.newArrayList(
            Lists.newArrayList("123"),
            Lists.newArrayList("- test -"),
            Lists.newArrayList("start"),
            Lists.newArrayList("end"),
            Lists.newArrayList("test123"));

    @Test
    public void testNext() throws Exception {
        List<String> expectedFirstNext = Lists.newArrayList("- test -", "start", "end");
        List<String> expectedSecondNext = Lists.newArrayList("test123");

        BlockIterator blockIterator = new BlockIterator(lines.iterator(), "test");

        List<String> actualFirstNext = blockIterator.next();
        assertEquals(expectedFirstNext, actualFirstNext);

        List<String> actualSecondNext = blockIterator.next();
        assertEquals(expectedSecondNext, actualSecondNext);
    }

    @Test
    public void testHasNext() throws Exception {
        BlockIterator blockIterator = new BlockIterator(lines.iterator(), "test");

        for (int i = 0; i < 20; i++) {
            assertTrue(blockIterator.hasNext());
        }
    }

    private ImprovedAlternateIterator<Iterator> improvedIterator;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> list1 = Lists.newArrayList("A", "B", "C");
        ArrayList<String> list2 = Lists.newArrayList("x", "y", "z");
        ArrayList<Integer> list3 = Lists.newArrayList(1, 2);

        // ListIterator to traverse the list
        ListIterator iterator1 = list1.listIterator();
        ListIterator iterator2 = list2.listIterator();
        ListIterator iterator3 = list3.listIterator();

        improvedIterator = new ImprovedAlternateIterator<Iterator>(iterator1, iterator2, iterator3);
    }

    @After
    public void tearDown() throws Exception {
        improvedIterator = null;
    }

    /**
     * check that hasNext() is idempotent -> even if you call it 1 or 10 times the result should be the same
     */
    @Test
    public void testHasNextForAlternateIterator() {
        for (int i = 0; i < 20; i++) {
            assertTrue(improvedIterator.hasNext());
        }
    }

    /**
     * check that next() for iterator
     * it should return first element per each iterator
     */
    @Test
    public void testNextForAlternateIterator() {
        String expectedFromFirstIterator = "A";
        String expectedFromSecondIterator = "x";
        int expectedFromThirdIterator = 1;

        assertEquals("First element from first iterator isn't retrieved", expectedFromFirstIterator, improvedIterator.next());
        assertEquals(expectedFromSecondIterator, improvedIterator.next());
        assertEquals(expectedFromThirdIterator, improvedIterator.next());

        String expected2FromFirstIterator = "B";
        String expected2FromSecondIterator = "y";
        int expected2FromThirdIterator = 2;

        assertEquals(expected2FromFirstIterator, improvedIterator.next());
        assertEquals(expected2FromSecondIterator, improvedIterator.next());
        assertEquals(expected2FromThirdIterator, improvedIterator.next());

        // you can omit following section if you don't need to cover it
        String expected3FromFirstIterator = "C";
        String expected3FromSecondIterator = "z";

        assertEquals(expected3FromFirstIterator, improvedIterator.next());
        assertEquals(expected3FromSecondIterator, improvedIterator.next());
    }
}
