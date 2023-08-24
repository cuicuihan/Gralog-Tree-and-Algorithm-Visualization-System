/* This file is part of Gralog, Copyright (c) 2016-2018 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later. */
package gralog.algorithm;

import gralog.progresshandler.ProgressHandler;
import gralog.structure.Edge;
import gralog.structure.Structure;
import gralog.structure.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 */
@AlgorithmDescription(
        name = "Shortest Path",
        text = "Finds a shortest path between two selected vertice, using Dijkstra's Algorithm",
        url = "https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm"
)
public class ShortestPath extends Algorithm {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    /*public static void dijkstraShortestPath(Structure s, Vertex start,
                                            Vertex target,
                                            HashMap<Vertex, Vertex> predecessor,
                                            HashMap<Vertex, Edge> edgeFromPredecessor,
                                            HashMap<Vertex, Double> distances) {
        //predecessor前一个处理的节点 edgeFromPredecessor前一个处理的边
        //setQ存储所有要处理的节点
        HashSet<Vertex> setQ = new HashSet<>();
        //添加所有的节点
        setQ.addAll(s.getVertices());
        //选择开始的节点，开始节点的前置节点为NULL，<当前节点，前一个节点><当前节点，前一个边><当前节点，初始节点到当前节点的距离>
        predecessor.put(start, null);
        edgeFromPredecessor.put(start, null);
        distances.put(start, 0d);
        //当处理节点不为空时
        while (!setQ.isEmpty()) {
            Vertex u = null;
            //u=null时代表初始节点，不等于null时说明有好几个以及处理好的节点，接下来需要找距离最小的那个节点
            for (Vertex uit : setQ)
                if (distances.containsKey(uit))
                    if (u == null || (distances.get(uit) < distances.get(u)))
                        u = uit;
            if (u == null) // happens if there are unreachable vertices
                break;

            setQ.remove(u);//每处理一个节点都要将起从处理序列中去除
            if (u == target) // found desired shortest path
                break;
            //当前距离
            Double distu = distances.get(u);

            for (Edge e : u.getIncidentEdges()) {
                //判断边的类型，因为有传入和传出边
                if (e.getSource() != u && e.isDirected) // incoming (directed) edge
                    continue;
                //找到传出边，该节点所有的下一个顶点
                Vertex other = e.getTarget();
                if (other == u)
                    other = e.getSource();
                //检查该顶点是否被处理过
                if (!setQ.contains(other)) // shortest path already found
                    continue;
                //更新距离
                Double alt = distu + e.weight;
                //如果该点从来没有处理过就直接更新（相当于无穷大），否则判断更新后的距离是否小于之前的距离，小则更新
                if ((!distances.containsKey(other)) || (alt < distances.get(other))) {
                    distances.put(other, alt);//所求节点到当点节点的最短距离
                    predecessor.put(other, u);//当前节点以及前一个节点
                    edgeFromPredecessor.put(other, e);//当前节点以及当前节点的前一条边
                    //<当前节点，前一个节点><当前节点，前一个边><当前节点，初始节点到当前节点的距离>
                }
            }
        }
    }*/
    public static void dijkstraShortestPath(Structure s, Vertex start,
                                            Vertex target,
                                            HashMap<Vertex, Vertex> predecessor,
                                            HashMap<Vertex, Edge> edgeFromPredecessor,
                                            HashMap<Vertex, Double> distances) {

        PriorityQueue<Vertex> pq = new PriorityQueue<>((v1, v2) -> distances.get(v1).compareTo(distances.get(v2)));
        HashSet<Vertex> setQ = new HashSet<>();

        setQ.addAll(s.getVertices());
        predecessor.put(start, null);
        edgeFromPredecessor.put(start, null);
        distances.put(start, 0d);

        pq.add(start); // 将起始点添加到优先队列中

        while (!pq.isEmpty()) {
            Vertex u = pq.poll();
            setQ.remove(u);

            if (u == target)
                break;

            double distu = distances.get(u);

            for (Edge e : u.getIncidentEdges()) {
                if (e.getSource() != u && e.isDirected)
                    continue;

                Vertex other = e.getTarget();
                if (other == u)
                    other = e.getSource();

                if (!setQ.contains(other))
                    continue;

                double alt = distu + e.weight;

                if ((!distances.containsKey(other)) || (alt < distances.get(other))) {
                    distances.put(other, alt);
                    predecessor.put(other, u);
                    edgeFromPredecessor.put(other, e);
                    pq.add(other); // 更新后的顶点重新添加到优先队列中
                }
            }
        }
    }
    public static void dijkstraShortestPath(Structure s, Vertex start,
                                            HashMap<Vertex, Vertex> predecessor,
                                            HashMap<Vertex, Edge> edgeFromPredecessor,
                                            HashMap<Vertex, Double> distances) {
        ShortestPath.dijkstraShortestPath(s, start, null, predecessor, edgeFromPredecessor, distances);
    }

    public Object run(Structure s, AlgorithmParameters p, Set<Object> selection,
                      ProgressHandler onprogress) throws Exception {
        if (s.getVertices().size() == 0)
            return ("The structure should not be empty.");
        for (Edge e : (Set<Edge>) s.getEdges()) {
            if (e.weight < 0d)
                return ("Dijkstra's Algorithm requires non-negative edge weights");
        }


        Vertex v = selectedUniqueVertex(selection);
        if (v == null)
            return "Please, select exactly one vertex: then all shortest paths to all other vertices will be computed.";


        // deselect all edges
        HashSet<Object> selectedEdges = new HashSet<>();
        for (Object o : selection)
            if (o instanceof Edge)
                selectedEdges.add(o);
        selection.removeAll(selectedEdges);

        HashMap<Vertex, Vertex> predecessor = new HashMap<>();
        HashMap<Vertex, Edge> edgeFromPredecessor = new HashMap<>();
        HashMap<Vertex, Double> distances = new HashMap<>();


        ShortestPath.dijkstraShortestPath(s, v, predecessor, edgeFromPredecessor, distances);

        selectedEdges.clear();
        //移除初始节点 edgeFromPredecessor.put(other, e);//当前节点以及当前节点的前一条边
        edgeFromPredecessor.remove(v);
        selectedEdges.addAll(edgeFromPredecessor.values());

        String result = "Shortest  path:\n\nvertex id\t\t\tdistance\n";

        for (Vertex va : distances.keySet()){
            result += va.getId() + "\t\t\t\t" + distances.get(va) + "\n";
            Vertex temp=va;
            while (predecessor.get(temp)!=null){
                result += temp.getId() + "\t<-";
                temp=predecessor.get(temp);
            }
            result+=temp.getId()+"\n";
        }

        return selectedEdges;
    }
}
