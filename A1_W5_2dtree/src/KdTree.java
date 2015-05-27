/*************************************************************************
 * Name: Milenko Adamovic
 * Email:
 *
 * Compilation:  javac KdTree.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/
public class KdTree {
    
    private Node root = null;
    private int size = 0;
    
    /*---------------------------------------------------------
    * inner class that represents a node of Kd-Tree
    *---------------------------------------------------------*/
    private static class Node {    
       private Point2D p;      // the point
       private RectHV rect;    // the axis-aligned rectangle corresponding to this node
       private Node lb;        // the left/bottom subtree
       private Node rt;        // the right/top subtree
       private int orientation; // 1 - verical; 2 - horizontal
    }
   

    /*---------------------------------------------------------
    * construct an empty set of points
    *---------------------------------------------------------*/
   public KdTree() {
   }
   
    /*---------------------------------------------------------
    * is the set empty?
    *---------------------------------------------------------*/
   public boolean isEmpty() {
       return (root == null);
   }
   
    /*---------------------------------------------------------
    * number of points in the set
    *---------------------------------------------------------*/
   public int size() {
       return size;
   }
   
    /*---------------------------------------------------------
    * add the point p to the set (if it is not already in the set)
    *---------------------------------------------------------*/
   public void insert(Point2D p) {
            root = insert(root, p, 1, 0, 0, 1, 1);
       
   }
   
    /*---------------------------------------------------------
    * a nearest neighbor in the set to p; null if set is empty
    *---------------------------------------------------------*/
   private Node insert(Node n, Point2D p, int orientation, double xmin, double ymin, double xmax, double ymax) {
       if (n == null) { //insert a new Node
           Node temp = new Node();
           temp.lb = null;
           temp.rt = null;
           temp.orientation = orientation;
           temp.p = p;
           //rectange corresponding to this node is half of parent rectangle
           temp.rect = new RectHV(xmin, ymin, xmax, ymax);
           size++;
           return temp;
       }
       
       if (n.p.x() == p.x() && n.p.y() == p.y()) {
           //point is already in the tree
           return n;
       }
       

       
       if (orientation == 1) { // vertical
           if (p.x() <= n.p.x()) { // vertical, go left
               n.lb =  insert(n.lb, p, 2, xmin, ymin, n.p.x(), ymax);
           }
           else { //verical, go right
               n.rt = insert(n.rt, p, 2, n.p.x(), ymin, xmax, ymax);
           }
       }
       else { // horizontal
           if (p.y() <= n.p.y()) { // horizontal, go down
               n.lb = insert(n.lb, p, 1, xmin, ymin, xmax, n.p.y());
           }
           else {
               n.rt = insert(n.rt, p, 1, xmin, n.p.y(), xmax, ymax);
           }        
       } 
       return n;
   }
   
    /*---------------------------------------------------------
    * does the set contain the point p?
    *---------------------------------------------------------*/
   public boolean contains(Point2D p) {
      return (contains(root, p) != null);
       
   }
   
    /*---------------------------------------------------------
    * contains helper function
    *---------------------------------------------------------*/
   private Node contains(Node n, Point2D p) {
       if (n == null) {
           return null;
       }
       if (p.x() == n.p.x() && p.y() == n.p.y()) {
           return n;
       }
       else {
           if (n.orientation == 1) { // vertical
               if (p.x() <= n.p.x()) { //go left
                   return contains(n.lb, p);
               }
               else { // go right
                   return contains(n.rt, p);
               }
           }
           else { // horizontal
               if (p.y() <= n.p.y()) { // go left
                   return contains(n.lb, p);
               }
               else {
                   return contains(n.rt, p);
               }
           }
       }
   }
   
    /*---------------------------------------------------------
    * draw all of the points to standard draw
    *---------------------------------------------------------*/
   public void draw() {
       
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(.01);
       root.p.draw();
       
       StdDraw.setPenColor(StdDraw.RED);
       StdDraw.setPenRadius();
       StdDraw.line(root.p.x(), 0, root.p.x(), 1);
       draw(root.lb, true, root); 
       draw(root.rt, false, root); 
   }
   
    /*---------------------------------------------------------
    * draw helper function
    *---------------------------------------------------------*/
   private void draw(Node n, boolean bLB, Node parent) {
       if (n == null) {
           return;
       }
       
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(.01);
       
       n.p.draw();
       
       if (n.orientation == 1) {  //vertical
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            if (bLB) {
                StdDraw.line(n.p.x(), 0, n.p.x(), parent.p.y());
            }
            else {
                StdDraw.line(n.p.x(), parent.p.y(), n.p.x(), 1);
            }     
       }
       else { //horizontal
           StdDraw.setPenColor(StdDraw.BLUE);
           StdDraw.setPenRadius();
           if (bLB) {
                StdDraw.line(0, n.p.y(), parent.p.x(), n.p.y());
            }
            else {
                StdDraw.line(parent.p.x(), n.p.y(), 1, n.p.y());
            }              
       }
       
       draw(n.lb, true, n);
       draw(n.rt, false, n);
   }
   
    /*---------------------------------------------------------
    * all points in the set that are inside the rectangle
    *---------------------------------------------------------*/
   public Iterable<Point2D> range(RectHV rect) {
       SET<Point2D> tmp = new SET<Point2D>();
       
       //tmp = range(root, rect, tmp);
       range(root, rect, tmp);
       
       return tmp;
   } 
   
    /*---------------------------------------------------------
    * range helper function
    *---------------------------------------------------------*/
   //private SET<Point2D> range(Node n, RectHV rect, SET<Point2D> res) {
   private void range(Node n, RectHV rect, SET<Point2D> res) {
       //SET<Point2D> tmp = new SET<Point2D>();
       if (n == null) {
           //return res;
           return;
       }
       
       if (rect.contains(n.p)) {
           res.add(n.p);
       }
       if (n.lb != null && rect.intersects(n.lb.rect)) {
            //res = range(n.lb, rect, res);
           range(n.lb, rect, res);
           
       }

       if (n.rt != null && rect.intersects(n.rt.rect)) {
            //res = range(n.rt, rect, res);
           range(n.rt, rect, res);
       }
       
       //return res;
   }
   
    /*---------------------------------------------------------
    * a nearest neighbor in the set to p; null if set is empty
    *---------------------------------------------------------*/
   public Point2D nearest(Point2D p) {
       Point2D currentChampion = null;
       double currentdistance;
       if (root != null) {
           currentdistance = p.distanceTo(root.p);
           
           currentChampion = nearest(root, p, root.p, currentdistance);
           //currentChampion = nearest(root, p, currentdistance);
       }
       return currentChampion;
   }     
   
    /*---------------------------------------------------------
    * a nearest neighbor helper function
    *---------------------------------------------------------*/
   private Point2D nearest(Node n, Point2D p, Point2D currentChampion, double currentdistance) {
   //private Point2D nearest(Node n, Point2D p, double currentdistance) {
       Point2D newChampion = currentChampion;
       double newDistance = currentdistance;
       if (n != null) {
           double distance = n.p.distanceTo(p);
           if (currentdistance > distance) {
               newChampion = n.p;
               newDistance = distance;
           }
           
           
           if (n.orientation == 1) {
               if (p.x() < n.p.x()) {
                   if (n.lb != null && newDistance > n.lb.rect.distanceTo(p)) {
                       newChampion = nearest(n.lb, p, newChampion, newDistance);  
                       //currentChampion = nearest(n.lb, p, currentdistance);  
                       //currentdistance = p.distanceTo(currentChampion);
                       newDistance = p.distanceTo(newChampion);
                   }

                   if (n.rt != null && newDistance > n.rt.rect.distanceTo(p)) {
                       newChampion = nearest(n.rt, p, newChampion, newDistance);
                       //currentChampion = nearest(n.rt, p, currentdistance);
                   }
               }
               else {
                   if (n.rt != null && newDistance > n.rt.rect.distanceTo(p)) {
                       newChampion = nearest(n.rt, p, newChampion, newDistance);
                       //currentChampion = nearest(n.rt, p, currentdistance);
                       //currentdistance = p.distanceTo(currentChampion);
                       newDistance = p.distanceTo(newChampion);
                   }

                   if (n.lb != null && newDistance > n.lb.rect.distanceTo(p)) {
                       newChampion = nearest(n.lb, p, newChampion, newDistance);               
                       //currentChampion = nearest(n.lb, p, currentdistance);               
                   }      
               }     
           }
           else {
               if (p.y() < n.p.y()) {
                   if (n.lb != null && newDistance > n.lb.rect.distanceTo(p)) {
                       newChampion = nearest(n.lb, p, newChampion, newDistance);  
                       //currentChampion = nearest(n.lb, p, currentdistance);  
                       //currentdistance = p.distanceTo(currentChampion);
                       newDistance = p.distanceTo(newChampion);
                   }

                   if (n.rt != null && newDistance > n.rt.rect.distanceTo(p)) {
                       newChampion = nearest(n.rt, p, newChampion, newDistance);
                       //currentChampion = nearest(n.rt, p, currentdistance);
                   }
               }
               else {
                   if (n.rt != null && newDistance > n.rt.rect.distanceTo(p)) {
                       newChampion = nearest(n.rt, p, newChampion, newDistance);
                       //currentChampion = nearest(n.rt, p, currentdistance);
                       //currentdistance = p.distanceTo(currentChampion);
                       newDistance = p.distanceTo(newChampion);
                   }

                   if (n.lb != null && newDistance > n.lb.rect.distanceTo(p)) {
                       newChampion = nearest(n.lb, p, newChampion, newDistance);               
                       //currentChampion = nearest(n.lb, p, currentdistance);
                   }      
               }                  
           }
       }
       //return currentChampion;       
       return newChampion;       
   }
   
    /*---------------------------------------------------------
    * main function for unit testing
    *---------------------------------------------------------*/
   public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        //PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            kdtree.size();
           // brute.insert(p);
        }
        StdOut.println("size: " + kdtree.size());
        
        
        
        int count = 0;
        RectHV rect = new RectHV(0.2065, 0.2395, 0.7923, 0.3254);
        for (Point2D p: kdtree.range(rect)) {
            count++;
        }
        StdOut.println("count: " + count);
        
//        StdOut.println("Size: " + kdtree.size());
//        StdOut.println("Empty: " + kdtree.isEmpty());
//        for(Point2D p: kdtree.range(new RectHV(0.0, 0.0, 0.81, 0.3))) {
//            StdOut.println(p.toString());
//        }
        
        //kdtree.draw();
        
   }
   
   
}
