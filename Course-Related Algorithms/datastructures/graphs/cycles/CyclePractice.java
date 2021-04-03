package computerscience.algorithms.datastructures.graphs.cycles;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Eduardo
 */

public class CyclePractice {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    public CyclePractice(Graph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, -1, v);
            }
        }
    }
    public boolean hasCycle(){
        return cycle != null;
    }

    public Iterable<Integer> cycle(){
        return cycle;
    }

    private boolean hasSelfLoop(Graph G){
        for (int v = 0; v < G.V(); v++){
            for (int w : G.adj(v)){
                if (v == w){
                    cycle = new Stack<>();
                    cycle.push(v);
                    cycle.push(w);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasParallelEdges(Graph G) {
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (marked[w]) {
                    cycle = new Stack<>();
                    cycle.push(v);
                    cycle.push(w);
                    cycle.push(v);
                    return true;
                }
                marked[w] = true;
            }

            for (int w : G.adj(v)) {
                marked[w] = false;
            }

        }
        return false;
    }

    private void dfs(Graph G, int u, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (cycle != null) {
                return;
            }

            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, v, w);
            } else if (w != u) {
                cycle = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1,2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(4, 5);
        G.addEdge(5, 0);


        CyclePractice finder = new CyclePractice(G);
        System.out.println("parallel edges?: " + finder.hasParallelEdges(G));
        System.out.println("cycle?: " + finder.hasCycle());
        if (finder.hasCycle()) {
            for (int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else {
            StdOut.println("Graph is acyclic");
        }
    }


}
