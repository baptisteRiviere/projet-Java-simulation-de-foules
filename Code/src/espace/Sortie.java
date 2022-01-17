package espace;

import coordonnees.Coordonnees;

public class Sortie extends Element {
	private boolean ouvert;
	
	//////////////////////////////////////////////////////////////////////////////////////
	// constructeur 1
	public Sortie(Coordonnees position) {
		super(position);
		this.ouvert = true;
	}
	
	// constructeur 2
	public Sortie(Coordonnees position,boolean ouvert) {
		super(position);
		this.ouvert = ouvert;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// getters
	public boolean getOuvert() {return this.ouvert;}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// setter 
	
	// méthodes permettant de changer l'état de la porte
	public void ouvrirSortie() {this.ouvert = true;}
	public void fermerSortie() {this.ouvert = false;}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// redéfinition de la méthode toString
    @Override
	public String toString() {
        String txt = "sortie ";
		if (this.ouvert) {txt += "ouverte";} else {txt += "fermée";}
        return txt + this.position.toString();
    }
}