/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milenko
 */
import java.util.LinkedList;

//import java.io.FileInputStream;
//import java.io.File;
//import java.io.FileNotFoundException;

public class MoveToFront {
    
    public static void encode() {
        LinkedList<Character> seq = new LinkedList<Character>();
        Alphabet a = Alphabet.EXTENDED_ASCII;
        int radix = a.R();
        for (int i = 0; i < radix; i++) {
            seq.add(a.toChar(i));
            
        }
        
        while (!BinaryStdIn.isEmpty()) {
            Character c = BinaryStdIn.readChar();
            int index = seq.indexOf(c);
            BinaryStdOut.write((char) index);
            
            seq.remove(index);
            seq.addFirst(c);
        }
        
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> seq = new LinkedList<Character>();
        Alphabet a = Alphabet.EXTENDED_ASCII;
        int radix = a.R();
        for (int i = 0; i < radix; i++) {
            seq.add(a.toChar(i));
            
        }
        
        while (!BinaryStdIn.isEmpty()) {
            Character c = BinaryStdIn.readChar();
            Character index = seq.get((int) c);
            BinaryStdOut.write((char) index);
            
            seq.remove((int) c);
            seq.addFirst(index);
        }
        
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
         if (args[0].compareTo("-") == 0) {
//            FileInputStream is = new FileInputStream(new File(args[1]));
//            System.setIn(is);
            encode();
        }
        else {
            decode();
        }
    }
    
}
