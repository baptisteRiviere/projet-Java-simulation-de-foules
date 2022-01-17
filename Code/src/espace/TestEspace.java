package espace;

import java.util.List;

import coordonnees.Coordonnees;
import graphe.Vertex;
import io.AffichageSimulation;


public class TestEspace {
	
	public static void testEspace() {
		// On récupère l'espace fourni par le moteur de la classe MoteurEspace
		Espace espace = MoteurEspace.moteurEspace();
		System.out.println(espace);
		
		AffichageSimulation frame = new AffichageSimulation(espace);
		frame.setVisible(true);
	}

    public static void testFindPositionsObstaclesVoisins() {
    	Espace espace = MoteurEspace.moteurEspace();
    	Coordonnees coord = new Coordonnees(0,1);
    	Obstacle o = new Obstacle(coord,"mur");
    	espace.addObstacle(o);
    	
        List<Coordonnees> posVoisins = espace.findPositionsObstaclesVoisins(o);
        
        AffichageSimulation frame = new AffichageSimulation(espace);
		frame.setVisible(true);
        
        System.out.println("voisins de " + o.toString());
        for (Coordonnees c : posVoisins) {
            System.out.println(c);
        }
    }   
 
    public static void testPointsInteretObstacle() {
    	Espace espace = MoteurEspace.moteurEspace();
    	Coordonnees coord = new Coordonnees(9,3);
    	Obstacle o = new Obstacle(coord,"mur");
    	espace.addObstacle(o);
    	
    	List<Coordonnees> pointInt = espace.pointsInteretObstacle(o);
    	
    	AffichageSimulation frame = new AffichageSimulation(espace);
		frame.setVisible(true);
    	
    	System.out.println("points d'intéret autour de " + o.toString());
    	for (Coordonnees c : pointInt) {
            System.out.println(c);
        }
    }
    
    public static void testFindPointsInteret() {
    	Espace espace = MoteurEspace.moteurEspace();
    	System.out.println("points d'intéret du graphe");
    	List<Coordonnees> pointIntEsp = espace.findPointsInteret();
    	
    	AffichageSimulation frame = new AffichageSimulation(espace);
		frame.setVisible(true);
    	
    	for (Coordonnees c : pointIntEsp) {
            System.out.println(c);
    	}
    }  
    
    public static void testConstruireGrapheAssocie() {
    	Espace espace = MoteurEspace.moteurEspace();
    	System.out.println("Graphe associé à cet espace");
    	System.out.println(espace.getGraphe());
    	
    	AffichageSimulation frame = new AffichageSimulation(espace);
		frame.setVisible(true);
    	
    	System.out.println("\n");
    	Vertex v0 = espace.getGraphe().getVertexes().get(0);
    	Vertex v1 = espace.getGraphe().getVertexes().get(1);
    	System.out.println(espace.sontRelies(v0,v1));
    }
    	
}
