package algorithms;

import java.awt.Point;
import java.util.*;

public class Kruskal { //Heuristique des plus court chemains
    HashMap<Edge, Double> graph; // Complete weighted graph K
    private List<Edge> edges;
    private ArrayList<Point> hitPoints;

    // Constructor
    public Kruskal(HashMap<Edge, Double> graph, ArrayList<Point> hitPoints) {
        this.graph = graph;
        this.hitPoints = hitPoints;
    }

    // Compute the minimum spanning tree T using Kruskal's algorithm
    public Tree2D computeMST() {
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

        // Initialize H as a new HashMap to store the resulting graph
        ArrayList<Edge> H = new ArrayList<>();

        // Iterate through the edges in the minimum spanning tree T
        for (Edge e : mstEdges) {
            Point u = e.getStart();
            Point v = e.getEnd();

            // Calculate the shortest path between u and v in G
            ArrayList<Edge> path = Path.getShortestPath(u, v);

            // Add each edge in the path to H, with its weight equal to the weight of the original edge in T
            for (int i = 0; i < path.size() - 1; i++) {
                H.add(path.get(i));
            }
        }

        HashMap<Point, Tree2D> pointToTree = new HashMap<>();
        for (Edge e : H) {
            Point u = e.getStart();
            Point v = e.getEnd();

            if (!pointToTree.containsKey(u)) {
                pointToTree.put(u, new Tree2D(u, new ArrayList<>()));
            }

            if (!pointToTree.containsKey(v)) {
                pointToTree.put(v, new Tree2D(v, new ArrayList<>()));
            }

            pointToTree.get(u).getSubTrees().add(pointToTree.get(v));
            pointToTree.get(v).getSubTrees().add(pointToTree.get(u));
        }

        // Build the tree recursively
        Tree2D steinerTree = buildSubtree(pointToTree.get(H.get(0).getStart()), null);

        return steinerTree;
    }

    private static Tree2D buildSubtree(Tree2D currentNode, Tree2D parentNode) {
        // Remove the parent node from the current node's subtrees
        if (parentNode != null) {
            currentNode.getSubTrees().remove(parentNode);
        }

        // Recursively build subtrees
        ArrayList<Tree2D> subtrees = new ArrayList<>();
        for (Tree2D child : currentNode.getSubTrees()) {
            subtrees.add(buildSubtree(child, currentNode));
        }

        return new Tree2D(currentNode.getRoot(), subtrees);
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
