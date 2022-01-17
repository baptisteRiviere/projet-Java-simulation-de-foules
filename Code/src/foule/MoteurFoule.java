package foule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import coordonnees.Coordonnees;
import espace.Espace;
import espace.Obstacle;
import espace.Sortie;

public class MoteurFoule {
	
	protected List<Coordonnees> placesLibres;
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Constructeur par d�faut
	public MoteurFoule() {
		this.placesLibres = new ArrayList<Coordonnees>();
	}
	
	// Constructeur � partir d'un espace
	public MoteurFoule(Espace espace) {
		// Initialisation
		this.placesLibres = new ArrayList<Coordonnees>();
		
		// Onjectif : construire la liste de places libres associ�es � l'espace
		// on r�cup�re la surface de l'espace
		int[] surface = espace.getSurface();
		int xmin = surface[0];
		int xmax = surface[1];
		int ymin = surface[2];
		int ymax = surface[3];
		
		// On construit la liste de toutes les positions dans la surface
		for (int xVar = xmin; xVar <= xmax; xVar++) {
			for (int yVar = ymin; yVar <= ymax; yVar++) {
				this.placesLibres.add(new Coordonnees(xVar,yVar));
			}
		}
		// Puis on parcourt la liste des obstacles et on supprime leurs positions de la liste
		for (Obstacle o : espace.getObstacles()) {
			Coordonnees c = o.getPosition();
			this.placesLibres.remove(c);
		}
		
		// De m�me pour les sorties
		for (Sortie s : espace.getSorties()) {
			Coordonnees c = s.getPosition();
			this.placesLibres.remove(c);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// getter placesLibres
	public List<Coordonnees> getPlacesLibres() {return this.placesLibres;}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	// On veut cr�er une foule de n personnes dans l'espace 
	// Si on cherche � toujours avoir la m�me : seed = true
	public Foule moteurFoule(int nombreIndividus,Espace espace,boolean seed) {
		
		// Initialisation de la foule
		Foule foule = new Foule();
		
		// Initialisation du g�n�rateur random 
		Random generator = new Random();
		// Si on cherche � toujours avoir la m�me g�n�ration, seed = true 
		if (seed) {generator = new Random(5);}
		
		// Il ne reste plusqu'� ajouter les individus dans les cases vides (donn�es avec placesLibres)
		for (Integer i = 1; i<=nombreIndividus ; i++) {
			// On v�rifie qu'il reste de la place (sinon on break, la salle est � son maximum)
			if (this.placesLibres.size() == 0) {
				break;
			} else {
			// On tire un indice au hasard pour trouver des coordonn�es libres al�atoirement
			int indiceAleatoire =  generator.nextInt(this.placesLibres.size());
			Coordonnees c = placesLibres.get(indiceAleatoire);
			
			// On les retire de la liste pour ne pas les prendre en compte avec les prochains individus
			this.placesLibres.remove(c);
			
			// Et on ajoute l'individu � notre foule
			Individu individu = new Individu("ind_n" + i,c);
			foule.addIndiv(individu);
			}
		}
		
		// On attribue son chemin � chaque individu de la foule
		foule.attribuerChemins(espace);
		// On peut renvoyer la foule constitu�e
		return foule;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	//Creation d'une foule sp�cifique � un avion
	public Foule moteurFouleAvion(int prs, Espace espace) {
		
		// Initialisation de la foule
		Foule foule = new Foule();
		
		List<Coordonnees> place = new ArrayList<Coordonnees>();
		//Prend les positions des chaises
		for (Obstacle obs : espace.getObstacles()) {
			if (obs.getType().equals("chaise")) {
				place.add(new Coordonnees(obs.getPosition().x,obs.getPosition().y));
			}
		}
		//Modifie les positions de chaise pour avoir les positions des individus
		for (Coordonnees coo : place) {
			if (coo.x == 93) {
				coo.x -= 1; 
			}
			else {
				coo.x += 1;
			}
		}
		
		//on regarde que le nombre de personne ne depasse pas le nombre de place limite de l'avion
		if (prs>=place.size()) {
			prs = place.size();
		}
		
		// Et on ajoute l'individu � notre foule
		for (int i=0 ; i<prs ; i++) {
			foule.addIndiv(new Individu("ind_n" + i, place.get(i)));
		}
		// On attribue son chemin � chaque individu de la foule
		foule.attribuerChemins(espace);
		// On peut renvoyer la foule constitu�e
		return foule;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	public Foule moteurFouleClass(int prs, Espace espace) {
		
		// Initialisation de la foule
		Foule foule = new Foule();
		
		List<Coordonnees> place = new ArrayList<Coordonnees>();
		place.add(new Coordonnees(1,3));
		place.add(new Coordonnees(8,2));
		place.add(new Coordonnees(10,2));
		place.add(new Coordonnees(13,3));
		place.add(new Coordonnees(18,1));
		place.add(new Coordonnees(1,7));
		place.add(new Coordonnees(11,6));
		place.add(new Coordonnees(12,8));
		place.add(new Coordonnees(16,6));
		place.add(new Coordonnees(7,10));
		place.add(new Coordonnees(11,10));
		place.add(new Coordonnees(13,10));
		place.add(new Coordonnees(16,9));
		
		//on regarde que le nombre de personne ne depasse pas le nombre de place limite de l'avion
		if (prs>=place.size()) {
			prs = place.size();
		}
		
		// Et on ajoute l'individu � notre foule
		for (int i=0 ; i<prs ; i++) {
			foule.addIndiv(new Individu("ind_n" + i, place.get(i)));
		}
		// On attribue son chemin � chaque individu de la foule
		foule.attribuerChemins(espace);
		// On peut renvoyer la foule constitu�e
		return foule;
	}
}