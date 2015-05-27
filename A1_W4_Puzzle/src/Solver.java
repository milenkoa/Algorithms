/*----------------------------------------------------------------
 *  Author:        Milenko Adamovic
 *  Written:       13/10/2013
 *  Last updated:  13/10/2013
 *
 *  Compilation:   javac Solver.java
 *  Execution:     
 *  
 *  Solves a NxN puzzle using A* search algorithm
 *
 *
 *----------------------------------------------------------------*/
public class Solver {
    private int moves = -1;
    private boolean isSolvable = false;
    private Node solution = null;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Node> pq1 = new MinPQ<Node>();
        Node node = new Node(initial, null, 0);              
        pq1.insert(node);
        
        // now create a board twin and start another A* search
        MinPQ<Node> pq2 = new MinPQ<Node>();
        Node node2 = new Node(initial.twin(), null, 0);
        pq2.insert(node2);
        
        while (!pq1.isEmpty() && !pq2.isEmpty()) {
            //StdOut.println("Number of items: " + pq.size());
            Node n1 = pq1.delMin();
            //StdOut.println("Processing: " + n.current.toString());
            if (n1.current.isGoal()) {
                //found a solution
                this.isSolvable = true;
                this.moves = n1.moves;
                this.solution = n1;
                return;
            }
            else {
                //we dont have a solution yet
                //StdOut.println("Not found a solution!");
                
                for (Board b: n1.current.neighbors()) {
                   // StdOut.println("Adding neighbor: " + b.toString());
//                    if (!b.equals(n1.previous.current)) {
//                        pq1.insert(new Node(b, n1, n1.moves+1));
//                    }
                    
                    if (n1.previous != null) {
                        if (!b.equals(n1.previous.current)) {
                            pq1.insert(new Node(b, n1, n1.moves+1));
                        }
                    }
                    else {
                         pq1.insert(new Node(b, n1, n1.moves+1));                        
                    }
                }
            }  
            
            Node n2 = pq2.delMin();
            //StdOut.println("Processing: " + n.current.toString());
            if (n2.current.isGoal()) {
                //found a solution
                this.isSolvable = false;
                //this.moves = n2.moves;
                //this.solution = n2;
                return;
            }
            else {
                //we dont have a solution yet
                //StdOut.println("Not found a solution!");
                
                for (Board b: n2.current.neighbors()) {
                   // StdOut.println("Adding neighbor: " + b.toString());
                    if (n2.previous != null) {
                        if (!b.equals(n2.previous.current)) {
                            pq2.insert(new Node(b, n2, n2.moves+1));
                        }
                    }
                    else {
                         pq2.insert(new Node(b, n2, n2.moves+1));                        
                    }
                }
            }     
        }
    }    
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return this.isSolvable;
    }  
    
     // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return this.moves;
    } 
   
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (isSolvable) {
            Bag<Board> b = new Bag<Board>();

            Node n = this.solution;
            //b.add(n.current);
            while (n != null) {
                b.add(n.current);
                n = n.previous;
            }
            return b; 
        }
        else {
            return null;
        }
    }
    
    private class Node implements Comparable<Node> {
        private Board current;
        private Node previous;
        private int moves;
        
        public Node(Board current, Node previous, int moves) {
            this.current = current;
            this.previous = previous;
            this.moves = moves;
        }
        
        @Override
        public int compareTo(Node that) {
            if (that == null) {
                throw new java.lang.NullPointerException();                
            }
            
            int distance1 = this.current.manhattan() + this.moves;
            int distance2 = that.current.manhattan() + that.moves;

            if (distance1 == distance2) {
                return 0;
            }
            else if (distance1 > distance2) {
                return 1;
            }
            else {
                return -1;
            }    
                
            
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
