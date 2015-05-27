/**
 * SAP = Shortest Ancestor Path
 * @author milenko
 */
public class SAP {
    private Digraph graph;
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = new Digraph(G);     
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v > graph.V() - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (w < 0 || w > graph.V() - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        
        int ancestor = -1;
        int length = -1;
        BreadthFirstDirectedPaths pathsFromv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathsFromw = new BreadthFirstDirectedPaths(graph, w);

        for (int i = 0; i < graph.V(); i++) {
            if (pathsFromv.hasPathTo(i) && pathsFromw.hasPathTo(i)) {
                int distanceFromv = pathsFromv.distTo(i);
                int distanceFromw = pathsFromw.distTo(i);

                if (length == -1) {
                    ancestor = i;
                    length = distanceFromv + distanceFromw;                       
                } 
                else if (length > distanceFromv + distanceFromw) {
                    ancestor = i;
                    length = distanceFromv + distanceFromw;   
                }
            }  
        }
        

        if (ancestor != -1) { // there is ancestor path
            return pathsFromv.distTo(ancestor) + pathsFromw.distTo(ancestor);            
        }
        else {
            return -1;
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) { 
        if (v < 0 || v > graph.V() - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (w < 0 || w > graph.V() - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        
        int ancestor = -1;
        int length = -1;
        BreadthFirstDirectedPaths pathsFromv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathsFromw = new BreadthFirstDirectedPaths(graph, w);

        for (int i = 0; i < graph.V(); i++) {
            if (pathsFromv.hasPathTo(i) && pathsFromw.hasPathTo(i)) {
                int distanceFromv = pathsFromv.distTo(i);
                int distanceFromw = pathsFromw.distTo(i);
                
                if (length == -1) {
                    ancestor = i;
                    length = distanceFromv + distanceFromw;                                    
                } 
                else if (length > distanceFromv + distanceFromw) {
                    ancestor = i;
                    length = distanceFromv + distanceFromw;
                }
            }
        }     
        return ancestor;        
    }
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        for (int i: v) {
            if (i < 0 || i > graph.V() - 1) {
                throw new java.lang.IndexOutOfBoundsException();
            }
        }
        for (int i: w) {
            if (i < 0 || i > graph.V() - 1) {
                throw new java.lang.IndexOutOfBoundsException();
            }            
        }
        
        int ancestor = -1;
        int length = -1;
        BreadthFirstDirectedPaths pathsFromv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathsFromw = new BreadthFirstDirectedPaths(graph, w);

        for (int i = 0; i < graph.V(); i++) {         
            if (pathsFromv.hasPathTo(i) && pathsFromw.hasPathTo(i)) {
                int distanceFromv = pathsFromv.distTo(i);
                int distanceFromw = pathsFromw.distTo(i);

                if (length == -1) {
                    ancestor = i;
                    length = distanceFromv + distanceFromw;                                  
                } 
                else if (length > distanceFromv + distanceFromw) {
                    ancestor = i;
                    length = distanceFromv + distanceFromw;
                }  
            }       
        }

        if (ancestor != -1) { // there is ancestor path
            return pathsFromv.distTo(ancestor) + pathsFromw.distTo(ancestor);            
        }
        else {
            return -1;
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        for (int i: v) {
            if (i < 0 || i > graph.V() - 1) {
                throw new java.lang.IndexOutOfBoundsException();
            }
        }
        for (int i: w) {
            if (i < 0 || i > graph.V() - 1) {
                throw new java.lang.IndexOutOfBoundsException();
            }            
        }
        
        int ancestor = -1;
        int length = -1;
        BreadthFirstDirectedPaths pathsFromv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathsFromw = new BreadthFirstDirectedPaths(graph, w);

        for (int i = 0; i < graph.V(); i++) {
            if (pathsFromv.hasPathTo(i) && pathsFromw.hasPathTo(i)) {
                int distanceFromv = pathsFromv.distTo(i);
                int distanceFromw = pathsFromw.distTo(i);
                
                if (length == -1) {
                    ancestor = i;
                    length = distanceFromv + distanceFromw;                                      
                } 
                else if (length > distanceFromv + distanceFromw) {
                    ancestor = i;
                    length = distanceFromv + distanceFromw;
                }
            }
        }
        return ancestor;        
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        StdOut.println(G.toString());
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }        
    }    
}
