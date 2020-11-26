package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Eric Huang
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        int edges = 0;
        if (contains(v)) {
            for (int[] element : edges()) {
                if (element[1] == v) {
                    edges++;
                }
            }
        }
        return edges;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        ArrayList<Integer> predecessors = new ArrayList<Integer>();
        for (int[] element : edges()) {
            if (element[1] == v) {
                predecessors.add(element[0]);
            }
        }
        return Iteration.iteration(predecessors);
    }
}
