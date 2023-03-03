package algorithms;

import com.sun.source.tree.Tree;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DivideAndConquer {
    /*Heuristique basée sur une methode diviser pour reigner
    Nous avons tout d'abbord besoin d'effectuer un préprocessing sur les données: Nous prenons un point A puis nous calculons
    le chemain vers le point le plus proche (Dijkstra) de A . Cela nous permets de créer N/2 sous arbres de Steiners
    ____
    Nous appliquons après un algorithme de fusion d'arbre pour fusioner les sous arbres obtenus après le préprocessing.
    Ceci nous créera l'arbre de Steiner
     */
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
}
