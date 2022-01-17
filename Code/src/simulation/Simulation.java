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
	// M�thodes li�es aux sorties
	
	// M�thode pour ouvrir une sortie proprement
	public void ouvrirSortie(Sortie s) {
		this.espace.ouvrirSortie(s);
		// On doit alors r�affecter un chemin � chaque individu
		this.foule.attribuerChemins(this.espace);
	}
	
	// M�thode pour fermer une sortie proprement
	public void fermerSortie(Sortie s) {
		this.espace.fermerSortie(s);
		// On doit alors r�affecter un chemin � chaque individu
		this.foule.attribuerChemins(this.espace);
	}
	
	// Methode pour ouvrir toutes les portes de sortie proprement
	public void ouvrirToutesSorties() {
		this.espace.ouvrirToutesSorties();
		// On doit alors r�affecter un chemin � chaque individu
		this.foule.attribuerChemins(this.espace);
	}
		
	// Methode pour fermer toutes les portes de sortie proprement
	public void fermerToutesSorties() {
		this.espace.fermerToutesSorties();
		// On doit alors r�affecter un chemin � chaque individu
		this.foule.attribuerChemins(this.espace);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	// M�thodes g�rant la simulation
	
	// fait tourner le temps +1 et fait bouger tous les individus dans l'espace sans faire de collision
	public void etapeSsCollis() {
		// On avance le temps
		this.temps += 1;				
		// On parcourt la liste des individus
		for (Individu ind : this.foule.getRepartition()) {
			// On v�rifie qu'il est encore � l'int�rieur de la pi�ce 
			if (ind.getInterieur()) {
				// S'il n'est pas bloqu�, l'individu avance sur son chemin
				ind.deplacementSsCollis(this.foule, this.espace);
				// Si l'individu est sur une sortie, il n'est plus � l'int�rieur de la pi�ce. On l'enregistre.
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
			// On v�rifie que l'individu est encore � l'int�rieur de la pi�ce et on le d�place
			if (ind.getInterieur()) { 
				ind.deplacement();
				// On veut ne pas prendre en compte tous les individus sortis
				// Si l'individu est encore CONSIDERE � l'int�rieur de la pi�ce 
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
		do {litige = this.testLitige();nbreLitiges++;} // Tant qu'on a un litige (plus d'une personne sur la meme position) on le r�gle
		while(litige);
		// On estime que chaque litige prend plus de temps, on va estimer 1/4 de seconde par litige
		// On le rajoute donc au temps
		temps += (int) Math.ceil((double) nbreLitiges/3);
	}
	
	
	
	// r�alise une �tape, de diff�rente mani�re selon le bool�en collis
		public void etape(boolean collis) {
			if (collis) {
				this.etapeAvecCollis();
			}
			else {this.etapeSsCollis();}
		}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Test pour d�terminer s'il y a des litiges (plusieurs personnes sur la m�me case)
	// Et pour les r�gler
	public boolean testLitige(){
		// On initialise le bool�en litige sur false
		boolean litige = false;
		
		// On parcourt la liste des individus 
	    for (Individu ind : this.foule.getRepartition()) {  
	    	
	    	// On v�rifie que l'individu se trouve encore � l'int�rieur de la salle et qu'il n'est pas � terre,
	    	// sinon on ne le consid�re pas
	    	if((ind.getInterieur()) && (!(ind.getATerre()))) {
	    		
	    		// Initialisation des listes d'individus en collision et de ceux qui sont � terre
	    		ArrayList<Individu> individusEnCollision = new ArrayList<Individu>();
	    		ArrayList<Individu> individusATerre = new ArrayList<Individu>();
	    		
	    		// On parcourt la liste des individus pour d�terminer lesquels sont en collision avec l'individu consid�r�
	    		for (Individu indAComparer : this.foule.getRepartition()) {
	    			if (ind.getPosition().equals(indAComparer.getPosition())) { 
	    				// L'individu � comparer est sur la m�me place que l'individu �tudi�
	    				// On l'ajoute � la liste "collision" et on v�rifie s'il est � terre ou non
	    				individusEnCollision.add(indAComparer); 
	    				if (indAComparer.getATerre() == true) {
	    					individusATerre.add(indAComparer);
	    				}
	    			}
	    		}
	    		
	    		// S'il y a plus d'un individu sur la position, on a un litige et on va le r�gler
	    		if(individusEnCollision.size()>1) { 
	    			litige = true;
	    			
	    			// On initialise le bool�en place � prendre, pour l'instant la place est bien vide
	    			boolean placeAPrendre = true;
	    			
	    			// Si on a une personne � terre sur la case, alors la place est prise
	    			if (individusATerre.size() > 0) { placeAPrendre = false; }
	    			
	    			// On parcourt la liste des autres individus � d�placer
	    			for (Individu indADeplacer : individusEnCollision) {
	    				// Si personne la place est � prendre OU que la personne est � terre, 
	    				// l'individu indADeplacer peut prendre la place (on ne le bouge pas)
	    				if (placeAPrendre || (indADeplacer.getATerre())) {
	    					// La place est maintenant prise
	    					placeAPrendre = false;
	    				} else {
	    					// Sinon, l'individu retourne en arri�re et tombe � terre
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
	// M�thode r�alisant toutes les �tapes de la simulation jusqu'� ce que
	// tous les individus soient sortis ou bloqu�s 
	public void finDeSimulation(boolean collis) {
		boolean tousSortisOuBloques = false;
		// tant que tous les individus ne sont pas sortis ou bloqu�s
		while (!(tousSortisOuBloques)) {
			// On effectue l'�tape 
			this.etape(collis);
			// Puis on parcourt la foule pour d�terminer s'il reste des individus � "bouger"
			tousSortisOuBloques = true;
			for (Individu ind : this.foule.getRepartition()) {
				// Si la position de l'individu �tudi� n'est pas la derni�re �tape de son chemin,
				// Alors il reste au moins une �tape
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