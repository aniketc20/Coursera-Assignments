import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;

        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            this.current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                Node node = current;
                current = current.next;
                return node.item;
            }
        }
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.isEmpty()) {
            this.first = new Node(item);
            this.last = first;
        } else {
            Node node = new Node(item);
            node.next = this.first;
            this.first.prev = node;
            this.first = node;
        }
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.isEmpty()) {
            this.last = new Node(item);
            this.first = last;
        } else {
            Node node = new Node(item);
            this.last.next = node;
            node.prev = this.last;
            this.last = node;
        }
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Node node = first;
        if (this.size() == 1) {
            this.first = null;
            this.last = null;
        } else {
            this.first.next.prev = null;
            this.first = this.first.next;
        }
        this.size--;
        node.next = null;
        return node.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Node node = last;
        if (this.size() == 1) {
            this.first = null;
            this.last = null;
        } else {
            this.last.prev.next = null;
            this.last = this.last.prev;
        }
        this.size--;
        node.prev = null;
        return node.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(deque.removeLast());
            else
                deque.addFirst(s);
        }
    }
}
