package foule;

import java.util.ArrayList;
import java.util.List;
import coordonnees.Coordonnees;
import espace.Espace;

public class Foule {
	
	private List<Individu> repartition;
	
	// Constructeur
	public Foule(){
	  this.repartition = new ArrayList<Individu>();
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Getters
	
	public List<Individu> getRepartition() {return this.repartition;}
	public List<Coordonnees> getPositionIndividus() { // Renvoie la liste des coordonnees des individus
		ArrayList<Coordonnees> positionindiv = new ArrayList<Coordonnees>();
		for (Individu indv : this.repartition){
			if (indv.getInterieur()) {
				positionindiv.add(indv.getPosition());
			}
		}
		return positionindiv;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Attribue un chemin � chaque individu de la foule
	public void attribuerChemins(Espace espace) {
		for (Individu ind : this.getRepartition()) {ind.findChemin(espace);}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Methode qui ajoute un individu dans la repartition de la foule sans lui calculer son chemin 
	public void addIndiv(Individu ind){
		this.repartition.add(ind);
	}
	
	// Methode qui ajoute un individu dans la repartition de la foule
	// avec un chemin dans l'espace
	public void addIndiv(Individu ind,Espace espace){
		ind.findChemin(espace);
		this.repartition.add(ind);
	}
	//////////////////////////////////////////////////////////////////////////////////////
	// Red�finition de la m�thode toString
	@Override
	public String toString() {
		String txt = "\nfoule compos�e de : \n";
		for (Individu individu : repartition) {
			txt += individu.getNom() + ", " + individu.getPosition();
			txt += "\n";
		}
		return txt;
    }
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Renvoie un bool�en true si tous les individus sont bloqu�s ou sortis, false sinon
	public boolean individusSortis() {
		boolean tousSortisOuBloques = true;
		// tant que tous les individus ne sont pas sortis ou bloqu�s
		// Puis on parcourt la foule pour d�terminer s'il reste des individus � "bouger"
		for (Individu ind : this.getRepartition()) {
			
			// Regarde si l'individu est sorti ou pas
			if (ind.getInterieur()){
				tousSortisOuBloques = false;
				break;
				}
			}
		return tousSortisOuBloques;
	}
}

