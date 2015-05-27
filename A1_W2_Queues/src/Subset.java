/*----------------------------------------------------------------
 *  Author:        Milenko Adamovic
 *  Written:       28/9/2013
 *  Last updated:  29/9/2013
 *
 *  Compilation:   javac Queues.java
 *  Execution:     
 *  
 *  
 *
 *
 *----------------------------------------------------------------*/
//import java.util.*;

public class Subset {
   public static void main(String[] args) {       
       RandomizedQueue<String> d = new RandomizedQueue<String>();
       
       while (!StdIn.isEmpty()) {
           String s = StdIn.readString();
           d.enqueue(s);           
       }
       
        int firstArg = Integer.parseInt(args[0]);
       
       for (int i = 0; i < firstArg; i++) {
           System.out.println(d.dequeue());
       }      
   } 
}
