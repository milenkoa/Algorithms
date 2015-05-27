/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milenko
 */
public class WordNet {
    private ST<String, Bag<Integer>> st;
    private String[] keys;
    private Digraph g;
    private SAP s;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) { 
        st = new ST<String, Bag<Integer>>();
        int counter = 0;
        
        In syns = new In(synsets);
        In hyps = new In(hypernyms);
        
        while (!syns.isEmpty()) {
            String line = syns.readLine();          
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);    
            String synset = parts[1];
            String[] nouns = synset.split(" ");
            
            for (String noun: nouns) {
                if (!st.contains(noun)) {
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(id); 
                    st.put(noun, bag);
                }
                else {
                    Bag<Integer> bag = st.get(noun);
                    bag.add(id);   
                }   
            }
            counter++;
        }
        
        keys = new String[counter];
        In syns2 = new In(synsets);
        while (!syns2.isEmpty()) {
            String line = syns2.readLine();          
           // StdOut.println(line);
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);  
            
            keys[id] = parts[1];    
        }
        g = new Digraph(counter);
        
        
        while (!hyps.isEmpty()) {
            String line = hyps.readLine();
            String[] parts = line.split(",");
            for (int i = 1; i < parts.length; i++) {
                int v = Integer.parseInt(parts[0]);
                int w = Integer.parseInt(parts[i]);
               
                g.addEdge(v, w);
            }
        }   
        
        //check if it is rooted
        int roots = 0;
        for (int v = 0; v < g.V(); v++) {
           int outdegree = 0;
           for (int w: g.adj(v)) {
               outdegree++;
           }
           if (outdegree == 0) {
               roots++;
           }  
        }
        if (roots != 1) {
            throw new IllegalArgumentException();
        }
        
        //check if it has cycles
        DirectedCycle dc = new DirectedCycle(g);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }
        s = new SAP(g);
    }

    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns() {
        return st.keys();        
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return st.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        Bag<Integer> bagA = st.get(nounA);
        Bag<Integer> bagB = st.get(nounB);
         if (bagA == null || bagB == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return s.length(bagA, bagB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        Bag<Integer> bagA = st.get(nounA);
        Bag<Integer> bagB = st.get(nounB);
        if (bagA == null || bagB == null) {
            throw new java.lang.IllegalArgumentException();
        }
        int ancestor = s.ancestor(bagA, bagB);
        if (ancestor == -1) {
            return null;
        }
        return keys[ancestor];
    }
   

    // for unit testing of this class
    public static void main(String[] args) {
        //WordNet wn = new WordNet(args[0], args[1]);
        
        
    }
    
}
