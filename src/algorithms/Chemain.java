package algorithms;



import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.valueOf;

public class Chemain {
    private List<Point> way;
    private Double distance;
    public Chemain(){
        way = new ArrayList<Point>();
        distance = valueOf(0);
    }
    public void addPoint(Point p,Double dist){
        way.add(p);
        distance += dist;
    }

    public Double getDistance() {
        return distance;
    }

    public List<Point> getWay() {
        return way;
    }

}
