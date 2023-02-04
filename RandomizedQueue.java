import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Data Structure used: LinkedList
 *
 * Enqueue: Worst Time complexity: O(1)
 *   Update the firstNode as newNode
 *   Make the newNode point to the previous firstNode
 *
 * Dequeue: Worst Time complexity: O(N)
 *  Case 1: First Node dequeued
 *         Make the next item of current firstNode as firstNode
 *  Case 2: Last Node dequeued
 *         Make the second last Node point to NULL. Worst time complexity O(N)
 *  Case 3: Node from anywhere else dequeued
 *         Make the popped item's previous node point to the next of popped Node
 * */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node firstNode;
    private int size;

    private class Node {
        Item item;
        Node next;

        public Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.firstNode = null;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Node current;

        public RandomizedQueueIterator() {
            this.current = firstNode;
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

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (this.isEmpty()) {
            this.firstNode = new Node(item);
        } else {
            Node newNode = new Node(item);
            newNode.next = firstNode;
            this.firstNode = newNode;
        }
        Node itr = firstNode;
        this.size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        int randIndex = StdRandom.uniform(this.size);
        if (randIndex == this.size) {
            randIndex--;
        }
        Node temp = this.firstNode;
        while (randIndex > 1) {
            temp = temp.next;
            randIndex--;
        }
        Item item;
        if (randIndex == 0) {
            item = temp.item;
            this.firstNode = temp.next;
        } else {
            item = temp.next.item;
            if (temp.next.next != null) {
                temp.next = temp.next.next;
            } else {
                temp.next = null;
            }
        }
        this.size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        int randIndex = StdRandom.uniform(this.size);
        Node temp = this.firstNode;
        while (randIndex > 1) {
            temp = temp.next;
            randIndex--;
        }
        Item item = temp.next.item;
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(rq.dequeue());
            else
                rq.enqueue(s);
        }
    }
}
