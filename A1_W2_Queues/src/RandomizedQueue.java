/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milenko
 */

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] s;
    
    private int N = 0;
    // construct an empty randomized queue
   public RandomizedQueue() {
       s = (Item[]) new Object[1];
       
   }   
   
   // is the queue empty?
   public boolean isEmpty() {
       return N == 0;
   }
   
   // return the number of items on the queue
   public int size() {
       return N;
   }  
   
   private void resize(int capacity) {
       Item[] copy = (Item[]) new Object[capacity];
       for (int i = 0; i < N; i++) {
           copy[i] = s[i];
       }
       s = copy;
   }  
   
   // add the item
   public void enqueue(Item item) {
       if (item == null) {
           throw new java.lang.NullPointerException();
       }
       
       if (N == s.length) {
           resize(2 * N);
       }
       s[N++] = item;
       
   }   
   
   // delete and return a random item
   public Item dequeue() {
       if (N == 0) {
           throw new java.util.NoSuchElementException();
       }
       
       // take a random item and swap it with the last
       int pos = StdRandom.uniform(N);
       Item temp = s[pos];
       s[pos] = s[N-1];
       s[N-1] = temp;
          
       // remove last item
       Item item = s[--N];
       s[N] = null; 
       if (N > 0 && N == s.length/4) {
           resize(s.length/2);
       }
       return item;     
   } 
   
  // return (but do not delete) a random item
   public Item sample() {
       if (N == 0) {
           throw new java.util.NoSuchElementException();
       }
       
       int pos = StdRandom.uniform(N);
       return s[pos];     
   }
   
   private class RandomizedQueueIterator implements Iterator<Item> {
       
       private Item[] iteratorArray;
    
       private int iteratorArraySize;
    
       public RandomizedQueueIterator() {
           iteratorArraySize = N;   
           iteratorArray = (Item[]) new Object[iteratorArraySize];
           for (int i = 0; i < N; i++) {
               iteratorArray[i] = s[i];
           }
           
           StdRandom.shuffle(iteratorArray, 0, iteratorArraySize-1);
           
       }
       @Override
       public boolean hasNext() {
           return (iteratorArraySize != 0);
       }
       
       @Override
       public Item next() {
          if (iteratorArraySize == 0) {
              throw new java.util.NoSuchElementException();
          }
          // remove last item
          Item item = iteratorArray[--iteratorArraySize];
          iteratorArray[iteratorArraySize] = null; 

          return item;     
       }
       
       @Override
       public void remove() {
           throw new java.lang.UnsupportedOperationException();            
       }
       
   }
           
   // return an independent iterator over items in random order
   @Override
   public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
       
   }  
}
