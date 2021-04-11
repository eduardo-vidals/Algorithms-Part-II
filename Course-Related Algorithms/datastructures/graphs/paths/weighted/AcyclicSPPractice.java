package computerscience.algorithms.datastructures.graphs.paths.weighted;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Topological;

/**
 * @author Eduardo
 */
public class AcyclicSPPractice {
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public AcyclicSPPractice(EdgeWeightedDigraph G, int s){
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        validateVertex(s);

        for(int v = 0; v < G.V(); v++){
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0.0;
        Topological topological = new Topological(G);
        if (!topological.hasOrder()){
            throw new IllegalArgumentException();
        }
        for(int v : topological.order()){
            for(DirectedEdge e : G.adj(v)){
                relax(e);
            }
        }
    }

    private void relax(DirectedEdge e){
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()){
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public double distTo(int v){
        validateVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v){
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v){
        validateVertex(v);
        if(!hasPathTo(v)){
            return null;
        }
        Stack<DirectedEdge> path = new Stack<>();
        for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]){
            path.push(e);
        }
        return path;
    }

    private void validateVertex(int v){
        int V = distTo.length;
        if (v < 0 || v >= V){
            throw new IllegalArgumentException();
        }
    }
}
