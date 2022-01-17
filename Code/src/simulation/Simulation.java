package simulation;

import foule.Foule;
import foule.Individu;
import io.AffichageErreur;

import java.util.ArrayList;
import java.util.List;

import coordonnees.Coordonnees;
import espace.Espace;
import espace.Sortie;

public class Simulation {
  
	public Foule foule;
	public Espace espace;
	public int temps;
	
	//////////////////////////////////////////////////////////////////////////////////////
	//constructeur
	public Simulation(Foule foule, Espace espace) {
		this.foule = foule;
		this.espace = espace;
		this.temps = 0;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Méthodes liées aux sorties
	
	// Méthode pour ouvrir une sortie proprement
	public void ouvrirSortie(Sortie s) {
		this.espace.ouvrirSortie(s);
		// On doit alors réaffecter un chemin à chaque individu
		this.foule.attribuerChemins(this.espace);
	}
	
	// Méthode pour fermer une sortie proprement
	public void fermerSortie(Sortie s) {
		this.espace.fermerSortie(s);
		// On doit alors réaffecter un chemin à chaque individu
		this.foule.attribuerChemins(this.espace);
	}
	
	// Methode pour ouvrir toutes les portes de sortie proprement
	public void ouvrirToutesSorties() {
		this.espace.ouvrirToutesSorties();
		// On doit alors réaffecter un chemin à chaque individu
		this.foule.attribuerChemins(this.espace);
	}
		
	// Methode pour fermer toutes les portes de sortie proprement
	public void fermerToutesSorties() {
		this.espace.fermerToutesSorties();
		// On doit alors réaffecter un chemin à chaque individu
		this.foule.attribuerChemins(this.espace);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Méthodes gérant la simulation
	
	// fait tourner le temps +1 et fait bouger tous les individus dans l'espace sans faire de collision
	public void etapeSsCollis() {
		// On avance le temps
		this.temps += 1;				
		// On parcourt la liste des individus
		for (Individu ind : this.foule.getRepartition()) {
			// On vérifie qu'il est encore à l'intérieur de la pièce 
			if (ind.getInterieur()) {
				// S'il n'est pas bloqué, l'individu avance sur son chemin
				ind.deplacementSsCollis(this.foule, this.espace);
				// Si l'individu est sur une sortie, il n'est plus à l'intérieur de la pièce. On l'enregistre.
				if (this.espace.getPosSorties().contains(ind.getPosition())) {
					ind.setInterieur(false);
				}
			}
		}
	}
	
	
	// fait tourner le temps +1 et fait bouger tous les individus dans l'espace en prenant en compte les collisions
	public void etapeAvecCollis() {
		// On avance le temps
		this.temps += 1;
		
		// On parcourt la foule pour "bouger" tous les individus 
		for (Individu ind : this.foule.getRepartition()) {
			// On vérifie que l'individu est encore à l'intérieur de la pièce et on le déplace
			if (ind.getInterieur()) { 
				ind.deplacement();
				// On veut ne pas prendre en compte tous les individus sortis
				// Si l'individu est encore CONSIDERE à l'intérieur de la pièce 
				// ET que l'individu est sur une sortie
				if (ind.getInterieur() && (this.espace.getPosSorties().contains(ind.getPosition()))) {
					// Alors on change son statut et on l'enregistre
					ind.setInterieur(false);
				}
			}
		}
		
		// On s'occupe alors des collisions et donc des litiges, et on les compte
		int nbreLitiges = 0;
		boolean litige = true;
		do {litige = this.testLitige();nbreLitiges++;} // Tant qu'on a un litige (plus d'une personne sur la meme position) on le règle
		while(litige);
		// On estime que chaque litige prend plus de temps, on va estimer 1/4 de seconde par litige
		// On le rajoute donc au temps
		temps += (int) Math.ceil((double) nbreLitiges/3);
	}
	
	
	
	// réalise une étape, de différente manière selon le booléen collis
		public void etape(boolean collis) {
			if (collis) {
				this.etapeAvecCollis();
			}
			else {this.etapeSsCollis();}
		}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Test pour déterminer s'il y a des litiges (plusieurs personnes sur la même case)
	// Et pour les régler
	public boolean testLitige(){
		// On initialise le booléen litige sur false
		boolean litige = false;
		
		// On parcourt la liste des individus 
	    for (Individu ind : this.foule.getRepartition()) {  
	    	
	    	// On vérifie que l'individu se trouve encore à l'intérieur de la salle et qu'il n'est pas à terre,
	    	// sinon on ne le considère pas
	    	if((ind.getInterieur()) && (!(ind.getATerre()))) {
	    		
	    		// Initialisation des listes d'individus en collision et de ceux qui sont à terre
	    		ArrayList<Individu> individusEnCollision = new ArrayList<Individu>();
	    		ArrayList<Individu> individusATerre = new ArrayList<Individu>();
	    		
	    		// On parcourt la liste des individus pour déterminer lesquels sont en collision avec l'individu considéré
	    		for (Individu indAComparer : this.foule.getRepartition()) {
	    			if (ind.getPosition().equals(indAComparer.getPosition())) { 
	    				// L'individu à comparer est sur la même place que l'individu étudié
	    				// On l'ajoute à la liste "collision" et on vérifie s'il est à terre ou non
	    				individusEnCollision.add(indAComparer); 
	    				if (indAComparer.getATerre() == true) {
	    					individusATerre.add(indAComparer);
	    				}
	    			}
	    		}
	    		
	    		// S'il y a plus d'un individu sur la position, on a un litige et on va le régler
	    		if(individusEnCollision.size()>1) { 
	    			litige = true;
	    			
	    			// On initialise le booléen place à prendre, pour l'instant la place est bien vide
	    			boolean placeAPrendre = true;
	    			
	    			// Si on a une personne à terre sur la case, alors la place est prise
	    			if (individusATerre.size() > 0) { placeAPrendre = false; }
	    			
	    			// On parcourt la liste des autres individus à déplacer
	    			for (Individu indADeplacer : individusEnCollision) {
	    				// Si personne la place est à prendre OU que la personne est à terre, 
	    				// l'individu indADeplacer peut prendre la place (on ne le bouge pas)
	    				if (placeAPrendre || (indADeplacer.getATerre())) {
	    					// La place est maintenant prise
	    					placeAPrendre = false;
	    				} else {
	    					// Sinon, l'individu retourne en arrière et tombe à terre
	    					indADeplacer.deplacementinverse();
	    					indADeplacer.setATerre(true);
	    				}
	    			}	    				
	    		}
	    	}
	    }
	    return litige;
	}

	
	//////////////////////////////////////////////////////////////////////////////////////
	// Méthode réalisant toutes les étapes de la simulation jusqu'à ce que
	// tous les individus soient sortis ou bloqués 
	public void finDeSimulation(boolean collis) {
		boolean tousSortisOuBloques = false;
		// tant que tous les individus ne sont pas sortis ou bloqués
		while (!(tousSortisOuBloques)) {
			// On effectue l'étape 
			this.etape(collis);
			// Puis on parcourt la foule pour déterminer s'il reste des individus à "bouger"
			tousSortisOuBloques = true;
			for (Individu ind : this.foule.getRepartition()) {
				// Si la position de l'individu étudié n'est pas la dernière étape de son chemin,
				// Alors il reste au moins une étape
				List<Coordonnees> chemin = ind.getChemin();
				if (!(ind.getPosition() == chemin.get(chemin.size()-1))){
					tousSortisOuBloques = false;
					break;
				}
			}
			if (this.temps>=10000) {
				tousSortisOuBloques = true;
				AffichageErreur affErreur = new AffichageErreur(true);
				affErreur.setVisible(true);
				}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	//redefinition de la methode toString
	@Override
	public String toString() {
		String mes = "\nLe temps : " + this.temps;
		mes += this.foule.toString();
		return mes;
	}
}