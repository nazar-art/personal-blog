package net.lelyak.edu.additional_tasks.javabrains;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Nazar Lelyak.
 */
class Stack {
    private int array[];
    private int top;
    private int capacity;

    public Stack(int capacity) {
        this.array = new int[capacity];
        this.capacity = capacity;
        this.top = -1;
    }

    public void push(int item) {
        if (isFull()) {
            throw new RuntimeException("Stack is full");
        }
        array[++top] = item;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return array[top--];
    }


    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return array[top];
    }


    private boolean isFull() {
        return top == capacity - 1;
    }

    private boolean isEmpty() {
        return top == -1;
    }
}

public class Ch04StackImplementation {

    public static void main(String[] args) {
//        Stack stack = new Stack(4);
//
//        stack.push(12);
//        stack.push(42);
//        stack.push(52);
//        stack.push(62);
//
//        System.out.println(stack.peek());
//        System.out.println(stack.pop());
//        System.out.println(stack.pop());
//        System.out.println(stack.pop());
//        System.out.println(stack.pop());
//        System.out.println(stack.pop());

        StackImpl stack = new StackImpl();
        stack.push(8);
        stack.push(7);
        stack.push(6);
        stack.push(5);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
//        System.out.println(stack.pop());
    }
}

class StackImpl {
    public static final int STACK_SIZE = 10;

    private int[] array;
    private int index;

    public StackImpl() {
        array = new int[STACK_SIZE];
        index = 0;
    }

    public void push(int number) {
        if (index == STACK_SIZE) {
            throw new RuntimeException("stack is full");
        }
        array[index++] = number;
    }

    public int pop() {
        if (index == 0) {
            throw new RuntimeException("stack is empty");
        }
        return array[--index];
    }

    public int summary(List<Integer> numbers) {
//        int total = 0;
//
//        for (Integer number : numbers) {
//            if (number % 2 == 0) {
//                total += number;
//            }
//        }
//
//        return total;
//
//        return numbers.stream()
//                .filter(n -> n % 2 == 0)
//                .reduce(0, Integer::sum);

        return numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(Integer::intValue)
                .sum();
    }


}

final class ImmutableItem {
    private final String value;
    private final Date date;
    private final List<String> elements;

    public ImmutableItem(String value, Date date, List<String> elements) {
        this.value = value;
        this.date = date;
        this.elements = Collections.unmodifiableList(elements);
    }

    public String getValue() {
        return value;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public List<String> getElements() {
        return elements;
    }
}

interface SearchUtils {

    Optional<List<String>> search();

    Optional<String> searchCriteria(Optional<String> criteria);

}