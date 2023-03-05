package algorithms;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Steiner {
    /**
     * Heuristic :
     * - Calculate distance matrix and access matrix for all points from "points" using Floyd-Warshall's algorithm
     * - Construct a complete weighted graph of all possible edges for "hitPoints" using weights from distance matrix
     * - Construct a minimum spanning tree based on a weighted graph using the Kruskal Union-Find algorithm
     * - Replace each edge of the tree with the shortest path from the access matrix
     *
     This class is the class called in DefaultTeam. It computes the steiner tree with and without budget constraints.
     */

    private static Graph g;
    private static ArrayList<Edge> graph;
    private static Kruskal k;
    private static ArrayList<Edge> mstEdges;
    private static ArrayList<Point> hitPoints;

    /**
     * use the shortest path heuristic
     * @param points points on the plan
     * @param hitPoints terminal points on the plan
     * @param edgeThreshold threshold value for edge distance in graph
     */
    public Steiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
        new FloydWarshall(points, edgeThreshold);
        g = new Graph(points, hitPoints, edgeThreshold);
        graph = g.constructGraph();
        k = new Kruskal(graph, hitPoints);
        mstEdges = k.computeMST();
        Steiner.hitPoints = hitPoints;
    }

    /**
     * @return a Minimum Spanning Tree
     */
    public static Tree2D withoutBudget() {//quand il n y as pas de budjet on retourne juste l'arbre obtenu par Kruskal
        return buildTree(Steiner.mstEdges);
    }

    /**
     * @param budget is a Minimum Spanning Tree's weight constraint
     * @return a Minimum Spanning Tree
     */
    public static Tree2D budget(int budget) {

        ArrayList<Point> visited = new ArrayList<>();
        ArrayList<Edge> newEdges = new ArrayList<>();

        visited.add(hitPoints.get(0));

        double cost = 0.0;

        // for each point, construct a list of edges that it is connected to
        // edged are sorted by weight in ascending order
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

        Edge minEdge;

        while (cost <= budget) {
            //find the minimum weighted edge connected to one of visited points
            minEdge = findMinimumEdge(pointToEdge, visited);

            if (cost + minEdge.getWeight() > budget) {
                break;
            }

            Point u = minEdge.getStart();
            Point v = minEdge.getEnd();
            //remove this edge from the list for current points
            pointToEdge.get(u).remove(minEdge);
            pointToEdge.get(v).remove(minEdge);

            if (!visited.contains(u)) {
                visited.add(u);
            }
            if (!visited.contains(v)) {
                visited.add(v);
            }
            cost += minEdge.getWeight();
            //form new edges array
            newEdges.add(minEdge);
        }

        return buildTree(newEdges);

    }

    /**
     * @param pointToEdge for each point, maps the list of edges to which it is connected
     * @param points is a list of points for which we are looking for the minimum connected edge
     * @return a Minimum Edge
     */
    private static Edge findMinimumEdge(HashMap<Point, ArrayList<Edge>> pointToEdge, ArrayList<Point> points) {
        Edge minEdge = new Edge(new Point(0, 0), new Point(0, 0), Double.POSITIVE_INFINITY);
        for (Point point : points) {
            if (!pointToEdge.get(point).isEmpty()) {
                //We take 0 element because it's minimum weighted edge in list
                if (pointToEdge.get(point).get(0).getWeight() < minEdge.getWeight()) {
                    minEdge = pointToEdge.get(point).get(0);
                }
            }
        }
        return minEdge;
    }

    /**
     * @param currentNode is a root node
     * @param parentNode is a parent node for current node
     * @return a Tree2D Steiner Tree
     */
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

    /**
     * @param mstEdges list of edges which form the Steiner tree
     * @return a Tree2D Steiner Tree
     */
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
