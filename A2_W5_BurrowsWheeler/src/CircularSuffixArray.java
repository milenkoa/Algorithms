/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milenko
 */
public class CircularSuffixArray {
    private static final int CUTOFF =  12;
    private String s;
    private char[] text;
    private int[] index;
    private int N;
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        N = s.length();
        this.s = s;
        //s = s + '\0';
        this.text = s.toCharArray();
        //index = new int[s.length()];
        index = new int[N];
        
        for (int i = 0; i < N; i++) {
            index[i] = i;
        }       
        sort(0, N-1, 0);   
    }
    
    private void sort(int lo, int hi, int d) {   
        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(lo, hi, d);
            return;
        }
        
        int lt = lo, gt = hi;
        //char v = text[index[lo] + d];
        char v = text[(index[lo] + d) % N];
        //int v = text[(index[lo] + d) % N];
        //StdOut.println("text[" + index[lo] + " + " + d + "] = " + v);
        int i = lo + 1;
        while (i <= gt) {
            //int t = text[index[i] + d];
            char t = text[(index[i] + d) % N];
            //int t = text[(index[i] + d) % N];
            //StdOut.println("text[" + index[i] + " + " + d + "] = " + t);
            if (t < v) {
                exch(lt++, i++);
            }
            else if (t > v) {
                exch(i, gt--);
            }
            else {
                i++;
            }
        }

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]. 
        sort(lo, lt-1, d);
        if (v >= 0) {
            sort(lt, gt, d+1);
        }
        sort(gt+1, hi, d);
    }
    
    // sort from a[lo] to a[hi], starting at the dth character
    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(index[j], index[j-1], d); j--) {
                exch(j, j-1);
            }
        }
    }
    
    // is text[i+d..N) < text[j+d..N) ?
    private boolean less(int i, int j, int d) {
        if (i == j) {
            return false;
        }
        int a = i + d;
        int b = j + d;
//        while (i < N && j < N) {
//            if (text[i] < text[j]) return true;
//            if (text[i] > text[j]) return false;
//            i++;
//            j++;
//        }
        int k = 0;
        while (k < N) {
            if (text[(a+k) % N] < text[(b+k) % N]) {
                return true;
            }
            if (text[(a+k) % N] > text[(b+k) % N]) {
                return false;
            }
            k++;            
        }
        return a > b;
    }
    
        // exchange index[i] and index[j]
    private void exch(int i, int j) {
        int swap = index[i];
        index[i] = index[j];
        index[j] = swap;
    }
    
    // length of s
    public int length() {
        return N;
    }
    
    // returns index of ith sorted suffix
    public int index(int i) {
        return index[i];
        
    }
    
      public static void main(String[] args) {
          // String s = "0010000001";
          // String s = "ABRACADABRA!";
          String s = "ABRACADABR";
          
          CircularSuffixArray csa = new CircularSuffixArray(s);
          
          StdOut.println("length = " + csa.length());
          
           StdOut.println("index[4] = " + csa.index(4));
          
      }
    
}
