/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerscience.algorithms.week6.wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
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
        DirectedCycle dc = new DirectedCycle(digraph);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(digraph);

        int root = 0;
        int count = idToSynsets.size();
        for (int v = 0; v < count; v++) {
            if (digraph.outdegree(v) == 0) {
                root++;
            }
        }
        if (root != 1) {
            throw new IllegalArgumentException();
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Input is null.");
        }

        return nounsToIds.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!nounsToIds.containsKey(nounA) || !nounsToIds.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }
        
        return sap.length(nounsToIds.get(nounA), nounsToIds.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!nounsToIds.containsKey(nounA) || !nounsToIds.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }

        int ancestor = sap.ancestor(nounsToIds.get(nounA), nounsToIds.get(nounB));
        String noun = idToSynsets.get(ancestor);
        return noun;
    }

    private void synsetNouns(String synsets) {
        if (synsets == null) {
            throw new IllegalArgumentException();
        }

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
        if (hypernyms == null) {
            throw new IllegalArgumentException();
        }

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

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
