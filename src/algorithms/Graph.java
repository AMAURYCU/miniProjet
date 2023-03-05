package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class Graph {
    private ArrayList<Point> hitPoints;

    // Constructor to initialize the graph
    public Graph(ArrayList<Point> hitPoints) {
        this.hitPoints = hitPoints;
    }

    // Method to construct the complete weighted graph K
    public ArrayList<Edge> constructGraph() { //O(n²)

        ArrayList<Edge> graph = new ArrayList<>();

        for (int i = 0; i < hitPoints.size(); i++) {
            for (int j = i + 1; j < hitPoints.size(); j++) {
                Point u = hitPoints.get(i);
                Point v = hitPoints.get(j);

                Edge edge = new Edge(u, v);
                graph.add(edge);
            }
        }
        return graph;
    }

}
