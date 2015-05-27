/*----------------------------------------------------------------
 *  Author:        Milenko Adamovic
 *  Written:       29/8/2013
 *  Last updated:  1/9/2013
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     
 *  
 *  Model of percolation system using an N-by-N grid of sites
 *
 *
 *----------------------------------------------------------------*/


public class Percolation {

    private boolean[] state; // state of each site (block: false, open: true)
    private int size; // size of Percolation problem
    private WeightedQuickUnionUF u;
    
     /*---------------------------------------------------------
    * constructor
    *---------------------------------------------------------*/  
    public Percolation(int N) {        
        u = new WeightedQuickUnionUF(N*N+2);
        
        state = new boolean[N*N + 2];
        this.size = N;
        
        // connect super-nodes to first row
        for (int i = 1; i <= N; i++) {
            u.union(0, i);
        }
        
        // connect super-nodes for last row
        
        for (int i = size*size; i > size*size - size; i--) {
            u.union(i, size*size+1);
        }
 
        
    }
    
    /*---------------------------------------------------------
    * validates indices
    *---------------------------------------------------------*/  
    private void validate(int i, int j) {
        if (i <= 0 || i > size) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > size) throw new IndexOutOfBoundsException("row index j out of bounds");
    }
    
    /*---------------------------------------------------------
    * translates from 2D xy grid into 1D array index
    *---------------------------------------------------------*/  
    private int xyto1D(int i, int j) {
        validate(i, j);
      
        return ((i-1)*size + j);
    }
 
       
    /*---------------------------------------------------------
    * open site (i, j) if it is not already opened
    *---------------------------------------------------------*/  
    public void open(int i, int j) {
      validate(i, j);
      
      state[xyto1D(i, j)] = true;
       
      if (i > 1 && isOpen(i - 1, j)) {
        u.union(xyto1D(i - 1, j), xyto1D(i, j));
      }
      if (i < size && isOpen(i + 1, j)) {
        u.union(xyto1D(i + 1, j), xyto1D(i, j));
      }
      if (j > 1 && isOpen(i, j - 1)) {
        u.union(xyto1D(i, j - 1), xyto1D(i, j));
      }
      if (j < size && isOpen(i, j + 1)) {
        u.union(xyto1D(i, j + 1), xyto1D(i, j));
      }
    }
    
    /*---------------------------------------------------------
    * returns true if site is opened, otherwise false
    *---------------------------------------------------------*/  
    public boolean isOpen(int i, int j) {  
        validate(i, j);
        return state[xyto1D(i, j)];     
    }
   
    /*---------------------------------------------------------
    * returns true if site is full, otherwise false
    *---------------------------------------------------------*/      
    public boolean isFull(int i, int j) {
        validate(i, j);
        return (state[xyto1D(i, j)] && u.connected(xyto1D(i, j), 0)); 
    }
    
    
    /*---------------------------------------------------------
    * returns true if percolates, otherwise false
    *---------------------------------------------------------*/  
    public boolean percolates() {
      if (size == 1 && !isOpen(1, 1)) {
        return false;        
      } 
        return u.connected(0, size*size+1);        
    }   
}
