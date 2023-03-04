package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class FloydWarshall {
    private static ArrayList<Point> points;
    private int edgeThreshold;
    private static double[][] distance;
    private static int[][] access;

    // Constructor to initialize the graph
    public FloydWarshall(ArrayList<Point> points, int edgeThreshold) {
        FloydWarshall.points = points;
        this.edgeThreshold = edgeThreshold;

        calculateShortestPath();
    }

    // Method to calculate the shortest path between two points using Floyd-Warshall algorithm
    private void calculateShortestPath() {
        int n = points.size();
        double[][] distance = new double[n][n];
        int[][] access = new int[n][n];

        // Initialize distance matrix and access matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distance[i][j] = 0.0;
                    access[i][j] = -1;
                } else {
                    double d = points.get(i).distance(points.get(j));
                    if (d <= edgeThreshold) {
                        distance[i][j] = d;
                        access[i][j] = j;
                    } else {
                        distance[i][j] = Double.POSITIVE_INFINITY;
                        access[i][j] = -1;
                    }
                }
            }
        }

        // Apply Floyd Warshall algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distance[i][j] > distance[i][k] + distance[k][j]) {
                        distance[i][j] = distance[i][k] + distance[k][j];
                        access[i][j] = access[i][k];
                    }
                }
            }
        }

        FloydWarshall.distance = distance;
        FloydWarshall.access = access;
    }

    public static double getShortestDist(Point u, Point v)
    {
        int uIndex = points.indexOf(u);
        int vIndex = points.indexOf(v);

        return distance[uIndex][vIndex];
    }

    public static ArrayList<Edge> getShortestPath(Point u, Point v) {

        int uIndex = points.indexOf(u);
        int vIndex = points.indexOf(v);

        ArrayList<Edge> path = new ArrayList<>();
        if (access[uIndex][vIndex] == -1) {
            return path;
        }
        while (uIndex != vIndex) {
            int kIndex = access[uIndex][vIndex];
            path.add(new Edge(points.get(uIndex), points.get(kIndex)));
            uIndex = kIndex;
        }
        path.add(new Edge(points.get(uIndex), points.get(vIndex)));
        return path;
    }
}
