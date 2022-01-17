package graphe;

import java.util.*;

public class DijkstraAlgorithm {
	
    // listes des noeuds et des arcs
    private final List<Vertex> nodes;
    private final List<Edge> edges;
    
    // listes des noeuds r�gl�s et non r�gl�s
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;

    // Map associant � un noeud son pr�d�cesseur, c'est � dire le noeud par o� il faut passer lors de l'�tape pr�c�dente pour obtenir le plus court chemin
    private Map<Vertex, Vertex> predecessors;

    // Map associant � un noeud la distance minimale � partir du point de d�part
    private Map<Vertex, Double> distance;

	//////////////////////////////////////////////////////////////////////////////////////
    // Constructeur
    public DijkstraAlgorithm(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Programme principal effectuant l'algorithme : 
    // source = Vertex de d�part  
    public void execute(Vertex source) {
        // initialisation des listes
        settledNodes = new HashSet<Vertex>();
        unSettledNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Double>();
        predecessors = new HashMap<Vertex, Vertex>();

        // on rajoute au dictionnaire distance la source et sa distance � la source (i.e. z�ro)
        distance.put(source, 0.0);

        // On rajoute la source aux noeuds non r�gl�s
        unSettledNodes.add(source);

        // Tant que la liste des noeuds non r�gl�s n'est pas vide :
        while (unSettledNodes.size() > 0) {
            // On cherche le noeud non r�gl� le plus proche de la source
            Vertex node = getMinimum(unSettledNodes);
            // On le supprime des noeuds non r�gl�s pour l'ajouter aux noeuds r�gl�s
            settledNodes.add(node);
            unSettledNodes.remove(node);
            // On met � jour les listes
            findMinimalDistances(node);
        }
    }

	//////////////////////////////////////////////////////////////////////////////////////
    // Explore les noeuds voisins pour mettre � jour les diff�rentes listes
    private void findMinimalDistances(Vertex node) {
    	// On r�cup�re la liste des voisins de ce noeud
        List<Vertex> adjacentNodes = getNeighbors(node);
        // On la parcourt
        for (Vertex target : adjacentNodes) {
        	// Si la distance minimale pour arriver au noeud voisin (par un autre chemin ou non explor� : i.e. inf)
            // est sup�rieure � la distance minimale pour arriver au noeud "node" en entr�e 
            // + la distance entre les deux noeuds (voisin et node)
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                // alors on change/ajoute cette dist dans le dictionnaire distance
                distance.put(target,(getShortestDistance(node)
                        + getDistance(node, target)));
                // Et on change/ajoute le noeud associ� au pr�d�cesseur du noeud voisin
                predecessors.put(target, node);
                // Target (le voisin) est donc ajout� aux noeuds non r�gl�s
                unSettledNodes.add(target);
            }
        }

    }

    // Renvoie la distance entre deux noeuds (distance euclidienne)
    private double getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }


    // Renvoie la liste des voisins du noeud en entr�e
    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    // Renvoie le noeud de la liste en entr�e dont la distance � la source est minimale
    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    // renvoie true si le noeud entr� est d�j� explor�
    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    // Renvoie la distance minimale entre le noeud en entr�e (destination) � la source
    private Double getShortestDistance(Vertex destination) {
        // On r�cup�re la distance dans le dictionnaire
        Double d = distance.get(destination);
        // Si d est null, le noeud n'a pas �t� r�gl�
        if (d == null) {
            // On retourne donc une valeur infinie
            return Double.MAX_VALUE;
        } else {
            // Sinon on renvoie d
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        // on initialise le chemin
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        // Si un chemin existe, on le reconstitue en partant de la fin
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        // On renvoie la liste de noeuds dans l'ordre
        return path;
    }



    // Renvoie le chemin le plus court entre la source et l'un des noeuds de la liste targets
    public LinkedList<Vertex> getShortestPath(Vertex source,List<Vertex> targets) {
    	// On initialise le plus court chemin et sa longueur (pour l'instant infinie)
    	LinkedList<Vertex> shortestPath = new LinkedList<Vertex>();
        Double min = Double.MAX_VALUE;
    	   	
    	// On ex�cute l'algorithme
        execute(source);
        
        // On parcourt tous les noeuds cible
        for (Vertex v : targets) {
        	// On retrouve le plus court chemin
        	LinkedList<Vertex> path = getPath(v);
        	// Et on calcule sa longueur
        	double dist = 0;
        	for (Integer i = 0; i < path.size()-1;i++) {
        		Edge e = new Edge(i.toString(),path.get(i),path.get(i+1));
        		dist += e.getWeight();
        	}
        	// Pour la comparer au plus court chemin actuel
        	if (dist < min) {
        		min = dist;
        		shortestPath = path;
        	}
        }
        // On peut retourner le chemin le plus court
        return shortestPath;
    }

    
    
    
    // Renvoie le chemin le plus court entre la source et l'un des noeuds tel que target = true
    public LinkedList<Vertex> getShortestPath(Vertex source) {
    	// On initialise le plus court chemin et sa longueur (pour l'instant infinie)
    	LinkedList<Vertex> shortestPath = new LinkedList<Vertex>();
        Double min = Double.MAX_VALUE;
    	   	
    	// On ex�cute l'algorithme
        execute(source);
        
        // On parcourt tous les noeuds 
        for (Vertex v : this.nodes) {
        	// S'il s'agit d'une cible
        	if (v.getTarget()) {
        		// On retrouve le plus court chemin
        		LinkedList<Vertex> path = getPath(v);
        		// On v�rifie qu'il n'est pas nul (si le noeud n'a pas acc�s � une sortie)
        		if (path != null) {
        			// On calcule sa longueur
            		double dist = 0;
            		for (Integer i = 0; i < path.size()-1;i++) {
            			Edge e = new Edge(i.toString(),path.get(i),path.get(i+1));
            			dist += e.getWeight();
            		}
            		// Pour la comparer au plus court chemin actuel
            		if (dist < min) {
            			min = dist;
            			shortestPath = path;
            		}
        		} else { // Si le path est nul, on ne renvoie que le point de d�part
        			shortestPath.add(source);
        		}
        	}
        }
        // On peut retourner le chemin le plus court
        return shortestPath;
    }

}