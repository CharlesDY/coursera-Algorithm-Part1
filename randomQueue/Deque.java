
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int SIZE;

    private class Node {
        Item val;
        Node next;
        Node pre;
    }

    private Node Head;
    private Node End;

    public Deque() {
        Head = new Node();
        End = new Node();
        Head.next = End;
        End.pre = Head;
        Head.pre = null;
        End.next = null;
        SIZE = 0;
    }// construct an empty deque

    public boolean isEmpty() {
        if (SIZE == 0)
            return true;
        else
            return false;
    }// is the deque empty?

    public int size() {
        return SIZE;
    }// return the number of items on the deque

    private void checkItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    private void checkDeque() {
        if (SIZE == 0)
            throw new NoSuchElementException();
    }

    public void addFirst(Item item) {
        checkItem(item);
        Node temp = new Node();
        temp.val = item;
        temp.pre = Head;
        temp.next = Head.next;
        Head.next.pre = temp;
        Head.next = temp;
        SIZE += 1;
    }// add the item to the front

    public void addLast(Item item) {
        checkItem(item);
        Node temp = new Node();
        temp.val = item;
        temp.pre = End.pre;
        temp.next = End;
        End.pre.next = temp;
        End.pre = temp;
        SIZE += 1;
    }// add the item to the end


    public Item removeFirst() {
        checkDeque();

        Item out = Head.next.val;
        Head.next = Head.next.next;
        Head.next.pre = Head;
        SIZE -= 1;
        return out;
    }// remove and return the item from the front

    public Item removeLast() {
        checkDeque();
        Item out = End.pre.val;
        End.pre = End.pre.pre;
        End.pre.next = End;
        SIZE -= 1;
        return out;
    }// remove and return the item from the end

    public Iterator<Item> iterator() {
        return new ListIterator();
    }// return an iterator over items in order from front to end

    private class ListIterator implements Iterator<Item> {
        private Node current = Head.next;

        public boolean hasNext() {
            return current != End;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == End)
                throw new NoSuchElementException();
            Item out = current.val;
            current = current.next;
            return out;
        }
    }

    public static void main(String[] args) {

    }   // unit testing (optional)
}
