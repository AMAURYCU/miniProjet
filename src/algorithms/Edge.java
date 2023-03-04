package algorithms;

import java.awt.Point;

public class Edge {
    /*
    Cette classe sert a relier 2 point avec leur distance
     */


    private Point start;
    private Point end;
    private double weigth;

    //constructeur utiliser lorsque l'on as besoin de forcer une valeurs de poids
    public Edge(Point u, Point v, double weight) {
        //par exemple dans la classe Steiner
        this.start = u;
        this.end = v;
        this.weigth = weight;
    }
    //Constructeur par défaut
    public Edge(Point u, Point v) {
        this.start = u;
        this.end = v;
        weigth = FloydWarshall.getShortestDist(u, v);
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
