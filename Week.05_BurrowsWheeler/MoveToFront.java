package computerscience.algorithms.week10.burrows;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * @author Eduardo
 */
public class MoveToFront {

    // radix
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to
    // standard output
    public static void encode() {
        char[] alphabet = alphabet();

        char c, count, tempIn, tempOut;
        while (!BinaryStdIn.isEmpty()) {
            c = BinaryStdIn.readChar();
            for (count = 0, tempOut = alphabet[0]; c != alphabet[count]; count++) {
                tempIn = alphabet[count];
                alphabet[count] = tempOut;
                tempOut = tempIn;
            }
            alphabet[count] = tempOut;
            BinaryStdOut.write(count);
            alphabet[0] = c;
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to
    // standard output
    public static void decode() {
        char[] alphabet = alphabet();
        char c, count;
        while (!BinaryStdIn.isEmpty()) {
            count = BinaryStdIn.readChar();
            for (c = alphabet[count]; count > 0; count--) {
                alphabet[count] = alphabet[count - 1];
            }
            alphabet[count] = c;
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    // alphabet based on radix
    private static char[] alphabet() {
        char[] al = new char[R];
        for (char i = 0; i < R; i++) {
            al[i] = i;
        }
        return al;
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        }
    }

}
