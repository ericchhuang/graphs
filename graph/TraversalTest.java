package graph;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

public class TraversalTest {
    private class BFS extends Traversal {

        protected BFS(Graph G, ArrayList<Integer> list) {
            super(G, new LinkedList<Integer>());
            _list = list;
        }

        @Override
        public boolean visit(int v) {
            _list.add(v);
            return true;
        }

        ArrayList<Integer> _list;
    }

    @Test
    public void bfsTest() {
        Graph g  = new UndirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 7);
        g.add(2, 5);
        g.add(2, 6);
        g.add(5, 7);
        g.add(4, 6);
        g.add(6, 3);
        g.add(3, 8);
        ArrayList<Integer> list = new ArrayList<Integer>();
        BFS traversal = new BFS(g, list);
        traversal.traverse(1);
        assertEquals("[1, 2, 4, 7, 5, 6, 3, 8]", list.toString());
    }

    private class PreDFS extends Traversal {

        protected PreDFS(Graph G, ArrayList<Integer> list) {
            super(G, Collections.asLifoQueue(new LinkedList<Integer>()));
            _list = list;

        }

        @Override
        public boolean visit(int v) {
            _list.add(v);
            return true;
        }

        ArrayList<Integer> _list;
    }

    @Test
    public void preDFSTest() {
        Graph g  = new UndirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 7);
        g.add(2, 5);
        g.add(2, 6);
        g.add(5, 7);
        g.add(4, 6);
        g.add(6, 3);
        g.add(3, 8);
        ArrayList<Integer> list = new ArrayList<Integer>();
        PreDFS traversal = new PreDFS(g, list);
        traversal.traverse(1);
        assertEquals("[1, 7, 5, 2, 6, 3, 8, 4]", list.toString());
    }

    private class PostDFS extends Traversal {

        protected PostDFS(Graph G, ArrayList<Integer> list) {
            super(G, Collections.asLifoQueue(new LinkedList<Integer>()));
            _list = list;

        }

        @Override
        public boolean postVisit(int v) {
            _list.add(v);
            return true;
        }

        @Override
        public boolean shouldPostVisit(int v) {
            return true;
        }
        ArrayList<Integer> _list;
    }

    @Test
    public void postDFSTest() {
        Graph g  = new UndirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(1, 7);
        g.add(2, 5);
        g.add(2, 6);
        g.add(5, 7);
        g.add(4, 6);
        g.add(6, 3);
        g.add(3, 8);
        ArrayList<Integer> list = new ArrayList<Integer>();
        PostDFS traversal = new PostDFS(g, list);
        traversal.traverse(1);
        assertEquals("[8, 3, 4, 6, 2, 5, 7, 1]", list.toString());
    }


    private class SP extends SimpleShortestPaths {
        SP(Graph g, int start, int dest) {
            super(g, start, dest);
        }

        public void setWeight(int u, int v, double w) {
            getLG().setLabel(u, v, w);
        }

        @Override
        protected double getWeight(int u, int v) {
            return getLG().getLabel(u, v);
        }

        @Override
        public double getWeight(int v) {
            return getLG().getLabel(v);
        }

        @Override
        public void setWeight(int v, double w) {
            getLG().setLabel(v, w);
        }
    }

    @Test
    public void shortestPathTest2() {
        Graph g = new DirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(2, 3);
        g.add(3, 4);
        g.add(4, 5);
        g.add(5, 6);
        g.add(6, 7);
        g.add(7, 8);
        g.add(8, 9);
        g.add(9, 10);
        SP sP = new SP(g, 1, 8);
        sP.setWeight(1, 2, 4.7);
        sP.setWeight(2, 3, 4.1);
        sP.setWeight(3, 4, 2.0);
        sP.setWeight(4, 5, 1.0);
        sP.setWeight(5, 6, 1.2);
        sP.setWeight(6, 7, 0.9);
        sP.setWeight(7, 8, 2.8);
        sP.setWeight(8, 9, 5.0);
        sP.setWeight(9, 10, 5.0);
        sP.setPaths();
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8]", sP.pathTo(8).toString());
        assertEquals(16.7, sP.getWeight(8), 0);
    }

    @Test
    public void predTest() {
        Graph g = new DirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(2, 3);
        g.add(3, 4);
        g.add(4, 5);
        g.add(5, 6);
        g.add(6, 7);
        g.add(7, 8);
        g.add(8, 9);
        g.add(9, 10);
        SP sP = new SP(g, 1, 10);
        sP.setWeight(1, 2, 4.7);
        sP.setWeight(2, 3, 4.1);
        sP.setWeight(3, 4, 2.0);
        sP.setWeight(4, 5, 1.0);
        sP.setWeight(5, 6, 1.2);
        sP.setWeight(6, 7, 0.9);
        sP.setWeight(7, 8, 2.8);
        sP.setWeight(8, 9, 5.0);
        sP.setWeight(9, 10, 5.0);
        sP.setPaths();
        for (int i = 0; i > 11; i++) {
            assertEquals(i - 1, sP.getPredecessor(i));
        }
        sP.setPredecessor(5, 10);
        assertEquals(10, sP.getPredecessor(5));
    }
}

