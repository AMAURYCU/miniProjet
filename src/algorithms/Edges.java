package algorithms;

import java.awt.*;

public  class Edges{
    private Point start;
    private Point goal;
    private Double dist;
    public Double distance(){
        return Math.sqrt(Math.pow(this.goal.getX()-start.getX(),2)+Math.pow(goal.getY()-start.getY(),2));
    }
    public Edges(Point p1,Point p2){
        start = p1;
        goal = p2;
        dist = distance();

    }

    public Double getDist() {
        return dist;
    }

    public Point getGoal() {
        return goal;
    }

    public Point getStart() {
        return start;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }

    public void setGoal(Point goal) {
        this.goal = goal;
    }

    public void setStart(Point start) {
        this.start = start;
    }



}