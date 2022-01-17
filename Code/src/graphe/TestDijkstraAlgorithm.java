package graphe;

import java.util.*;

public class TestDijkstraAlgorithm {

    private List<Vertex> nodes;
    private List<Edge> edges;

    public void testExecute() {
    	// Initialisation des listes de noeuds et d'arcs
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        
        // on ajoute les noeuds à la liste
        for (int i = 0; i < 11; i++) {
            Vertex location = new Vertex("Node_" + i);
            nodes.add(location);
        }

        // On ajoute les arcs
        addBothLane("Edge_0", 0, 1, 85.0);
        addBothLane("Edge_1", 0, 2, 217.0);
        addBothLane("Edge_2", 0, 4, 173.0);
        addBothLane("Edge_3", 2, 6, 186.0);
        addBothLane("Edge_4", 2, 7, 103.0);
        addBothLane("Edge_5", 3, 7, 183.0);
        addBothLane("Edge_6", 5, 8, 250.0);
        addBothLane("Edge_7", 8, 9, 84.0);
        addBothLane("Edge_8", 7, 9, 167.0);
        addBothLane("Edge_9", 4, 9, 502.0);
        addBothLane("Edge_10", 9, 10, 40.0);
        addLane("Edge_11", 1, 10, 600.0);

        // On instancie le graphe et l'algorithme
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        
        // On exécute l'algo à partir du noeud 0
        dijkstra.execute(nodes.get(0));
        
        // On n'a plus qu'à récupérer le chemin jusqu'au noeud 10
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(5));

        // On affiche les noeuds du chemin
        for (Vertex vertex : path) {
            System.out.println(vertex);
        }  
    }
    
    public void testGetShortestPath() {
    	// Initialisation des listes de noeuds et d'arcs
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        // on ajoute les noeuds à la liste
        for (int i = 0; i < 11; i++) {
            Vertex location = new Vertex("Node_" + i);
            nodes.add(location);
        }
        // On ajoute les arcs
        addBothLane("Edge_0", 0, 1, 85.0);
        addBothLane("Edge_1", 0, 2, 217.0);
        addBothLane("Edge_2", 0, 4, 173.0);
        addBothLane("Edge_3", 2, 6, 186.0);
        addBothLane("Edge_4", 2, 7, 103.0);
        addBothLane("Edge_5", 3, 7, 183.0);
        addBothLane("Edge_6", 5, 8, 250.0);
        addBothLane("Edge_7", 8, 9, 84.0);
        addBothLane("Edge_8", 7, 9, 167.0);
        addBothLane("Edge_9", 4, 9, 502.0);
        addBothLane("Edge_10", 9, 10, 40.0);
        addBothLane("Edge_11", 1, 10, 600.0);

        // On instancie le graphe et l'algorithme
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        
        Vertex source = nodes.get(0);
        List<Vertex> targets = new ArrayList<Vertex>();
        targets.add(nodes.get(3));
        targets.add(nodes.get(5));
        
        LinkedList<Vertex> path2 = dijkstra.getShortestPath(source,targets);
        
        // On affiche les noeuds du chemin
        for (Vertex vertex : path2) {
            System.out.println(vertex);
        }  
    
    }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Méthode pour ajouter un arc orienté
    
    private void addLane(String laneId, int sourceLocNo, int destLocNo,
            double distanceEucl) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), distanceEucl );
        edges.add(lane);
    }
    
    // Méthode pour ajouter un arc non orienté
    private void addBothLane(String laneId, int sourceLocNo, int destLocNo,
            double distanceEucl) {
        Edge lane1 = new Edge(laneId + "into",nodes.get(sourceLocNo), nodes.get(destLocNo), distanceEucl );
        edges.add(lane1);
        Edge lane2 = new Edge(laneId + "back",nodes.get(destLocNo), nodes.get(sourceLocNo), distanceEucl );
        edges.add(lane2);
    }
}