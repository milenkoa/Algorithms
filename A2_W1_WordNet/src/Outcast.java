/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milenko
 */
public class Outcast {
    private WordNet wordnet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;     
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int length = nouns.length;
        int maxDistance = -1;
        int maxIndex = -1;
        for (int i = 0; i < length; i++) {
            int distance = 0;
            for (int j = 0; j < length; j++) {
                if (i != j) {
                    distance += wordnet.distance(nouns[i], nouns[j]);
                }
            }
            if (distance > maxDistance) {
                maxDistance = distance;
                maxIndex = i;
            }
        } 
        return nouns[maxIndex];
    }
    
    
    public static void main(String[] args) {
//        WordNet wordnet = new WordNet(args[0], args[1]);
//        Outcast outcast = new Outcast(wordnet);
//        for (int t = 2; t < args.length; t++) {
//            String[] nouns;
//            nouns = In.readStrings(args[t]);
//            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
//        }
    }
    
}
