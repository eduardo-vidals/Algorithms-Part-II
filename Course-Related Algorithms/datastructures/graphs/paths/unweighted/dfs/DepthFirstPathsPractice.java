package computerscience.algorithms.datastructures.graphs.paths.unweighted.dfs;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

/**
 * @author Eduardo
 */
public class DepthFirstPathsPractice {
    private final boolean[] marked;
    private final int[] edgeTo;
    private final int s;

    public DepthFirstPathsPractice(Graph G, int s){
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    private void dfs(Graph G, int v){
        marked[v] = true;
        for(int w : G.adj(v)){
            if (!marked[w]){
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v){
        validateVertex(v);
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v){
        validateVertex(v);
        if (!hasPathTo(v)){
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for(int x = v; x != s; x = edgeTo[x]){
            path.push(x);
        }
        path.push(s);
        return path;
    }

    private void validateVertex(int v){
        int V = marked.length;
        if (v < 0 || v >= V){
            throw new IllegalArgumentException();
        }
    }

}
