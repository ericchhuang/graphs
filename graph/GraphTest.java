package graph;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;

/** Unit tests for the Graph class.
 *  @author Eric Huang
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void basicVertexTest() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 1; i <= 10; i++) {
            assertEquals("Vertex add returns wrong value.", i, g.add());
        }
        assertEquals(10, g.vertexSize());
        for (int i = 1; i <= 10; i++) {
            assertTrue("Vertex contains is wrong.", g.contains(i));
        }
        g.remove(2);
        g.remove(5);
        g.remove(6);
        g.remove(7);
        assertEquals("Vertex size should be 6 after removing.",
                6, g.vertexSize());
        assertEquals(2, g.add());
        assertEquals(5, g.add());
    }

    @Test
    public void basicEdgeTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        assertFalse("Should not contain vertex 1,2.", g.contains(1, 2));
        assertEquals(1, g.add(1, 2));
        assertTrue("Should contain vertex 1,2.", g.contains(1, 2));
        assertEquals(2, g.add(2, 1));
        assertEquals(3, g.add(3, 1));
        assertEquals("Edge size should be 3.", 3, g.edgeSize());
        g.remove(2, 1);
        assertEquals("Edge size should change after removing.",
                2, g.edgeSize());
        assertEquals(3, g.edgeId(3, 1));
        assertEquals(4, g.add(2, 1));
    }

    @Test
    public void outDegreeTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        for (int i = 1; i <= 10; i++) {
            assertEquals(0, g.outDegree(i));
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(3, 1);
        assertEquals("Outdegree should be 3.", 3, g.outDegree(1));
        g.remove(1, 2);
        assertEquals(2, g.outDegree(1));
        g.add(1, 2);
        assertEquals(3, g.outDegree(1));
    }

    @Test
    public void inDegreeTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        for (int i = 1; i <= 10; i++) {
            assertEquals(0, g.outDegree(i));
        }
        g.add(2, 1);
        g.add(3, 1);
        g.add(4, 1);
        g.add(1, 3);
        assertEquals("Indegree should be 3.", 3, g.inDegree(1));
        g.remove(2, 1);
        assertEquals(2, g.inDegree(1));
        g.add(2, 1);
        assertEquals(3, g.inDegree(1));
    }

    @Test
    public void edgesUndirectedTest() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(1, 2);
        assertEquals(1, g.edgeSize());
        assertTrue(g.contains(2, 1));
        g.add(1, 4);
        assertEquals(2, g.edgeSize());
        assertTrue(g.contains(4, 1));
    }

    @Test
    public void edgeIteration() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(3, 1);
        g.remove(1, 3);
        Iteration i = g.edges();
        assertTrue(i.hasNext());
        assertEquals("[1, 2]", Arrays.toString((int[]) i.next()));
        assertEquals("[1, 4]", Arrays.toString((int[]) i.next()));
        assertEquals("[3, 1]", Arrays.toString((int[]) i.next()));
        assertFalse(i.hasNext());
    }

    @Test
    public void predecessorsIteration() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(2, 3);
        g.add(1, 3);
        g.add(4, 3);
        Iteration i = g.predecessors(3);
        assertTrue(i.hasNext());
        assertEquals(2, i.next());
        assertEquals(1, i.next());
        assertEquals(4, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void successorsIteration() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(3, 2);
        g.add(3, 1);
        g.add(3, 4);
        Iteration i = g.successors(3);
        assertTrue(i.hasNext());
        assertEquals(2, i.next());
        assertEquals(1, i.next());
        assertEquals(4, i.next());
        assertFalse(i.hasNext());
    }


    @Test
    public void basicUndirectedTest() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 1; i <= 10; i++) {
            g.add();
        }
        g.add(3, 3);
        g.add(2, 3);
        g.add(1, 3);
        g.add(4, 3);
        assertEquals(4, g.degree(3));
        g.remove(3, 4);
        assertEquals(3, g.degree(3));
        Iteration i = g.successors(3);
        assertEquals(3, i.next());
        assertEquals(2, i.next());
        assertEquals(1, i.next());
        assertFalse(i.hasNext());
    }
}
