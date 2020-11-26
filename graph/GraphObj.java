package graph;

import java.util.ArrayList;
import java.util.Arrays;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Eric Huang
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        _vertices = new ArrayList<Integer>();
        _edges = new ArrayList<int[]>();
    }

    @Override
    public int vertexSize() {
        return _vertices.size();
    }

    @Override
    public int maxVertex() {
        return _vertices.get(_vertices.size() - 1);
    }

    @Override
    public int edgeSize() {
        int size = 0;
        for (int[] element : _edges) {
            if (element [0] != 0 && element[1] != 0) {
                size++;
            }
        }
        if (!isDirected()) {
            return size / 2;
        }
        return size;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        int edges = 0;
        if (contains(v)) {
            for (int[] element : _edges) {
                if (isDirected() && element[0] == v) {
                    edges++;
                } else if (!isDirected()
                        && (element[0]  == v || element[1] == v)) {
                    edges++;
                }
            }
        }
        if (!isDirected()) {
            return edges / 2;
        }
        return edges;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return _vertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        int[] edge = new int[2];
        edge[0] = u;
        edge[1] = v;
        boolean contains = false;
        for (int[] element : _edges) {
            if (Arrays.equals(edge, element)) {
                contains = true;
            }
        }
        return contains;
    }

    @Override
    public int add() {
        boolean added = false;
        int result = 0;
        int i = 0;
        while (!added) {
            if (_vertices.size() <= i || _vertices.get(i) != i + 1) {
                _vertices.add(i, i + 1);
                result = i + 1;
                added = true;
            }
            i++;
        }
        return result;
    }

    @Override
    public int add(int u, int v) {
        if (contains(u, v)) {
            throw new IllegalArgumentException("Edge already exists.");
        }
        if ((contains(u) || contains(v))) {
            int[] edge = new int[2];
            edge[0] = u;
            edge[1] = v;
            _edges.add(edge);
            if (!isDirected()) {
                int[] edge2 = new int[2];
                edge2[0] = v;
                edge2[1] = u;
                _edges.add(edge2);
            }
            return edgeId(u, v);
        } else {
            throw new IllegalArgumentException("Vertices do not exist.");
        }
    }

    @Override
    public void remove(int v) {
        if (_vertices.contains(v)) {
            _vertices.remove(Integer.valueOf(v));
            for (int[] element : _edges) {
                if (element[0] == v) {
                    remove(v, element[1]);
                }
                if (element[1] == v) {
                    remove(element[0], v);
                }
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        int[] edge = new int[2];
        int[] edge2 = new int[2];
        edge[0] = u;
        edge[1] = v;
        edge2[0] = v;
        edge2[1] = u;
        int id = 0;
        for (int i = 0; i < _edges.size(); i++) {
            if (Arrays.equals(edge, _edges.get(i))) {
                _edges.set(i, new int[2]);
            }
            if (!isDirected() && Arrays.equals(edge2, _edges.get(i))) {
                _edges.set(i, new int[2]);
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return Iteration.iteration(_vertices);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        ArrayList<Integer> successors = new ArrayList<Integer>();
        for (int[] element : _edges) {
            if (isDirected() && element[0] == v) {
                successors.add(element[1]);
            } else if (!isDirected()) {
                if (element[1] == v && !successors.contains(element[0])) {
                    successors.add(element[0]);
                } else if (element[0] == v
                        && !successors.contains(element[1])) {
                    successors.add(element[1]);
                }
            }
        }
        return Iteration.iteration(successors);
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> filterededges = new ArrayList<int[]>();
        for (int[] element : _edges) {
            if (element[0] != 0 && element[1] != 0) {
                filterededges.add(element);
            }
        }
        return Iteration.iteration(filterededges);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        if (contains(u, v)) {
            int[] edge = new int[2];
            edge[0] = u;
            edge[1] = v;
            int id = 0;
            for (int i = 0; i < _edges.size(); i++) {
                if (Arrays.equals(edge, _edges.get(i))) {
                    id = i + 1;
                }
            }
            return id;
        }
        return 0;
    }

    /** My vertices. */
    private static ArrayList<Integer> _vertices;
    /** My edges. */
    private static ArrayList<int[]> _edges;
}
