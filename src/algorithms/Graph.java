package algorithms;

import java.awt.Point;
import java.util.*;

public class Graph {

    private Set<Point> vertex;
    private Set<Edges> edges;

    public Graph(){
        vertex = new HashSet<Point>();
        edges = new HashSet<Edges>();
    }
    public Graph(Set<Point> points, Set<Edges> edges){
        vertex.addAll(points);
        this.edges.addAll(edges);
    }
    //TODO ajouter une methode qui renvoie toutes les arettes qui commencent par un point donné
    
    public Chemain dijkstra(Point start, Point end){


        HashMap<Point,Double> dist = new HashMap<>();
        HashMap<Point,Point> ancestor = new HashMap<>();
        PriorityQueue<Point> toVisit = new PriorityQueue<>();
        for(Point p: this.vertex ){
            dist.put(p,Double.POSITIVE_INFINITY);
            ancestor.put(p,null);
        }
        dist.put(start,0.);
        toVisit.add(start);
        while(!toVisit.isEmpty()){
            Point current = toVisit.poll();
            if(current.equals(end)){
                break;
            }
           //TODO pour chaque arrete qui contient le point courrant en position start
            //        for (Edge edge : graph.getEdges(current)) {
            //                Node neighbor = edge.getDestination();
            //                int tentativeDistance = distances.get(current) + edge.getWeight();
            //                if (tentativeDistance < distances.get(neighbor)) {
            //                    distances.put(neighbor, tentativeDistance);
            //                    predecessors.put(neighbor, current);
            //                    unvisitedNodes.remove(neighbor);
            //                    unvisitedNodes.add(neighbor);
            //                }
            //            }
            //        }
            // Construction du chemin
        /*    List<Node> path = new ArrayList<>();
            Node current = destination;
            while (current != null) {
                path.add(0, current);
                current = predecessors.get(current);
            }
            if (path.size() == 1 && !path.contains(source)) {
                return null; // pas de chemin trouvé
            }
            return path;
        }*/

    }

return null;
    }



}
