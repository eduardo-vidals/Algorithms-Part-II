package computerscience.algorithms.datastructures.graphs.weighted.edge;

/**
 * @author Eduardo
 */
public class DirectedEdgePractice {
    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdgePractice(int v, int w, double weight){
        if (v < 0){
            throw new IllegalArgumentException();
        }

        if (w < 0){
            throw new IllegalArgumentException();
        }

        if (Double.isNaN(weight)){
            throw new IllegalArgumentException();
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from(){
        return v;
    }

    public int to(){
        return w;
    }

    public double weight(){
        return weight;
    }

    public String toString(){
        return v + "->" + w + " " + String.format("%5.2f", weight);
    }

}
