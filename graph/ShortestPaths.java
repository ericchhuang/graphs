package graph;

/* See restrictions in Graph.java. */

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Eric Huang
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        _lG = new LabeledGraph<Double, Double>(G);
        _shortestpath = new LabeledGraph<Integer, Integer>(G);
        _fringe = new PriorityQueue<Integer>(new DistanceComparator<Integer>());
    }

    /** Comparator of type INTEGER using their distances. */
    private class DistanceComparator<Integer> implements Comparator<Integer> {

        /** Compares V against W. Returns positive, 0, or negative, if
         * V is less than, equal to, or more than W. */
        public int compare(Integer v, Integer w) {
            return Double.compare(getWeight((int) v), getWeight((int) w));
        }
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        for (int vertex : _G.vertices()) {
            _lG.setLabel(vertex, Double.POSITIVE_INFINITY);
            _shortestpath.setLabel(vertex, null);
        }
        _lG.setLabel(_source, 0.0);
        for (int vertex : _G.vertices()) {
            _fringe.add(vertex);
        }
        while (!_fringe.isEmpty()) {
            int v = _fringe.remove();
            for (int w : _G.successors(v)) {
                if (getWeight(v) + getWeight(v, w) < getWeight(w)) {
                    setWeight(w, getWeight(v) + getWeight(v, w));
                    _fringe.remove(w); _fringe.add(w);
                    _shortestpath.setLabel(w, v);
                }
            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        if (_G.contains(_dest)) {
            return _dest;
        }
        return 0;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        Integer back = v;
        ArrayList<Integer> path = new ArrayList<Integer>();
        while (back != null) {
            path.add(back);
            back = _shortestpath.getLabel(back);
            if (path.contains(back)) {
                throw new IllegalArgumentException("No path to " + v + ".");
            }
        }
        Collections.reverse(path);
        if (!path.get(0).equals(_source)) {
            throw new IllegalArgumentException("No path to " + v + ".");
        }
        return path;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** Returns the labeled graph of this. */
    public LabeledGraph<Double, Double> getLG() {
        return _lG;
    }

    /** Returns the shortest path of this. */
    public LabeledGraph<Integer, Integer> shortestPath() {
        return _shortestpath;
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** My labelled graph. */
    private final LabeledGraph<Double, Double> _lG;
    /** The fringe.  */
    private final PriorityQueue<Integer> _fringe;
    /** Labelled graph of shortest paths. */
    private final LabeledGraph<Integer, Integer> _shortestpath;
}
