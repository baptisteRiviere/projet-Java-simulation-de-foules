package foule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import coordonnees.Coordonnees;
import espace.Espace;
import graphe.DijkstraAlgorithm;
import graphe.Graph;
import graphe.Vertex;

public class Individu {
	
	private String nom;
    private Coordonnees position;
    private boolean interieur;
    private List<Coordonnees> chemin;
    private boolean aTerre;

	//////////////////////////////////////////////////////////////////////////////////////
    // Constructeur 1
    public Individu(String nom,Coordonnees position) {
    	this.nom = nom;
    	this.position = position;
    	this.interieur = true;
    	this.chemin = new ArrayList<Coordonnees>();
    	this.aTerre = false;
    }

	//////////////////////////////////////////////////////////////////////////////////////
    // Getters
    public String getNom() {return this.nom;}
    public Coordonnees getPosition() {return this.position;}
    public boolean getInterieur() {return this.interieur;}
    public boolean getATerre() {return this.aTerre;}
    public List<Coordonnees> getChemin() {return this.chemin;}

	//////////////////////////////////////////////////////////////////////////////////////
    // Setters
    public void setNom(String nom) {this.nom = nom;}
    public void setPosition(Coordonnees position) { this.position = position;}
    public void setInterieur(boolean interieur) { this.interieur = interieur;}
    public void setATerre(boolean aTerre) { this.aTerre = aTerre;}
    public void setChemin(LinkedList<Coordonnees> chemin) {this.chemin = chemin;}
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Red�finition de la m�thode toString
    @Override
    public String toString() {
    	String txt = "Individu ";
    	txt += nom;
    	txt += ", position :" + position;
    	if (aTerre) {
    		txt += ". Je suis � terre.";
    	}
    	if (!(interieur)) {
    		txt += ". Je suis sorti.";
    	}
    	return txt;
    }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Les trois m�thodes ci-dessous mettent en place les diff�rents d�placements
    
    // Permet de changer la position de l'Individu 
    public void deplacement() {
    	// On parcourt toutes les positions du chemin sauf la derni�re !
    	// Ainsi on va tomber sur la position actuelle et on n'a plus qu'� changer la position sur la suivante
    	// Si on est � la derni�re position de la liste, on ne change rien
    	for (int i = 0; i < chemin.size() - 1; i++) {
    		if (this.position.equals(chemin.get(i))) {
    			if (!(this.aTerre)) {
    				this.setPosition(chemin.get(i+1));
    				break;
    			}
    			else {
    				this.setATerre(false);
    			}
    		}
    	}
    }
    
    // M�thode d�placement sans collision
    public void deplacementSsCollis(Foule foule, Espace espace) {
    	// On parcourt toutes les positions du chemin sauf la derni�re !
    	// Ainsi on va tomber sur la position actuelle 
    	for (int i = 0; i < chemin.size() - 1; i++) {
    		if (this.position.equals(chemin.get(i))) {
    			if (espace.getPosSorties().contains(chemin.get(i+1))) {
    				this.setPosition(chemin.get(i+1));
    				break;
    			}
    			else {if(!(foule.getPositionIndividus().contains(chemin.get(i+1)))) {
    				this.setPosition(chemin.get(i+1));
    				break;
    				}
    			}
    		}
    	}
    }
  
