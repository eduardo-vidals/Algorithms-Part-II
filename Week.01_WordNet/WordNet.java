/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerscience.algorithms.week6.wordnet;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author Eduardo
 */
public class WordNet {

    private BST<String, Integer> nouns;
    private Digraph digraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In in1 = new In(new File(synsets));
        In in2 = new In(new File(hypernyms));
        nouns = new BST<>();
        int count = 0;
        while (in1.hasNextLine()) {
            String[] synset = in1.readLine().split(",");
            String[] synsetNouns = synset[1].split("\\s++");
            for (String synsetNoun : synsetNouns) {
                nouns.put(synsetNoun, count);
            }
            count++;
        }
        
        digraph = new Digraph(count);
        
        int vertex = 0;
        while (in2.hasNextLine()){
            String[] hypernym = in2.readLine().split(","); 
            for (int i = 1; i < hypernym.length; i++){
                int edge = Integer.valueOf(hypernym[i]);
                digraph.addEdge(vertex, edge);
            }
            vertex++;
        }
        
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet words = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(words.nouns());
    }
}
