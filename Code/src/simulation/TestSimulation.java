package simulation;

import coordonnees.Coordonnees;
import espace.Espace;
import espace.MoteurEspace;
import espace.Obstacle;
import espace.Sortie;
import foule.Foule;
import foule.MoteurFoule;
import io.AffichageSimulation;
import io.AffichageStart;


public class TestSimulation {
	
	
	public static void testSimulationSansCollision() {
		
		// On crée l'espace
		Espace espace =  MoteurEspace.moteurEspace();
		
		// On y ajoute la foule
		MoteurFoule mf = new MoteurFoule(espace);
		Foule foule = mf.moteurFoule(50,espace,true);
		
		// On réalise la simulation
		Simulation sim = new Simulation(foule, espace);
		
		/*
		for (int i = 0; i < 10; i++) {
			sim.etapeSsCollis();
			System.out.println(sim);
		}	
		*/
		
		AffichageSimulation frame = new AffichageSimulation(sim,false);
		frame.setVisible(true);
	}
	
	
	
	public static void testSimulationAvecCollision() {
		
		// On crée l'espace
		Espace espace =  MoteurEspace.moteurEspace();
		
		// On y ajoute la foule
		MoteurFoule mf = new MoteurFoule(espace);
		Foule foule = mf.moteurFoule(50,espace,true);
		
		// On réalise la simulation
		Simulation sim = new Simulation(foule, espace);
		
		/*
		for (int i = 0; i < 10; i++) {
			sim.etapeAvecCollis();
			System.out.println(sim);
		}	
		*/
		
		AffichageSimulation frame = new AffichageSimulation(sim,true);
		frame.setVisible(true);
	}
	
	public static void testSimulationSimple() {
		// On crée une espace de 7 par 6 entouré de murs
		Espace espace =  MoteurEspace.moteurPieceRectangulaire(9,8);
		espace.removeElement(new Obstacle(new Coordonnees(4,7)));
		espace.addSortie(new Sortie(new Coordonnees(4,7)));
				
		// On ajoute la foule dans l'espace
		MoteurFoule mf = new MoteurFoule(espace);
		Foule foule = mf.moteurFoule(10,espace,true);
			
		// On réalise la simulation
		Simulation sim = new Simulation(foule, espace);
		/*
		for (int i = 0; i < 20; i++) {
			sim.etapeAvecCollis();
			System.out.println(sim);
		}
		*/
		
		AffichageSimulation frame = new AffichageSimulation(sim,true);
		frame.setVisible(true);
	}
	
	public static void testSimulationCasParticuliers() {
		
		// On crée un espace de 7 par 6 entouré de murs
		Espace espace =  MoteurEspace.moteurPieceRectangulaire(9,8);
		espace.removeElement(new Obstacle(new Coordonnees(4,7)));
		espace.addSortie(new Sortie(new Coordonnees(4,7)));
		// On rajoute un mur qui coupe la pièce en 2 pour vérifier qu'un indiv bloqué sans sortie est géré par l'algo
		for (Obstacle o : MoteurEspace.createMur(new Coordonnees(1,3),new Coordonnees(7,3))) {espace.addObstacle(o);}
				
		// On ajoute la foule dans l'espace, on met 50 personnes pour vérifier que l'algorithme peut gérer le fait de 
		// demander plus d'individus que de place libre
		MoteurFoule mf = new MoteurFoule(espace);
		Foule foule = mf.moteurFoule(50,espace,true);
			
		// On réalise la simulation
		Simulation sim = new Simulation(foule, espace);
		/*
		for (int i = 0; i < 20; i++) {
			sim.etapeAvecCollis();
			System.out.println(sim);
		}
		*/
		AffichageSimulation frame = new AffichageSimulation(sim,true);
		frame.setVisible(true);
	}
	
	public static void testFinDeSimulation() {
		// On crée l'espace
		Espace espace =  MoteurEspace.moteurEspace();
		//espace.addObstacle(new Obstacle(new Coordonnees(26,5),"plante"));
		// On y ajoute la foule
		MoteurFoule mf = new MoteurFoule(espace);
		Foule foule = mf.moteurFoule(50,espace,true);
		// On crée la simulation et on la conduit jusqu'au bout
		Simulation sim = new Simulation(foule, espace);
		System.out.println("Au début de la simulation");
		System.out.println(sim);
			
		sim.finDeSimulation(true);
		System.out.println("A la fin de la simulation");
		System.out.println(sim);
	}	
}
