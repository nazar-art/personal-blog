package net.lelyak.edu.additional_tasks.block_iterator;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
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
 * regexp = ".*test.*”
 * 
 * Перший виклик методу next() на колекції lines дає таку колекцію (підсписок - від першого входження регекспу і до наступного, але не включаючи його):
 * next() --> [ "- test -“, "start”, "end”, ]
 * 
 * Другий виклик методу next() повертає ось такий підсписок (від наступного входження регекспу і до кінця, оскільки наступного входження вже немає):
 * next() --> [ "test123” ]
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
}
