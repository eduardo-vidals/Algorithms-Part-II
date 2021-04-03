package computerscience.algorithms.datastructures.graphs.unweighted;

import edu.princeton.cs.algs4.Bag;

/**
 * @author Eduardo
 */
public class GraphPractice {

    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public GraphPractice(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be non-negative.");
        }

        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];

        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v){
        validateVertex(v);
        return adj[v].size();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(V).append(" vertices, ").append(E).append(" edges").append(NEWLINE);
        for(int v = 0; v < V; v++){
            sb.append(v).append(": ");
            for(int w : adj[v]){
                sb.append(w).append(" ");
            }
            sb.append(NEWLINE);
        }
        return sb.toString();
    }

    private void validateVertex(int v){
        if (v < 0 || v >= V){
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

}
