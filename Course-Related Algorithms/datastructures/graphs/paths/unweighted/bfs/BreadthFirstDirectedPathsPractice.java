package computerscience.algorithms.datastructures.graphs.paths.unweighted.bfs;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

/**
 * @author Eduardo
 */
public class BreadthFirstDirectedPathsPractice {
    private final static int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public BreadthFirstDirectedPathsPractice(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        validateVertex(s);
        bfs(G, s);
    }

    private void bfs(Digraph G, int s) {
        Queue<Integer> q = new Queue<>();
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
    }

    private void bfs(Digraph G, Iterable<Integer> sources){
        Queue<Integer> q = new Queue<>();
        for (int s : sources){
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }

        while(!q.isEmpty()){
            int v = q.dequeue();
            for(int w : G.adj(v)){
                if (!marked[w]){
                    edgeTo[w] = v;
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v){
        validateVertex(v);
        return marked[v];
    }

    public int distTo(int v){
        validateVertex(v);
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v){
        validateVertex(v);
        if (!hasPathTo(v)){
            return null;
        }
        Stack<Integer> path = new Stack<>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x]){
            path.push(x);
        }
        path.push(x);
        return path;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException();
        }
    }
}
