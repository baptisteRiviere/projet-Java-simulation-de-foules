package foule;

import java.util.LinkedList;

import coordonnees.Coordonnees;
import espace.Espace;
import espace.MoteurEspace;
import graphe.Vertex;

public class TestIndividu {
	
	public static void testFindEtapes() {
		Espace espace = MoteurEspace.moteurEspace();
		Individu individu1 = new Individu("Michel",new Coordonnees(3,4));
		LinkedList<Vertex> cheminNoeuds = individu1.findEtapes(espace);
		for (Vertex c : cheminNoeuds) {
			System.out.println(c);
		}
	}	
		
	public static void testFindChemin() {
		Espace espace = MoteurEspace.moteurEspace();
		Individu individu1 = new Individu("Michel",new Coordonnees(3,4));
		individu1.findChemin(espace);
		for (Coordonnees c : individu1.getChemin()) {
			System.out.println(c);
		}
	}
}
