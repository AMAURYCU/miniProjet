package algorithms;

import java.awt.Point;

public class Edge {

    private Point start;
    private Point end;
    private double weigth;

    public Edge(Point u, Point v, double weight) {
        this.start = u;
        this.end = v;
        this.weigth = weight;
    }

    public Point getStart()
    {
        return this.start;
    }

    public Point getEnd()
    {
        return this.end;
    }

    public double getWeight()
    {
        return this.weigth;
    }
}
