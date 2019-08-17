package net.lelyak.edu.additional_tasks.javabrains;

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
        Stack stack = new Stack(4);

        stack.push(12);
        stack.push(42);
        stack.push(52);
        stack.push(62);

        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
