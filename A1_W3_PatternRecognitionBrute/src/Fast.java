
/*************************************************************************
 * Name: Milenko Adamovic
 * Email:
 *
 * Compilation:  javac Fast.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Arrays;

public class Fast {
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
        
        
        for (int j = 0; j < N; j++) {
            //put i at first position
            //Point temp = a[j];
            //a[j] = a[0];
            //a[0] = temp;
            
            // sort in respect to a[0]
         
            Arrays.sort(a, j+1, N, a[j].SLOPE_ORDER);

            int m = j+1;
            int n = j+2;
            while (m < N) {
                boolean bCollinear = false;
                double slope = a[j].slopeTo(a[m]);
                while (n < N && (a[j].slopeTo(a[n]) == slope)) {
                    n++;
                }
                if (n - m > 2) { // we found more then 4 collinear points
                    bCollinear = true;
                    //check if it is collinear with some of already processed
                    for (int z = 0; z < j; z++) {
                        if (slope == a[j].slopeTo(a[z])) {
                            bCollinear = false;
                        }
                    }
                    
                    if (bCollinear) {
                        Point[] temp = new Point[n-m+1];
                        temp[0] = a[j];
                        for (int i = 0; i < n-m; i++) {
                            temp[i+1] = a[m+i];
                        }
                    
                
                        Arrays.sort(temp);
                        temp[0].drawTo(temp[n-m]);
                        for (int p = 0; p < temp.length-1; p++) {
                            StdOut.print(temp[p].toString() + " -> ");
                        }
                        StdOut.println(temp[temp.length-1].toString());
                    }
                }
                m = n;
                n++;
            }
        }
    }
    
}
