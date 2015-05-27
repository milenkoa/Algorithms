/*************************************************************************
 * Name: Milenko Adamovic
 * Email:
 *
 * Compilation:  javac PointSET.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: Implements set of pointers
 *
 *************************************************************************/
public class PointSET {
    private SET<Point2D> set = new SET<Point2D>();
    
    /*---------------------------------------------------------
    * construct an empty set of points
    *---------------------------------------------------------*/
   public PointSET() {
       
   } 
   
   
   /*---------------------------------------------------------
    * is the set empty?
    *---------------------------------------------------------*/
   public boolean isEmpty()  {
       return set.isEmpty();
   } 
   
   
   /*---------------------------------------------------------
    * number of points in the set
    *---------------------------------------------------------*/
   public int size()  {
       return set.size();
   } 
   
   /*---------------------------------------------------------
    * add the point p to the set (if it is not already in the set)
    *---------------------------------------------------------*/
   public void insert(Point2D p) {
       //StdOut.println("Inserting a point " + p.toString());
       if (!set.contains(p)) {
           set.add(p);
       }
       
   }    
   
   /*---------------------------------------------------------
    * does the set contain the point p?
    *---------------------------------------------------------*/
   public boolean contains(Point2D p)   {
       return set.contains(p);
   }  
   
   /*---------------------------------------------------------
    * draw all of the points to standard draw
    *---------------------------------------------------------*/
   public void draw()   {
       for (Point2D p:set) {
           //StdOut.println("Drawing: " + p.toString());
           
           p.draw();           
       }
       
   } 
   
    /*---------------------------------------------------------
    * all points in the set that are inside the rectangle
    *---------------------------------------------------------*/
   public Iterable<Point2D> range(RectHV rect) {
       SET<Point2D> tmp = new SET<Point2D>();
       
       for (Point2D i:set) {
           if (rect.contains(i)) {
               tmp.add(i);
           }       
       }
       return tmp;   
   }
   
   /*---------------------------------------------------------
    * a nearest neighbor in the set to p; null if set is empty
    *---------------------------------------------------------*/
   public Point2D nearest(Point2D p)  {
       double currentDistance = Double.POSITIVE_INFINITY;
       Point2D currentChampion = null;

       if (set.isEmpty()) {
           return null;
       }
       
       for (Point2D i:set) {
           double distance = p.distanceTo(i);
           if (distance < currentDistance) {
               currentDistance = distance;
               currentChampion = i;
           }
           
       }
       return currentChampion; 
   }   
}
