package algorithms;

import java.awt.Point;

public class Edge {

    private Point start;
    private Point end;
    private double weigth;

    //constructor to use when you need to force a weight value
    public Edge(Point u, Point v, double weight) {
        //par exemple dans la classe Steiner
        this.start = u;
        this.end = v;
        this.weigth = weight;
    }
    //Default constructor
    public Edge(Point u, Point v) {
        this.start = u;
        this.end = v;
        this.weigth = FloydWarshall.getShortestDist(u, v);
    }

    public Point getStart() {
        return this.start;
    }

    public Point getEnd() {
        return this.end;
    }

    public double getWeight() {
        return this.weigth;
    }
}