    // M�thode d�placement inverse
    // Permet de changer la position de l'individu s'il doit retourner en arri�re
    public void deplacementinverse() {
    	// 	On parcourt toutes les positions du chemin
    	// Ainsi on va tomber sur la position actuelle et on n'a plus qu'� changer la position sur celle d'avant
    	for (int i = 1; i < chemin.size(); i++) {
    		if (this.position.equals(chemin.get(i))) {
    			this.setPosition(chemin.get(i-1));
    			break;
    		}
    	}
    }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Les m�thodes ci-dessous permettent d'attribuer � l'individu un chemin vers la sortie
    
    
    // M�thode interm�diaire renvoyant la liste des noeuds que doit parcourir l'individu 
    // pour arriver � la sortie la plus proche
    protected LinkedList<Vertex> findEtapes(Espace espace) {
  	    // On ajoute l'individu au graphe comme point d'int�ret
  	    Vertex source = new Vertex(this.getNom(),this.getPosition());
  	    Graph graphMAJ = espace.addIndividu(source);
        
        // r�cup�ration du chemin sous forme de noeuds gr�ce � Dijkstra
  	    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graphMAJ);
        LinkedList<Vertex> cheminNoeuds = dijkstra.getShortestPath(source);

        return cheminNoeuds;
    }   
    
    // M�thode permettant de trouver le chemin "case par case" et l'affectant directement � l'individu
    public void findChemin(Espace espace) {
    	// On initialise le chemin et on r�cup�re la liste des �tapes (points d'int�rets)
    	LinkedList<Coordonnees> chemin = new LinkedList<Coordonnees>();
    	LinkedList<Vertex> cheminNoeuds = this.findEtapes(espace);
    	// On parcourt les �tapes
    	for (int i = 0; i < cheminNoeuds.size();i++) {
    		Vertex etapeCourante = cheminNoeuds.get(i);
    		if (i == cheminNoeuds.size() - 1) { // Si on est au dernier noeud on l'ajoute
    			chemin.add(etapeCourante.getPosition());
    		} 
    		else { // Sinon, on doit trouver le chemin case par case entre l'�tape courrante et la suivante
    			// On r�cup�re le noeud courant
    			int Xc = etapeCourante.getPosition().x;
    			int Yc = etapeCourante.getPosition().y;
    			// et le noeud suivant
    			Vertex etapeSuivante = cheminNoeuds.get(i+1);
    			int Xs = etapeSuivante.getPosition().x;
    			int Ys = etapeSuivante.getPosition().y;
       
    			// On calcule la droite reliant ces deux points, si elle est verticale on g�re le cas plus tard
    			double a = 0;
    			double b = 0;
    			if (Xc != Xs) {
    				a = ( (double) (Ys - Yc) / (Xs - Xc));  // Pente
    				b = Ys - a * Xs;   // Ordonn�e � l'origine
    			}
    			
    			// Tant que le point courant n'est pas sur le noeud suivant
    			while (!((Xc == Xs) && (Yc == Ys))) {
    				// On ajoute les coordonn�es au chemin
    				chemin.add(new Coordonnees(Xc,Yc));
   
    				// On cherche les coordonn�es du point de l'intersection entre
    				// le carr� 3*3 dont le centre est le point courant (Xc,Yc)
    				// et la droite reliant les 2 points d'int�rets (y = ax + b)
    				// Il existe 2 points d'intersection mais on ne veut que celui "vers" (Xs,Ys)
   
    				double Yinter = 0;
    				double Xinter = 0;
   
    				// On va g�rer les cas o� le point courant et le point suivant sont align�s
    				if (Xc == Xs) { // Les points sont align�s verticalement
    					Xinter = Xs;
    					if (Yc<Ys) {Yinter = Yc+1.5;} else {Yinter = Yc-1.5;}
    				} 
    				else if (Yc == Ys) { // Les points sont align�s horizontalement
    					Yinter = Ys;
    					if (Xc<Xs) {Xinter = Xc+1.5;} else {Xinter = Xc-1.5;}
    				} 
    				else {
    					// Pour x :
    					// Si le point suivant est � droite du point courant on cherche le point d'intersection
    					// vers la droite du carr� 3*3
    					if (Xc < Xs) {
    						Yinter = a * (Xc + 1.5) + b;
    					} 
    					else { // De m�me � gauche
    						Yinter = a * (Xc - 1.5) + b;
    					}
    					// pour y : de m�me. On sait que a!=0
    					if (Yc < Ys) {
    						Xinter = ((double) (Yc + 1.5 - b))/a;
    					} 
    					else {
    					Xinter = ((double) (Yc - 1.5 - b))/a;
    					}
    				}
   
    				// Maintenant qu'on a ces coordonn�es, il reste � d�terminer quelle case est la suivante
    				// On initialise ces coordonn�es :
    				int Xc1 = 0;
    				int Yc1 = 0;
   
    				// Dans ce bloc, il s'agit des cas o� l'individu doit aller dans un "coin" du carr�
    				// (i.e. en haut � gauche, en bas � gauche...)
    				// on le d�termine avec Xinter
    				if ((Xc - 1.5 <= Xinter) && (Xinter <= Xc - 0.5) && (Yinter <= Yc - 0.5)) {Xc1 = Xc-1; Yc1 = Yc-1;}
    				if ((Xc - 1.5 <= Xinter) && (Xinter <= Xc - 0.5) && (Yinter >= Yc + 0.5)) {Xc1 = Xc-1; Yc1 = Yc+1;}
    				if ((Xc + 0.5 <= Xinter) && (Xinter <= Xc + 1.5) && (Yinter <= Yc - 0.5)) {Xc1 = Xc+1; Yc1 = Yc-1;}
    				if ((Xc + 0.5 <= Xinter) && (Xinter <= Xc + 1.5) && (Yinter >= Yc + 0.5)) {Xc1 = Xc+1; Yc1 = Yc+1;}
   
    				// Comme le bloc ci-dessus, l'individu doit aller dans un "coin"
    				// mais il est d�termin� avec Yinter
    				if ((Yc - 1.5 <= Yinter) && (Yinter <= Yc - 0.5) && (Xinter <= Xc - 0.5)) {Yc1 = Yc-1; Xc1 = Xc-1;}
    				if ((Yc - 1.5 <= Yinter) && (Yinter <= Yc - 0.5) && (Xinter >= Xc + 0.5)) {Yc1 = Yc-1; Xc1 = Xc+1;}
    				if ((Yc + 0.5 <= Yinter) && (Yinter <= Yc + 1.5) && (Xinter <= Xc - 0.5)) {Yc1 = Yc+1; Xc1 = Xc-1;}
    				if ((Yc + 0.5 <= Yinter) && (Yinter <= Yc + 1.5) && (Xinter >= Xc + 0.5)) {Yc1 = Yc+1; Xc1 = Xc+1;}
   
    				// On teste en dernier les cas o� l'individu doit se d�placer suivant x ou y seulement
    				// Cela permet de g�rer les cas o� la droite passe exactement entre 2 cases :
    				// On va d'abord consid�rer que l'individu se d�place en diagonale (mais il peut alors couper un mur)
    				// Puis, en passant par ce bloc, on va red�finir Xc1 et Yc1
    				if ((Xc - 0.5 <= Xinter) && (Xinter <= Xc + 0.5) && (Yinter <= Yc - 0.5)) {Xc1 = Xc; Yc1 = Yc-1;}
    				if ((Xc - 0.5 <= Xinter) && (Xinter <= Xc + 0.5) && (Yinter >= Yc + 0.5)) {Xc1 = Xc; Yc1 = Yc+1;}
    				if ((Yc - 0.5 <= Yinter) && (Yinter <= Yc + 0.5) && (Xinter <= Xc - 0.5)) {Yc1 = Yc; Xc1 = Xc-1;}
    				if ((Yc - 0.5 <= Yinter) && (Yinter <= Yc + 0.5) && (Xinter >= Xc + 0.5)) {Yc1 = Yc; Xc1 = Xc+1;}
   
    				// On peut alors mettre � jour (Xc,Yc)
    				Xc = Xc1;
    				Yc = Yc1;
    			}
    		}
    	}
    	// On peut affecter le chemin � l'individu courant
    	this.chemin = chemin;
    }
  	    


}
