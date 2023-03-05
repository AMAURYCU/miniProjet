package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class DefaultTeam {
  public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
    new Steiner(points, edgeThreshold, hitPoints);
    return Steiner.withoutBudget();
  }
  public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
    new Steiner(points, edgeThreshold, hitPoints);
    return Steiner.budget(1664);
  }
}
