package algorithms;

import java.awt.Point;
import java.util.*;

public class Kruskal {
    HashMap<Edge, Double> graph; // Complete weighted graph K
    private List<Edge> edges;
    private ArrayList<Point> hitPoints;

    // Constructor
    public Kruskal(HashMap<Edge, Double> graph, ArrayList<Point> hitPoints) {
        this.graph = graph;
        this.hitPoints = hitPoints;
    }

    // Compute the minimum spanning tree T using Kruskal's algorithm
    public ArrayList<Edge> computeMST() {
        // Sort the edges in graph in increasing order of weight
        edges = new ArrayList<Edge>(graph.keySet());
        Collections.sort(edges, new Comparator<Edge>() {
            public int compare(Edge e1, Edge e2) {
                return Double.compare(graph.get(e1), graph.get(e2));
            }
        });

        UnionFind uf = new UnionFind(this.hitPoints);

        ArrayList<Edge> mstEdges = new ArrayList<>();
        for (Edge edge : edges) {
            Point u = edge.getStart();
            Point v = edge.getEnd();
            if (uf.find(u) != uf.find(v)) {
                mstEdges.add(edge);
                uf.union(u, v);
            }
        }

        return mstEdges;
    }



    // Helper class to implement the Union-Find algorithm
    class UnionFind {
        HashMap<Point, Point> parent;

        UnionFind(ArrayList<Point> set) {
            parent = new HashMap<>();
            for (Point p : set) {
                parent.put(p, p);
            }
        }

        Point find(Point p) {
            Point root = p;
            while (parent.get(root) != root) {
                root = parent.get(root);
            }
            while (parent.get(p) != root) {
                Point next = parent.get(p);
                parent.put(p, root);
                p = next;
            }
            return root;
        }

        void union(Point u, Point v) {
            Point rootU = find(u);
            Point rootV = find(v);
            parent.put(rootU, rootV);
        }
    }
}
