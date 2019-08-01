package net.lelyak.edu.additional_tasks.avarage_counter;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class AverageCounterOld {

    private ArrayList<Double> numbers = new ArrayList<>();

    /**
     * Write implementation for calculating average for inputs:
     * numberAverage(1) -> 1
     * numberAverage(3) -> 2
     * numberAverage(11) -> 5
     * numberAverage(5) -> 5
     */
    double numberAverage(double number) {
        numbers.add(number);

        int counter = 0;
        double result = 0;

        while (counter < numbers.size()) {
            result += numbers.get(counter);
            counter++;
        }

        result = result / numbers.size();
        System.out.println(result);
        return result;
    }
}

class AverageCounterImproved {
    private BigInteger total = BigInteger.ZERO;
    private BigInteger numbersCounter = BigInteger.ZERO;

    double numberAverage(long number) {
        total = total.add(BigInteger.valueOf(number));
        numbersCounter = numbersCounter.add(BigInteger.ONE);
        
        double result = total.divide(numbersCounter).doubleValue();
        System.out.printf("%d / %d  -> %s\n", total, numbersCounter, result);
        
        return result;
    }
}

public class AverageCounterTest {

    private AverageCounterOld old;
    private AverageCounterImproved improved;

    @Before
    public void setUp() {
        old = new AverageCounterOld();
        improved = new AverageCounterImproved();
    }

    @Test
    public void testAddOld() {
        assertEquals(1, old.numberAverage(1), 0.01);
        assertEquals(2, old.numberAverage(3), 0.01);
        assertEquals(5, old.numberAverage(11), 0.01);

//        assertEquals(5.368709155E8, old.numberAverage(Integer.MAX_VALUE), 0.01);
    }

    @Test
    public void testAddImproved() {
        assertEquals(1, improved.numberAverage(1), 0.01);
        assertEquals(2, improved.numberAverage(3), 0.01);
        assertEquals(5, improved.numberAverage(11), 0.01);
        assertEquals(5, improved.numberAverage(5), 0.01);
        assertEquals(6, improved.numberAverage(10), 0.01);
        assertEquals(6, improved.numberAverage(6), 0.01);

//        assertEquals(5.36870915E8, improved.numberAverage(Integer.MAX_VALUE), 0.01);
    }
}
