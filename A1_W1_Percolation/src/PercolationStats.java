/*----------------------------------------------------------------
 *  Author:        Milenko Adamovic
 *  Written:       29/8/2013
 *  Last updated:  1/9/2013
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats
 *  
 *  Monte Carlo simulation to estimate the percolation threshold
 *
 *
 *----------------------------------------------------------------*/

import java.lang.Math;
import java.lang.IllegalArgumentException;


public class PercolationStats {

  private double[] tresholds; // array of treshold for each experiment
  private int T; // number of experiments to perform

 /*---------------------------------------------------------
 * perform T independent computational experiments on 
 * an N-by-N grid
 *---------------------------------------------------------*/
  public PercolationStats(int N, int T) {
    if (N <= 0) throw new java.lang.IllegalArgumentException();
    if (T <= 0) throw new java.lang.IllegalArgumentException();
    
    tresholds = new double[T];
    this.T = T;
    for (int i = 0; i < T; i++) {
      Percolation p = new Percolation(N);
      int count = 0;

      while (!p.percolates()) {
        int rand_i = 1 + (int) (Math.random() * N);
        int rand_j = 1 + (int) (Math.random() * N);
        if (!p.isOpen(rand_i, rand_j)) {
          p.open(rand_i, rand_j);
          count++;
        }
      }
      tresholds[i] = (double) count / (N * N);
    }
  }

 /*---------------------------------------------------------
 * returns sample mean of percolation threshold
 *---------------------------------------------------------*/
  public double mean() {
    return StdStats.mean(tresholds);

  }

 /*---------------------------------------------------------
 * returns sample standard deviation of percolation threshold
 *---------------------------------------------------------*/
  public double stddev() {
    return StdStats.stddev(tresholds);

  }

 /*---------------------------------------------------------
 * returns lower bound of the 95% confidence interval
 *---------------------------------------------------------*/
  public double confidenceLo() {
    return mean() - 1.96 * stddev() / Math.sqrt(T);

  }

 /*---------------------------------------------------------
 * returns upper bound of the 95% confidence interval
 *---------------------------------------------------------*/
  public double confidenceHi() {
    return mean() + 1.96 * stddev() / Math.sqrt(T);
  }

  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);
    

    PercolationStats p = new PercolationStats(N, T);

    System.out.println("mean                    = " + p.mean());
    System.out.println("stddev                  = " + p.stddev());
    System.out.println("95% confidence interval = " + p.confidenceLo() + ", " + p.confidenceHi());

  }
}
