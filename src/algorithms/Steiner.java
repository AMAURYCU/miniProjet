package algorithms;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Steiner {
    /*
    Cette classe est la classe appelée dans DefaultTeam. Elle calcule l'arbre steiner avec et sans contrainte de budget.
    Pour ce faire, nous utilisons l'heuristique des plus courts chemins
     */


    private static Graph g;
    private static ArrayList<Edge> graph;
    private static Kruskal k;
    private static ArrayList<Edge> mstEdges;
    private static ArrayList<Point> hitPoints;

    public Steiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
        new FloydWarshall(points, edgeThreshold);
        g = new Graph(points, hitPoints, edgeThreshold);
        graph = g.constructGraph();
        k = new Kruskal(graph, hitPoints);
        mstEdges = k.computeMST();
        Steiner.hitPoints = hitPoints;
    }

    public static Tree2D withoutBudget() {//quand il n y as pas de budjet on retourne juste l'arbre obtenu par Kruskal
        return buildTree(Steiner.mstEdges);
    }

    public static Tree2D budget(int budget) {


        ArrayList<Point> visited = new ArrayList<>();
        visited.add(hitPoints.get(0));

        double cost = 0.0;

        HashMap<Point, ArrayList<Edge>> pointToEdge = new HashMap<>();
        for (Edge e : mstEdges) {
            Point u = e.getStart();
            Point v = e.getEnd();

            if (!pointToEdge.containsKey(u)) {
                pointToEdge.put(u, new ArrayList<>());
            }

            if (!pointToEdge.containsKey(v)) {
                pointToEdge.put(v, new ArrayList<>());
            }

            pointToEdge.get(u).add(e);
            pointToEdge.get(v).add(e);
        }

        for (ArrayList<Edge> edgeList : pointToEdge.values()) {
            Collections.sort(edgeList, new Comparator<Edge>() {
                public int compare(Edge e1, Edge e2) {
                    return Double.compare(e1.getWeight(), e2.getWeight());
                }
            });
        }

        Edge minEdge = new Edge(new Point(0, 0), new Point(0, 0), 0);

        while (cost <= budget) {
            minEdge = findMinimumEdge(pointToEdge, visited);

            if (cost + minEdge.getWeight() > budget) {
                break;
            }

            Point P = minEdge.getStart();
            Point Q = minEdge.getEnd();
            pointToEdge.get(P).remove(minEdge);
            pointToEdge.get(Q).remove(minEdge);
            if (!visited.contains(P)) {
                visited.add(P);
            }
            if (!visited.contains(Q)) {
                visited.add(Q);
            }
            cost += minEdge.getWeight();
        }

        visited.remove(minEdge.getStart());
        visited.remove(minEdge.getEnd());

        Kruskal newK = new Kruskal(graph, visited);
        ArrayList<Edge> newEdges = newK.computeMST();

        return buildTree(newEdges);

    }

    private static Edge findMinimumEdge(HashMap<Point, ArrayList<Edge>> pointToEdge, ArrayList<Point> points) {
        Edge minEdge = new Edge(new Point(0, 0), new Point(0, 0), Double.POSITIVE_INFINITY);
        for (Point point : points) {
            if (!pointToEdge.get(point).isEmpty()) {
                if (pointToEdge.get(point).get(0).getWeight() < minEdge.getWeight()) {
                    minEdge = pointToEdge.get(point).get(0);
                }
            }
        }
        return minEdge;
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


    private static Tree2D buildTree(ArrayList<Edge> mstEdges) {
        // Initialize H as a new HashMap to store the resulting graph
        ArrayList<Edge> H = new ArrayList<>();

        // Iterate through the edges in the minimum spanning tree T
        for (Edge e : mstEdges) {
            Point u = e.getStart();
            Point v = e.getEnd();

            // Calculate the shortest path between u and v in G
            ArrayList<Edge> path = FloydWarshall.getShortestPath(u, v);

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
        return buildSubtree(pointToTree.get(H.get(0).getStart()), null);
    }

}
