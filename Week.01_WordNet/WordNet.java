/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerscience.algorithms.week6.wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Eduardo
 */
public class WordNet {

    private final Map<Integer, String> idToSynsets;
    private final Map<String, Set<Integer>> nounsToIds;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        idToSynsets = new HashMap<>();
        nounsToIds = new HashMap<>();
        synsetNouns(synsets);
        Digraph digraph = hypernyms(hypernyms);
        sap = new SAP(digraph);
    }

    private void synsetNouns(String synsets) {
        In in = new In(synsets);

        while (in.hasNextLine()) {
            String[] synset = in.readLine().split(",");
            int synsetID = Integer.valueOf(synset[0]);
            idToSynsets.put(synsetID, synset[1]);
            String[] synsetNouns = synset[1].split("\\s++");
            for (String noun : synsetNouns) {
                Set<Integer> nounIds = nounsToIds.get(noun);
                if (nounIds == null) {
                    nounIds = new HashSet<>();
                }
                nounIds.add(synsetID);
                nounsToIds.put(noun, nounIds);
            }
        }
    }

    private Digraph hypernyms(String hypernyms) {
        Digraph graph = new Digraph(idToSynsets.size());
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] hypernym = in.readLine().split(",");
            int vertex = Integer.valueOf(hypernym[0]);
            for (int i = 1; i < hypernym.length; i++) {
                int edge = Integer.valueOf(hypernym[i]);
                graph.addEdge(vertex, edge);
            }
        }
        return graph;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounsToIds.get(word) != null;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(nounsToIds.get(nounA), nounsToIds.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int ancestor = sap.ancestor(nounsToIds.get(nounA), nounsToIds.get(nounB));
        String noun = idToSynsets.get(ancestor);
        return noun;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
