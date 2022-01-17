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
    // Redéfinition de la méthode toString
    @Override
    public String toString() {
    	String txt = "Individu ";
    	txt += nom;
    	txt += ", position :" + position;
    	if (aTerre) {
    		txt += ". Je suis à terre.";
    	}
    	if (!(interieur)) {
    		txt += ". Je suis sorti.";
    	}
    	return txt;
    }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Les trois méthodes ci-dessous mettent en place les différents déplacements
    
    // Permet de changer la position de l'Individu 
    public void deplacement() {
    	// On parcourt toutes les positions du chemin sauf la dernière !
    	// Ainsi on va tomber sur la position actuelle et on n'a plus qu'à changer la position sur la suivante
    	// Si on est à la dernière position de la liste, on ne change rien
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
    
    // Méthode déplacement sans collision
    public void deplacementSsCollis(Foule foule, Espace espace) {
    	// On parcourt toutes les positions du chemin sauf la dernière !
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
  
    // Méthode déplacement inverse
    // Permet de changer la position de l'individu s'il doit retourner en arrière
    public void deplacementinverse() {
    	// 	On parcourt toutes les positions du chemin
    	// Ainsi on va tomber sur la position actuelle et on n'a plus qu'à changer la position sur celle d'avant
    	for (int i = 1; i < chemin.size(); i++) {
    		if (this.position.equals(chemin.get(i))) {
    			this.setPosition(chemin.get(i-1));
    			break;
    		}
    	}
    }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Les méthodes ci-dessous permettent d'attribuer à l'individu un chemin vers la sortie
    
    
    // Méthode intermédiaire renvoyant la liste des noeuds que doit parcourir l'individu 
    // pour arriver à la sortie la plus proche
    protected LinkedList<Vertex> findEtapes(Espace espace) {
  	    // On ajoute l'individu au graphe comme point d'intéret
  	    Vertex source = new Vertex(this.getNom(),this.getPosition());
  	    Graph graphMAJ = espace.addIndividu(source);
        
        // récupération du chemin sous forme de noeuds grâce à Dijkstra
  	    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graphMAJ);
        LinkedList<Vertex> cheminNoeuds = dijkstra.getShortestPath(source);

        return cheminNoeuds;
    }   
    
    // Méthode permettant de trouver le chemin "case par case" et l'affectant directement à l'individu
    public void findChemin(Espace espace) {
    	// On initialise le chemin et on récupère la liste des étapes (points d'intérets)
    	LinkedList<Coordonnees> chemin = new LinkedList<Coordonnees>();
    	LinkedList<Vertex> cheminNoeuds = this.findEtapes(espace);
    	// On parcourt les étapes
    	for (int i = 0; i < cheminNoeuds.size();i++) {
    		Vertex etapeCourante = cheminNoeuds.get(i);
    		if (i == cheminNoeuds.size() - 1) { // Si on est au dernier noeud on l'ajoute
    			chemin.add(etapeCourante.getPosition());
    		} 
    		else { // Sinon, on doit trouver le chemin case par case entre l'étape courrante et la suivante
    			// On récupère le noeud courant
    			int Xc = etapeCourante.getPosition().x;
    			int Yc = etapeCourante.getPosition().y;
    			// et le noeud suivant
    			Vertex etapeSuivante = cheminNoeuds.get(i+1);
    			int Xs = etapeSuivante.getPosition().x;
    			int Ys = etapeSuivante.getPosition().y;
       
    			// On calcule la droite reliant ces deux points, si elle est verticale on gère le cas plus tard
    			double a = 0;
    			double b = 0;
    			if (Xc != Xs) {
    				a = ( (double) (Ys - Yc) / (Xs - Xc));  // Pente
    				b = Ys - a * Xs;   // Ordonnée à l'origine
    			}
    			
    			// Tant que le point courant n'est pas sur le noeud suivant
    			while (!((Xc == Xs) && (Yc == Ys))) {
    				// On ajoute les coordonnées au chemin
    				chemin.add(new Coordonnees(Xc,Yc));
   
    				// On cherche les coordonnées du point de l'intersection entre
    				// le carré 3*3 dont le centre est le point courant (Xc,Yc)
    				// et la droite reliant les 2 points d'intérets (y = ax + b)
    				// Il existe 2 points d'intersection mais on ne veut que celui "vers" (Xs,Ys)
   
    				double Yinter = 0;
    				double Xinter = 0;
   
    				// On va gérer les cas où le point courant et le point suivant sont alignés
    				if (Xc == Xs) { // Les points sont alignés verticalement
    					Xinter = Xs;
    					if (Yc<Ys) {Yinter = Yc+1.5;} else {Yinter = Yc-1.5;}
    				} 
    				else if (Yc == Ys) { // Les points sont alignés horizontalement
    					Yinter = Ys;
    					if (Xc<Xs) {Xinter = Xc+1.5;} else {Xinter = Xc-1.5;}
    				} 
    				else {
    					// Pour x :
    					// Si le point suivant est à droite du point courant on cherche le point d'intersection
    					// vers la droite du carré 3*3
    					if (Xc < Xs) {
    						Yinter = a * (Xc + 1.5) + b;
    					} 
    					else { // De même à gauche
    						Yinter = a * (Xc - 1.5) + b;
    					}
    					// pour y : de même. On sait que a!=0
    					if (Yc < Ys) {
    						Xinter = ((double) (Yc + 1.5 - b))/a;
    					} 
    					else {
    					Xinter = ((double) (Yc - 1.5 - b))/a;
    					}
    				}
   
    				// Maintenant qu'on a ces coordonnées, il reste à déterminer quelle case est la suivante
    				// On initialise ces coordonnées :
    				int Xc1 = 0;
    				int Yc1 = 0;
   
    				// Dans ce bloc, il s'agit des cas où l'individu doit aller dans un "coin" du carré
    				// (i.e. en haut à gauche, en bas à gauche...)
    				// on le détermine avec Xinter
    				if ((Xc - 1.5 <= Xinter) && (Xinter <= Xc - 0.5) && (Yinter <= Yc - 0.5)) {Xc1 = Xc-1; Yc1 = Yc-1;}
    				if ((Xc - 1.5 <= Xinter) && (Xinter <= Xc - 0.5) && (Yinter >= Yc + 0.5)) {Xc1 = Xc-1; Yc1 = Yc+1;}
    				if ((Xc + 0.5 <= Xinter) && (Xinter <= Xc + 1.5) && (Yinter <= Yc - 0.5)) {Xc1 = Xc+1; Yc1 = Yc-1;}
    				if ((Xc + 0.5 <= Xinter) && (Xinter <= Xc + 1.5) && (Yinter >= Yc + 0.5)) {Xc1 = Xc+1; Yc1 = Yc+1;}
   
    				// Comme le bloc ci-dessus, l'individu doit aller dans un "coin"
    				// mais il est déterminé avec Yinter
    				if ((Yc - 1.5 <= Yinter) && (Yinter <= Yc - 0.5) && (Xinter <= Xc - 0.5)) {Yc1 = Yc-1; Xc1 = Xc-1;}
    				if ((Yc - 1.5 <= Yinter) && (Yinter <= Yc - 0.5) && (Xinter >= Xc + 0.5)) {Yc1 = Yc-1; Xc1 = Xc+1;}
    				if ((Yc + 0.5 <= Yinter) && (Yinter <= Yc + 1.5) && (Xinter <= Xc - 0.5)) {Yc1 = Yc+1; Xc1 = Xc-1;}
    				if ((Yc + 0.5 <= Yinter) && (Yinter <= Yc + 1.5) && (Xinter >= Xc + 0.5)) {Yc1 = Yc+1; Xc1 = Xc+1;}
   
    				// On teste en dernier les cas où l'individu doit se déplacer suivant x ou y seulement
    				// Cela permet de gérer les cas où la droite passe exactement entre 2 cases :
    				// On va d'abord considérer que l'individu se déplace en diagonale (mais il peut alors couper un mur)
    				// Puis, en passant par ce bloc, on va redéfinir Xc1 et Yc1
    				if ((Xc - 0.5 <= Xinter) && (Xinter <= Xc + 0.5) && (Yinter <= Yc - 0.5)) {Xc1 = Xc; Yc1 = Yc-1;}
    				if ((Xc - 0.5 <= Xinter) && (Xinter <= Xc + 0.5) && (Yinter >= Yc + 0.5)) {Xc1 = Xc; Yc1 = Yc+1;}
    				if ((Yc - 0.5 <= Yinter) && (Yinter <= Yc + 0.5) && (Xinter <= Xc - 0.5)) {Yc1 = Yc; Xc1 = Xc-1;}
    				if ((Yc - 0.5 <= Yinter) && (Yinter <= Yc + 0.5) && (Xinter >= Xc + 0.5)) {Yc1 = Yc; Xc1 = Xc+1;}
   
    				// On peut alors mettre à jour (Xc,Yc)
    				Xc = Xc1;
    				Yc = Yc1;
    			}
    		}
    	}
    	// On peut affecter le chemin à l'individu courant
    	this.chemin = chemin;
    }
  	    


}
