package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private List<Point> points;
    private List<Point> hitPoints;
    private int edgeThreshold;
    private Tree2D value;

    // Constructor to initialize the graph
    public Graph(ArrayList<Point> points, ArrayList<Point> hitPoints, int edgeThreshold) {
        this.points = points;
        this.hitPoints = hitPoints;
        this.edgeThreshold = edgeThreshold;

        new Path(points, edgeThreshold);
    }

    // Method to construct the complete weighted graph K
    public HashMap<Edge, Double> constructGraph() {

        HashMap<Edge, Double> graph = new HashMap<>();

        for (int i = 0; i < hitPoints.size(); i++) {
            for (int j = i + 1; j < hitPoints.size(); j++) {
                Point u = hitPoints.get(i);
                Point v = hitPoints.get(j);
                double shortestDist = Path.getShortestDist(u, v);
                Edge edge = new Edge(u, v, shortestDist);
                graph.put(edge, shortestDist);
            }
        }
        return graph;
    }
    public List<Point> getPoints(){
        return points;
    }
    public List<Point> getHitPoints(){
        return hitPoints;
    }

    public int getEdgeThreshold() {
        return edgeThreshold;
    }
}
