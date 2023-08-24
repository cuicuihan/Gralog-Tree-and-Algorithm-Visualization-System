/*
package gralog.algorithm;
import gralog.progresshandler.ProgressHandler;
import gralog.structure.Edge;
import gralog.structure.Structure;
import gralog.structure.Vertex;

import java.util.*;

public class MinimuTree extends Algorithm{
    public static List<Edge> primMST(Structure s, Vertex start) {
        // 用于存储最小生成树的边
        List<Edge> mstEdges = new ArrayList<>();

        // 用于存储每个顶点到生成树的最小距离
        HashMap<Vertex, Double> distances = new HashMap<>();

        // 用于存储每个顶点的上一个边
        HashMap<Vertex, Edge> edgeToVertex = new HashMap<>();

        // 用于存储尚未处理的顶点
        HashSet<Vertex> setQ = new HashSet<>();
        setQ.addAll(s.getVertices());

        // 优先队列用于根据距离排序顶点
        PriorityQueue<Vertex> pq = new PriorityQueue<>((v1, v2) -> distances.get(v1).compareTo(distances.get(v2)));

        // 初始化距离
        for (Vertex v : s.getVertices()) {
            if (v.equals(start)) {
                distances.put(v, 0d);
            } else {
                distances.put(v, Double.POSITIVE_INFINITY);
            }
        }

        pq.add(start);

        while (!pq.isEmpty()) {
            Vertex u = pq.poll();
            setQ.remove(u);

            // 将当前顶点的前一条边添加到最小生成树
            if (edgeToVertex.containsKey(u)) {
                mstEdges.add(edgeToVertex.get(u));
            }

            for (Edge e : u.getIncidentEdges()) {
                Vertex other = e.getTarget();
                if (other == u)
                    other = e.getSource();

                if (!setQ.contains(other))
                    continue;

                double weight = e.weight;

                if (weight < distances.get(other)) {
                    distances.put(other, weight);
                    edgeToVertex.put(other, e);
                    pq.add(other); // 更新后的顶点重新添加到优先队列中
                }
            }
        }

        return mstEdges; // 返回最小生成树的边
    }

}
*/
