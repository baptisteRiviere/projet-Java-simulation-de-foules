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
	// Attribue un chemin à chaque individu de la foule
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
	// Redéfinition de la méthode toString
	@Override
	public String toString() {
		String txt = "\nfoule composée de : \n";
		for (Individu individu : repartition) {
			txt += individu.getNom() + ", " + individu.getPosition();
			txt += "\n";
		}
		return txt;
    }
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Renvoie un booléen true si tous les individus sont bloqués ou sortis, false sinon
	public boolean individusSortis() {
		boolean tousSortisOuBloques = true;
		// tant que tous les individus ne sont pas sortis ou bloqués
		// Puis on parcourt la foule pour déterminer s'il reste des individus à "bouger"
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

