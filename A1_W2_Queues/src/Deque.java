/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milenko
 */
import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {
    //sentinels
    private Node<Item> head;
    private Node<Item> tail;
    
    private int size = 0;
    
    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous; 
    }
   // construct an empty deque
   public Deque() {
       head = new Node<Item>();
       tail = new Node<Item>();
       head.previous = null;
       tail.next = null;
       
       head.next = tail;
       tail.previous = head;   
   }                     
           
   // is the deque empty?
   public boolean isEmpty() {
       return (size == 0);
   }
   
   // return the number of items on the deque
   public int size() {
       return size;
   }           
   
   // insert the item at the front
   public void addFirst(Item item) {
       if (item == null) {
           throw new java.lang.NullPointerException();
       }
       
       Node<Item> newNode = new Node<Item>();
       newNode.item = item;
       
       head.next.previous = newNode;
       newNode.next = head.next;  
       head.next = newNode;
       newNode.previous = head;
       
       size++;
   }   
   
   // insert the item at the end
   public void addLast(Item item) {
       if (item == null) {
           throw new java.lang.NullPointerException();
       }
              
       Node<Item> newNode = new Node<Item>();
       newNode.item = item;
       
       tail.previous.next = newNode;
       newNode.previous = tail.previous;
       tail.previous = newNode;
       newNode.next = tail; 
       
       size++;
   }
   
   // delete and return the item at the front
   public Item removeFirst() {
       if (size == 0) {
           throw new java.util.NoSuchElementException();
       }
       Node<Item> temp = head.next;
       head.next = temp.next;
       temp.next.previous = temp.previous;
       
       temp.next = null;
       temp.previous = null;
       
       size--;
       
       return temp.item;       
   } 
   
   // delete and return the item at the end
   public Item removeLast() {
       if (size == 0) {
           throw new java.util.NoSuchElementException();
       }
       Node<Item> temp = tail.previous;
       temp.previous.next = tail;
       tail.previous = temp.previous;
       
       temp.next = null;
       temp.previous = null;
       
       size--;
       
       return temp.item;
   }
   
   private class DequeIterator implements Iterator<Item> {
       private Node<Item> current = head.next;
       
       @Override
       public boolean hasNext() {
           return (current != tail);
       }
       
       @Override
       public Item next() {
           if (current == tail) {
               throw new java.util.NoSuchElementException();
           }
           Item item = current.item;
           current = current.next;
           return item;
       }
       
       @Override
       public void remove() {
           throw new java.lang.UnsupportedOperationException();           
       }
   }
   
   // return an iterator over items in order from front to end
   @Override
   public Iterator<Item> iterator() {
       return new DequeIterator(); 
   }  
}
