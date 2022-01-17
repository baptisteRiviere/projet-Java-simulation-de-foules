package graphe;

public class Edge  {
    private final String id;
    private final Vertex source;
    private final Vertex destination;
    private final double weight;

	//////////////////////////////////////////////////////////////////////////////////////
    // Constructeur 1
    public Edge(String id, Vertex source, Vertex destination, double weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
    // Constructeur 2
    public Edge(String id, Vertex source, Vertex destination) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        int x1 = source.getPosition().x;
        int y1 = source.getPosition().y;
        int x2 = destination.getPosition().x;
        int y2 = destination.getPosition().y;
        this.weight = Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
    }

	//////////////////////////////////////////////////////////////////////////////////////
    // Getters
    
    public String getId() { return id; }
    public Vertex getDestination() { return destination; }
    public Vertex getSource() { return source; }
    public double getWeight() { return weight; }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Redéfinition de la méthode toString
    
    public String toString() {
    	Double weight = (Double) this.weight;
    	return "id :" + this.id + ", de " + this.source + " à " + this.destination
    			+ ", poids = " + weight.toString();
    }
}
