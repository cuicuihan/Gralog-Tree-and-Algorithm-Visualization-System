package gralog.algorithm;

import gralog.structure.DirectedGraph;
import gralog.structure.Edge;
import gralog.structure.Structure;
import gralog.structure.Vertex;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static gralog.algorithm.ShortestPath.dijkstraShortestPath;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MinimumTree {
    @Test
    public void testPrimMST() {
        Structure<Vertex, Edge> structure = new DirectedGraph();

        Vertex v0 = structure.addVertex("v0");
        Vertex v1 = structure.addVertex("v1");
        Vertex v2 = structure.addVertex("v2");
        Vertex v3 = structure.addVertex("v3");
        Vertex v4 = structure.addVertex("v4");

        structure.addEdge(v0, v1, 2); // Assuming Edge constructor takes vertices and weight
        structure.addEdge(v0, v2, 3);
        structure.addEdge(v1, v2, 4);
        structure.addEdge(v1, v3, 5);
        structure.addEdge(v2, v3, 1);
        structure.addEdge(v2, v4, 6);
        structure.addEdge(v3, v4, 7);

        List<Edge> mstEdges = primMST(structure, v0);

        assertEquals(4, mstEdges.size()); // MST should have n-1 edges for n vertices

        // Check that the edges in the MST are the expected ones
        assertTrue(mstEdges.contains(structure.getEdgeByEndVertices(v0, v1)));
        assertTrue(mstEdges.contains(structure.getEdgeByEndVertices(v0, v2)));
        assertTrue(mstEdges.contains(structure.getEdgeByEndVertices(v2, v3)));
        assertTrue(mstEdges.contains(structure.getEdgeByEndVertices(v2, v4)));

        // Sum of the weights of the edges in the MST should be 12
        double sumWeights = 0;
        for (Edge e : mstEdges) {
            sumWeights += e.weight;
        }
        assertEquals(12, sumWeights, 0.001);
    }
}
