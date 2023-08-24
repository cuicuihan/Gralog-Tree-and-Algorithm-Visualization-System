package gralog.algorithm;

import gralog.structure.*;
import org.junit.Test;

import java.util.*;

import static gralog.algorithm.ShortestPath.dijkstraShortestPath;
import static gralog.structure.StructureMatchers.equalsVertexSet;
import static org.junit.Assert.*;
import static gralog.algorithm.BreadthFirstSearchTree;

public class BFStest {

    @Test
    public void testBreadthFirstSearch() {
        Structure<Vertex, Edge> structure = new DirectedGraph();

        Vertex v0 = structure.addVertex("v0");
        Vertex v1 = structure.addVertex("v1");
        Vertex v2 = structure.addVertex("v2");
        Vertex v3 = structure.addVertex("v3");
        Vertex v4 = structure.addVertex("v4");

        structure.addEdge(v0, v1);
        structure.addEdge(v1, v2);
        structure.addEdge(v2, v3);
        structure.addEdge(v3, v4);
        structure.addEdge(v0, v3);

        HashMap<Vertex, Vertex> predecessor = new HashMap<>();
        HashMap<Vertex, Edge> edgeFromPredecessor = new HashMap<>();

        breadthFirstSearch(v0, predecessor, edgeFromPredecessor);

        assertNull(predecessor.get(v0));
        assertEquals(v0, predecessor.get(v1));
        assertEquals(v1, predecessor.get(v2));
        assertEquals(v0, predecessor.get(v3)); // If there's a choice between v0 -> v3 and v2 -> v3, v0 -> v3 is chosen in BFS
        assertEquals(v3, predecessor.get(v4));

        assertNull(edgeFromPredecessor.get(v0));
        assertEquals(structure.getEdgeByEndVertices(v0, v1), edgeFromPredecessor.get(v1));
        assertEquals(structure.getEdgeByEndVertices(v1, v2), edgeFromPredecessor.get(v2));
        assertEquals(structure.getEdgeByEndVertices(v0, v3), edgeFromPredecessor.get(v3));
        assertEquals(structure.getEdgeByEndVertices(v3, v4), edgeFromPredecessor.get(v4));
    }

}
