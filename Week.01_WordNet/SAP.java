/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerscience.algorithms.week6.wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author Eduardo
 */
public class SAP {

    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return sap(v, w)[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return sap(v, w)[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return sap(v, w)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return sap(v, w)[1];
    }

    private int[] sap(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        int distance = Integer.MAX_VALUE;
        int ancestor = Integer.MIN_VALUE;
        for (int vertex = 0; vertex < digraph.V(); vertex++) {
            if (bfsV.hasPathTo(vertex) && bfsW.hasPathTo(vertex) && bfsV.distTo(vertex) < distance && bfsW.distTo(vertex) < distance) {
                int sum = bfsV.distTo(vertex) + bfsW.distTo(vertex);
                if (distance > sum) {
                    distance = sum;
                    ancestor = vertex;
                }
            }
        }

        if (distance == Integer.MAX_VALUE) {
            return new int[]{-1, -1};
        } else {
            return new int[]{distance, ancestor};
        }
    }

    private int[] sap(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        int distance = Integer.MAX_VALUE;
        int ancestor = Integer.MIN_VALUE;

        for (int vertex = 0; vertex < digraph.V(); vertex++) {
            if (bfsV.hasPathTo(vertex) && bfsW.hasPathTo(vertex) && bfsV.distTo(vertex) < distance && bfsW.distTo(vertex) < distance) {
                int sum = bfsV.distTo(vertex) + bfsW.distTo(vertex);
                if (distance > sum) {
                    distance = sum;
                    ancestor = vertex;
                }
            }
        }

        if (distance == Integer.MAX_VALUE) {
            return new int[]{-1, -1};
        } else {
            return new int[]{distance, ancestor};
        }
    }

    private void validateVertex(int v) {
        int V = digraph.V();
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = digraph.V();

        for (Integer v : vertices) {
            if (v == null) {
                throw new IllegalArgumentException();
            }

            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int v = 9;
        int w = 12;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

    }

}
