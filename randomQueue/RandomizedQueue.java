
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int SIZE;

    private class Node {
        Item val;
        Node next;
    }

    private Node Head;

    public RandomizedQueue() {
        Head = new Node();
        Head.next = null;
        SIZE = 0;
    }// construct an empty randomized queue

    public boolean isEmpty() {
        return SIZE == 0;
    }// is the queue empty?

    public int size() {
        return SIZE;
    }// return the number of items on the queue

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node temp = new Node();
        temp.val = item;
        temp.next = Head.next;
        Head.next = temp;
        SIZE += 1;
    }// add the item

    public Item dequeue() {
        if (SIZE == 0)
            throw new NoSuchElementException();
        int random = StdRandom.uniform(1, SIZE + 1);
        int i = 0;
        Item out;
        Node re = Head.next;
        Node preRe = Head;
        for (i = 1; i < random; i++) {
            re = re.next;
            preRe = preRe.next;
        }
        out = re.val;
        preRe.next = re.next;
        SIZE -= 1;
        return out;
    }// remove and return a random item

    public Item sample() {
        if (SIZE == 0)
            throw new NoSuchElementException();
        int random = StdRandom.uniform(1, SIZE + 1);
        int i;
        Item out;
        Node re = Head.next;
        for (i = 1; i < random; i++) {
            re = re.next;
        }
        out = re.val;
        return out;
    }// return (but do not remove) a random item

    public Iterator<Item> iterator() {
        return new ListIterator();
    }// return an independent iterator over items in random order

    private class ListIterator implements Iterator<Item> {

        private int[] index = StdRandom.permutation(SIZE);
        private int currentIndex = 0;

        public boolean hasNext() {
            return currentIndex != SIZE;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (currentIndex == SIZE )
                throw new NoSuchElementException();
            Node temp = Head;
            int i;
            for (i = 0; i <= index[currentIndex]; i++)
                temp = temp.next;
            Item out = temp.val;
            currentIndex++;
            return out;
        }
    }

    public static void main(String[] args) {
    }// unit testing (optional)

}