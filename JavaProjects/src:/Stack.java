/**
 * A generic stack interface. You can query the size of
 * the stack and ask whether if empty, push items, pop
 * items, and peek at the top item.
 */
public interface Stack<T>{
    /**
     * Adds the given item to the top of the stack.
     */
    void push(T item);

    /**
     * Removes the top item from stack and returns it.
     * @exception java.util.NoSuchElementException.
     */
    T pop();

    /**
     * Returns top item from the stack without popping it.
     * @exception java.util.NoSuchElementException.
     */
    T peek();

    /**
     * Returns the number of items currently in the stack.
     */
    int size();

    /**
     * Returns whether the stack is empty or not.
     */
    boolean isEmpty();
}

