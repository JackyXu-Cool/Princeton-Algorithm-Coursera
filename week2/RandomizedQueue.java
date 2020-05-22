import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{

    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private boolean isFull() {
        return size == queue.length;
    }

    private boolean quarterFull() {
        return size == queue.length / 4;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if(isFull()) {
            resize(queue.length * 2);
        }
        queue[size++] = item;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < queue.length; i++) {
           temp[i] = queue[i];
        }
        queue = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int randomIndex = (int) (Math.random() * size);
        while (queue[randomIndex] == null) {
            randomIndex = (int) (Math.random() * size);
        }
        Item removed = queue[randomIndex];
        for (int i = randomIndex; i < size; i++) {
            if (i == queue.length - 1) {
                queue[i] = null;
            } else {
                queue[i] = queue[i + 1];
            }
        }
        size--;
        if (quarterFull()) {
            resize(queue.length / 2);
        }
        return removed;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int randomIndex = (int) (Math.random() * size);
        return queue[randomIndex];
    }

    private Item[] getQueue() {
        return queue;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator(getQueue());
    }

    private class RandomIterator implements Iterator<Item> {
        private Item[] copy;
        private int index;

        public RandomIterator(Item[] input) {
            copy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copy[i] = input[i];
            }
            StdRandom.shuffle(copy);
            index = 0;
        }
        public boolean hasNext() {
            return index < size;
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy[index++];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(2);
        rq.enqueue(8);
        rq.enqueue(10);
        rq.enqueue(12);
        rq.enqueue(30);
        rq.dequeue();
        for (Integer integer: rq) {
            System.out.println(integer);
        }
    }
}
