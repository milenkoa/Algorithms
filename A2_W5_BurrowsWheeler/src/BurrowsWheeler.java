/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milenko
 */
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;

public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output   
    public static void encode() {
        String s = BinaryStdIn.readString();
        char[] text = s.toCharArray();
        //StdOut.println(text);
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int N = s.length();
        for (int i = 0; i < N; i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < N; i++) {
            //StdOut.print(csa.index(i));
            //StdOut.println(text[(csa.index(i)+N-1) % N]);
            BinaryStdOut.write(text[(csa.index(i)+N-1) % N]);
            
        }
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] text = s.toCharArray();
        
        
        
        BinaryStdOut.flush();
        BinaryStdOut.close();
        
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding 
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) throws FileNotFoundException {
        if (args[0].compareTo("-") == 0) {
            //perform encoding
            //StdOut.println("Perform encoding");  
//            FileInputStream is = new FileInputStream(new File(args[1]));
//            System.setIn(is);
            encode();
            
        }
        else {
            //StdOut.println("perform decoding");
            decode();
        }
    }
    
}
