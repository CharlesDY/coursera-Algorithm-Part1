
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;


public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int i;
        String S;
        RandomizedQueue<String> Rqueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            S = StdIn.readString();
            Rqueue.enqueue(S);
        }

        Iterator<String> item = Rqueue.iterator();
        for (i = 0; i < k; i++) {
            String s = item.next();
            StdOut.println(s);
        }

    }
}
