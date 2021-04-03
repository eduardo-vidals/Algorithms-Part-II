package computerscience.algorithms.datastructures.graphs.paths.unweighted.bfs;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

/**
 * @author Eduardo
 */
public class BreadthFirstPathsPractice {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public BreadthFirstPathsPractice(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        validateVertex(s);
        bfs(G, s);
    }

    public BreadthFirstPathsPractice(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        validateVertices(sources);
        bfs(G, sources);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> q = new Queue<>();
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w]++;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    private void bfs(Graph G, Iterable<Integer> sources) {
        Queue<Integer> q = new Queue<>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }

        while (!q.isEmpty()){
            int v = q.dequeue();
            for (int w : G.adj(v)){
                if (!marked[w]){
                    edgeTo[w] = v;
                    distTo[w]++;
                    marked[w] = true;
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
        // distance will be 0 once we reach our vertex
        for (x = v; distTo[x] != 0; x = edgeTo[x]){
            path.push(x);
        }
        // x will be equal to our vertex, so that is why we intialize it outside
        // of the for-loop
        path.push(x);
        return path;
    }


    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException();
        }
    }

    private void validateVertices(Iterable<Integer> vertices) {
        int V = marked.length;
        int count = 0;
        for (Integer v : vertices) {
            count++;
            if (v == null) {
                throw new IllegalArgumentException();
            }
            validateVertex(v);
        }

        if (count == 0) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {

    }
}
