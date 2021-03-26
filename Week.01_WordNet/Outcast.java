/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerscience.algorithms.week6.wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author Eduardo
 */
public class Outcast {

    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = nouns[0];
        int distance = sum(nouns[0], nouns);
        for (int i = 1; i < nouns.length; i++) {
            int tempDist = sum(nouns[i], nouns);
            if (distance < tempDist) {
                distance = tempDist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    // helper methods
    private int sum(String noun, String[] nouns) {
        int distance = 0;
        for (String n : nouns) {
            distance += wordnet.distance(noun, n);
        }
        return distance;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
