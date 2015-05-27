/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milenko
 */
import java.util.*;

public class Queues {
   public static void main(String[] args) {
       //System.out.println("Hello world");
       
      /*Deque<String> d = new Deque<String>();
       
       while (!StdIn.isEmpty()) {
           String s = StdIn.readString();
           d.addFirst(s);           
       }
       
       for(String s:d) {
           System.out.println(s);
       }*/
       
       RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
       rq.enqueue(10);
       rq.enqueue(20);
       rq.enqueue(30);
       rq.enqueue(40);
       rq.enqueue(50);
       
       /*StdOut.println(rq.sample());
       StdOut.println(rq.sample());
       StdOut.println(rq.sample());
       StdOut.println(rq.sample());
       StdOut.println(rq.sample());*/
       
       /*while (!rq.isEmpty()) {
           StdOut.println(rq.dequeue());
       }*/
       for (Integer i : rq)
           StdOut.println(i);
       /*Iterator<Integer> i = rq.iterator();
       while (i.hasNext()) {
           StdOut.println(i.next());
       }*/
       
//       for (int i = 0; i < 10;i++) {
//       StdOut.println(StdRandom.uniform(10));
//       }
       
       
       
   }
    
}
