package computerscience.algorithms.datastructures.graphs.unweighted;

import edu.princeton.cs.algs4.Bag;

/**
 * @author Eduardo
 */
public class DigraphPractice {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    private int[] indegree;

    public DigraphPractice(int V){
        if (V < 0){
            throw new IllegalArgumentException();
        }
        this.V = V;
        this.E = 0;
        indegree = new int[V];
        adj = (Bag<Integer>[]) new Bag[V];
        for(int v = 0; v < V; v++){
            adj[v] = new Bag<>();
        }
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    private void validateVertex(int v){
        if (v < 0 || v >= V){
            throw new IllegalArgumentException();
        }
    }

    public void addEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        indegree[w]++;
        E++;
    }

    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v];
    }

    public int outdegree(int v){
        validateVertex(v);
        return adj[v].size();
    }

    public int indegree(int v){
        validateVertex(v);
        return indegree[v];
    }

    public Digraph reverse(){
        Digraph reverse = new Digraph(V);
        for(int v = 0; v < V; v++){
            for (int w : adj[v]){
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++){
            sb.append(String.format("%d: ", v));
            for (int w : adj[v]){
                sb.append(String.format("%d ", w));
            }
            sb.append(NEWLINE);
        }
        return sb.toString();
    }
}
