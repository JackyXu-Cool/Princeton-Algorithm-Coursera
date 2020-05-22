import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> input = new RandomizedQueue<>();
        while(!StdIn.isEmpty()) {
            input.enqueue(StdIn.readString());
        }
        Iterator<String> iterator = input.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(iterator.next());
        }
    }
}
