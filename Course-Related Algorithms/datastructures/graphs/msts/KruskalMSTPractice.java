package computerscience.algorithms.datastructures.graphs.msts;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;

import java.util.Arrays;

/**
 * @author Eduardo
 */
public class KruskalMSTPractice {
    private double weight;
    private Queue<Edge> mst = new Queue<>();

    public KruskalMSTPractice(EdgeWeightedGraph G){
        Edge[] edges = new Edge[G.E()];
        int t = 0;
        for (Edge e : G.edges()){
            edges[t++] = e;
        }
        Arrays.sort(edges);

        UF uf = new UF(G.V());
        for (int i = 0; i < G.E() && mst.size() < G.V() - 1;i++){
            Edge e = edges[i];
            int v = e.either();
            int w = e.other(v);

            if (uf.find(v) != uf.find(w)){
                uf.union(v, w);
                mst.enqueue(e);
                weight += e.weight();
            }
        }
    }

    public Iterable<Edge> edges(){
        return mst;
    }

    public double weight(){
        return weight;
    }
}
