package net.lelyak.edu.block_iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private Matcher regexMatcher;

    public BlockIterator(Iterator<String> lines, String pattern) {
        if (lines.hasNext()) {
            String line = lines.next();
            System.out.println("line: " + line);

            Pattern regex = Pattern.compile(pattern);
            regexMatcher = regex.matcher(line);
        }
    }

    @Override
    public boolean hasNext() {
        return regexMatcher.find();
    }

    @Override
    public List<String> next() {
        List<String> results = new ArrayList<>();
        while (regexMatcher.find()) {
            results.add(regexMatcher.group());
        }
        return results;
    }
}

public class BlockIteratorTest {

    public static final List<String> lines = Arrays.asList("123", "- test -", "start", "end", "test123");

    public static void main(String[] args) {
        System.out.println(lines);

        BlockIterator blockIterator = new BlockIterator(lines.iterator(), ".*test.*");
        while (blockIterator.hasNext()) {
            System.out.println(blockIterator.next());
        }
    }

}
