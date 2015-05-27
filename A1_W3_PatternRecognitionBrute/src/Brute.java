
/*************************************************************************
 * Name: Milenko Adamovic
 * Email:
 *
 * Compilation:  javac Brute.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Arrays;
public class Brute {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         StdDraw.setXscale(0, 32768);
         StdDraw.setYscale(0, 32768);
         
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] a = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            
            a[i] = p;
        }
        
        Arrays.sort(a);
        
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N && j != i; j++) {
                for (int k = j+1; k < N && k != j; k++) {
                    for (int m = k+1; m < N && m != k; m++) {
                        if ((a[i].slopeTo(a[j]) == a[i].slopeTo(a[k])) 
                                && (a[i].slopeTo(a[j]) == a[i].slopeTo(a[m]))) {                          
                            StdOut.println(a[i].toString() + " -> " + a[j].toString() + " -> " + a[k].toString() + " -> " + a[m].toString());                          
                            a[i].drawTo(a[m]);
                        }                        
                    }
                }
            }
        }
    }
}
