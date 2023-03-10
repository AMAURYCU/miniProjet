package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class DefaultTeam {
  public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
    Graph g = new Graph(points, hitPoints, edgeThreshold);
    DivideAndConquer d = new DivideAndConquer(g);
    HashMap<Edge, Double> result = g.constructGraph();
    Kruskal init = new Kruskal(result, hitPoints);
    Tree2D steinerTree = init.computeMST();

    Tree2D tTree = d.getTree();

    return steinerTree;
    /*Graph g = new Graph(points,hitPoints,edgeThreshold);
    DivideAndConquer d = new DivideAndConquer(g);
    Tree2D test = new Tree2D(points.get(0),new ArrayList<>(d.getSubTrees()));
    return test;*/
  }
  public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
    //REMOVE >>>>>
    Tree2D leafX = new Tree2D(new Point(700,400),new ArrayList<Tree2D>());
    Tree2D leafY = new Tree2D(new Point(700,500),new ArrayList<Tree2D>());
    Tree2D leafZ = new Tree2D(new Point(800,450),new ArrayList<Tree2D>());
    ArrayList<Tree2D> subTrees = new ArrayList<Tree2D>();
    subTrees.add(leafX);
    subTrees.add(leafY);
    subTrees.add(leafZ);
    Tree2D steinerTree = new Tree2D(new Point(750,450),subTrees);
    //<<<<< REMOVE

    return steinerTree;
  }
}
