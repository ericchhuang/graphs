package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Eric Huang
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        _lG = getLG();
        _shortestpath = shortestPath();
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        if (getLG().contains(v)) {
            return getLG().getLabel(v);
        }
        return Integer.MAX_VALUE;
    }

    @Override
    protected void setWeight(int v, double w) {
        getLG().setLabel(v, w);
    }

    @Override
    public int getPredecessor(int v) {
        return _shortestpath.getLabel(v);
    }

    @Override
    protected void setPredecessor(int v, int u) {
        _shortestpath.setLabel(v, u);
    }

    /** The labelled graph. */
    private LabeledGraph<Double, Double> _lG;
    /** Labelled graph of shortest paths. */
    private final LabeledGraph<Integer, Integer> _shortestpath;
}
