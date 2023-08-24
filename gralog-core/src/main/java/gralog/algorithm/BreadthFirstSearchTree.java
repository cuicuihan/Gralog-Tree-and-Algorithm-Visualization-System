/* This file is part of Gralog, Copyright (c) 2016-2018 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later. */
package gralog.algorithm;

import gralog.progresshandler.ProgressHandler;
import gralog.structure.Edge;
import gralog.structure.Structure;
import gralog.structure.Vertex;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@AlgorithmDescription(
        name = "Breadth-First Search-Tree",
        text = "Constructs a Breadth-First Search-Tree",
        url = "https://en.wikipedia.org/wiki/Breadth-first_search"
)
public class BreadthFirstSearchTree extends Algorithm {

    public static String breadthFirstSearch(Vertex start,
                                          HashMap<Vertex, Vertex> predecessor,
                                          HashMap<Vertex, Edge> edgeFromPredecessor) {
        //arraydeque是双端队列的数组实现
        ArrayDeque<Vertex> queue = new ArrayDeque<>();
        //将开始节点添加到数组最后面
        queue.addLast(start);
        //当前节点，以及前一个节点
        predecessor.put(start, null);
        //当前节点以及前一个边
        edgeFromPredecessor.put(start, null);
        int count=0;
        String result="";
        //result = "Strongly connected components:\n\nvertex id\t\tcomponent number\n";
        while (!queue.isEmpty()) {
            //删除第一个元素，并返回值
            Vertex v = queue.removeFirst();
            count++;
            result+=count+"\t"+v.getId()+"father\n";
            for (Edge e : v.getIncidentEdges()) {
                //排除传入边
                if (e.getSource() != v && e.isDirected) // incoming (directed) edge
                    continue;
                //找到传出边
                Vertex other = e.getTarget();
                if (other == v)
                    other = e.getSource();

                if (predecessor.containsKey(other)) // successor already in the tree
                    continue;
                result+="\t"+other.getId();
                predecessor.put(other, v);
                edgeFromPredecessor.put(other, e);
                queue.addLast(other);
            }
            result+="\n";
        }
        return result;
    }
/*    public static void breadthFirstSearch(Vertex start,
                                          HashMap<Vertex, Vertex> predecessor,
                                          HashMap<Vertex, Edge> edgeFromPredecessor) {
        //arraydeque是双端队列的数组实现
        ArrayDeque<Vertex> queue = new ArrayDeque<>();
        //将开始节点添加到数组最后面
        queue.addLast(start);
        //当前节点，以及前一个节点
        predecessor.put(start, null);
        //当前节点以及前一个边
        edgeFromPredecessor.put(start, null);
        while (!queue.isEmpty()) {
            //删除第一个元素，并返回值
            Vertex v = queue.removeFirst();
            for (Edge e : v.getIncidentEdges()) {
                //排除传入边
                if (e.getSource() != v && e.isDirected) // incoming (directed) edge
                    continue;
                //找到传出边
                Vertex other = e.getTarget();
                if (other == v)
                    other = e.getSource();

                if (predecessor.containsKey(other)) // successor already in the tree
                    continue;

                predecessor.put(other, v);
                edgeFromPredecessor.put(other, e);
                queue.addLast(other);
            }
        }
    }*/



    public Object run(Structure s, AlgorithmParameters p, Set<Object> selection,
                      ProgressHandler onprogress) {
        HashMap<Vertex, Vertex> predecessor = new HashMap<>();
        HashMap<Vertex, Edge> edgeFromPredecessor = new HashMap<>();
        Vertex v = selectedUniqueVertex(selection);
        //String result="Strongly connected components:\n\nvertex id\t\tcomponent number\n";
        if (v == null)
            return "Please, select exactly one vertex to start BFS from.";
        String result=breadthFirstSearch(v, predecessor, edgeFromPredecessor);

        HashSet<Edge> tree = new HashSet<>();
        tree.addAll(edgeFromPredecessor.values());
        tree.remove(null);
        return tree;
        return r;
    }

}

