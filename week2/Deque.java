import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    private class Node {
        Node next;
        Item item;
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
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node previousFirst = first;
        first = new Node();
        first.item = item;
        first.next = previousFirst;
        size++;
        if (size == 1) {
            last = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node previousLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (size == 0) {
            first = last;
        } else {
            previousLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item removed = first.item;
        first = first.next;
        size--;
        return removed;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item removed = last.item;
        Node current = first;
        if (size == 1) {
            last = null;
            first = null;
            size--;
            return removed;
        }
        for (int i = 0; i < size - 2; i++) {
            current = current.next;
        }
        last = current;
        last.next = null;
        size--;
        return removed;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item currentItem = current.item;
            current = current.next;
            return currentItem;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        System.out.println(dq.isEmpty());
//        dq.addFirst(2);
        dq.addLast(3);
//        dq.addLast(4);
        dq.removeLast();
        for (Integer integer: dq) {
            System.out.println(integer);
        }
    }

}
