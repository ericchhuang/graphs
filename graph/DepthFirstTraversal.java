package graph;

/* See restrictions in Graph.java. */

import java.util.LinkedList;
import java.util.Collections;

/** Implements a depth-first traversal of a graph.  Generally, the
 *  client will extend this class, overriding the visit and
 *  postVisit methods, as desired (by default, they do nothing).
 *  @author Eric Huang
 */
public class DepthFirstTraversal extends Traversal {

    /**
     * A depth-first Traversal of G.
     */
    protected DepthFirstTraversal(Graph G) {
        super(G, Collections.asLifoQueue(new LinkedList<Integer>()));
    }

    @Override
    protected boolean visit(int v) {
        return super.visit(v);
    }

    @Override
    protected boolean postVisit(int v) {
        return super.postVisit(v);
    }
}
