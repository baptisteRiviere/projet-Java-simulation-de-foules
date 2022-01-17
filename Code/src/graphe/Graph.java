package graphe;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

	//////////////////////////////////////////////////////////////////////////////////////
    // Constructeur 1
    public Graph() {
        this.vertexes = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
    }
    
    // Constructeur 2
    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Getters
    public List<Vertex> getVertexes() {return vertexes;}
    public List<Edge> getEdges() {return edges;}

	//////////////////////////////////////////////////////////////////////////////////////
    // Méthodes permettant d'ajouter un noeud au graphe
    public void addVertex(Vertex v) {vertexes.add(v);}
    public void addEdge(Edge e) {edges.add(e);}

	//////////////////////////////////////////////////////////////////////////////////////
    // Redéfinition de la méthode toString
    @Override
    public String toString() {
    	String txt = "Graphe constitué de : \n";
    	txt += "\nnoeuds :\n";
    	for (Vertex v : this.vertexes) {
    		txt += v.toString() + "\n";
    	}
    	txt += "\narcs\n";
    	for (Edge e : this.edges) {
    		txt += e.toString() + "\n";
    	}
    	return txt;
    }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Méthode renvoyant une copie du graphe
    public Graph copyGraph() {
    	List<Vertex> newVertexes = new ArrayList<Vertex>(this.vertexes);
    	List<Edge> newEdges = new ArrayList<Edge>(this.edges);
    	Graph newGraph = new Graph(newVertexes,newEdges);
    	return newGraph;
    }
}
