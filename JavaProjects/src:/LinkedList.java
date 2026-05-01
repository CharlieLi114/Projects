/**
 * LinkedList.java
 *
 * A generic singly linked list supporting essential operations:
 * size, indexOf, addFirst, remove(at index), and toString.
 * Nodes store a value of type T and a reference to the next node.
 *
 * @author Chengyu Li <lic24@wfu.edu>
 * @version 1.0, Nov 2025
 */

import java.util.NoSuchElementException;

public class LinkedList<T> implements Stack<T> {

    /** Node in a singly linked list. */
    private class Node {
        T data;     // stored value
        Node next;  // pointer to next node

        Node(T d, Node n) {
            data = d;
            next = n;
        }
    }

    private Node head;  // start of list

    /**
     * Construct an empty list.
     */
    public LinkedList() {
        head = null;
    }

    /**
     * Return the number of elements currently in the list.
     *
     * @return the element count
     */
    public int size() {
        int count = 0;
        Node curr = head;
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    /**
     * Return the index of the first occurrence of value, or -1 if absent.
     * Comparison uses value.equals(x).
     * The head of the list has index 0.
     *
     * @param value the value to locate
     * @return the index of value, or -1 if not found
     */
    public int indexOf(T value) {
        Node curr = head;
        int idx = 0;

        while (curr != null) {
            if ((value == null && curr.data == null) ||
                    (value != null && value.equals(curr.data))) {
                return idx;
            }
            curr = curr.next;
            idx++;
        }
        return -1;
    }

    /**
     * Insert value at the front of the list as the new head.
     *
     * @param value the value to insert
     */
    public void addFirst(T value) {
        head = new Node(value, head);
    }

    /**
     * Remove and return the element at index loc, or null if invalid.
     * Index 0 corresponds to the head.
     *
     * @param loc the index to remove
     * @return the removed value, or null if loc is invalid or list empty
     */
    public T remove(int loc) {
        if (loc < 0 || head == null) {
            return null;
        }

        // remove head
        if (loc == 0) {
            T val = head.data;
            head = head.next;
            return val;
        }

        Node prev = head;
        int i = 0;
        // move prev to the node just before loc
        while (prev != null && i < loc - 1) {
            prev = prev.next;
            i++;
        }

        if (prev == null || prev.next == null) {
            return null;
        }

        Node doomed = prev.next;
        prev.next = doomed.next;
        return doomed.data;
    }

    /**
     * Return a bracketed, comma separated representation of the list.
     * Format example: [a, b, c]
     *
     * @return the string representation of this list
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Node curr = head;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null) {
                sb.append(", ");
            }
            curr = curr.next;
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public void push(T item) {
        // top of the stack is the head
        addFirst(item);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        T value = head.data;
        head = head.next;
        return value;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return head.data;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
