package algorithms;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DivideAndConquer {

    private Graph graph;
    private Path path;
    private List<Edge> subTrees;


    public DivideAndConquer(Graph g ){
        graph = g;
        path = new Path(new ArrayList<>(g.getPoints()),g.getEdgeThreshold());
        List<Point> hp = g.getHitPoints();
        List<Edge> value =  new ArrayList<Edge>(hp.size());

        for(Point k : hp){
            Double dmax = Double.POSITIVE_INFINITY;
            Point elu = new Point(-1,-1);
            for(Point l : hp){
                if(k.equals(l)){
                    continue;
                }
                else{
                    if(Path.getShortestDist(k,l)<dmax){
                        dmax = Path.getShortestDist(k,l);
                        elu = l;

                    }

                }

            }

            Edge nedge = new Edge(k,elu,dmax);
            value.add(nedge);
        }
        subTrees = value;
    }

    public List<Edge> getSubTrees() {
        return subTrees;
    }

    protected Tree2D getTree()
    {
        HashMap<Point, Tree2D> pointToTree = new HashMap<>();
        for (Edge e : this.subTrees) {
            Point u = e.getStart();
            Point v = e.getEnd();

            if (!pointToTree.containsKey(u)) {
                pointToTree.put(u, new Tree2D(u, new ArrayList<>()));
            }

            if (!pointToTree.containsKey(v)) {
                pointToTree.put(v, new Tree2D(v, new ArrayList<>()));
            }

            pointToTree.get(u).getSubTrees().add(pointToTree.get(v));
            pointToTree.get(v).getSubTrees().add(pointToTree.get(u));
        }
//
//        for (Point name: pointToTree.keySet()) {
//            String key = name.toString();
//            String value = pointToTree.get(name).getSubTrees().toString();
//            System.out.println(key + " " + value);
//        }

        // Build the tree recursively
        Tree2D steinerTree = buildSubtree(pointToTree.get(this.subTrees.get(0).getStart()), null);

        return null;
    }

    private static Tree2D buildSubtree(Tree2D currentNode, Tree2D parentNode) {
        // Remove the parent node from the current node's subtrees
        if (parentNode != null) {
            currentNode.getSubTrees().remove(parentNode);
        }

        // Recursively build subtrees
        ArrayList<Tree2D> subtrees = new ArrayList<>();
        for (Tree2D child : currentNode.getSubTrees()) {
            subtrees.add(buildSubtree(child, currentNode));
        }

        return new Tree2D(currentNode.getRoot(), subtrees);
    }

}
