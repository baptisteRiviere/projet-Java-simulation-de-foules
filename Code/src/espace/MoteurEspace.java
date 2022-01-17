package espace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coordonnees.Coordonnees;


public class MoteurEspace {
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Ces premières fonctions permettent de créer plus rapidement nos salles
	
	
	// Crée le mur entre les deux coordonnées qui doivent nécessairement avoir x OU y en commun
	// renvoie une liste vide sinon
	public static List<Obstacle> createMur(Coordonnees depart,Coordonnees arrivee) {
		List<Obstacle> mur = new ArrayList<Obstacle>();
		// S'il s'agit d'un mur vertical
		if (depart.x == arrivee.x) {
			for (int i = depart.y; i <= arrivee.y; i++) {
				Obstacle o = new Obstacle(new Coordonnees(depart.x,i),"mur");
				mur.add(o);
			}
		}
		// S'il s'agit d'un mur horizontal
		if (depart.y == arrivee.y) {
			for (int i = depart.x; i <= arrivee.x; i++) {
				Obstacle o = new Obstacle(new Coordonnees(i,depart.y),"mur");
				mur.add(o);
			}		
		}
		// On renvoie le mur constitué
		return mur;
	}
		
	
	// Renvoie une liste d'obstacle définissant un grand obstacle rectangulaire
	// à partir d'un de son coin en haut à gauche et de sa longueur/largeur
	public static List<Obstacle> createRectanglePlein(Coordonnees origine,int longueur,int largeur,String type) {
		List<Obstacle> rectanglePlein = new ArrayList<Obstacle>();
		for (int i = origine.x;i<origine.x+longueur;i++) {
			for (int j = origine.y;j<origine.y+largeur;j++) {
				Obstacle o = new Obstacle(new Coordonnees(i,j),type);
				rectanglePlein.add(o);
			}
		}
		return rectanglePlein;
	}
	
	// Comme la méthode précédente mais renvoyant par défaut une liste de murs
	public static List<Obstacle> createRectanglePlein(Coordonnees origine,int longueur,int largeur) {
		return createRectanglePlein(origine,longueur,largeur,"mur");
	}
	
	
	// Crée une pièce rectangulaire à partir d'une longeur et d'une largeur en entrée et de la coordonnée origine
	// Il s'agit directement d'un espace
	public static Espace createRectangleCreux(Coordonnees c,int longueur, int largeur) {
		int x = c.x;
		int y = c.y;
		// Création espace
		Espace espace = new Espace();
		// Mur Nord
		for (Obstacle o : createMur(new Coordonnees(x,y),new Coordonnees(x+longueur-1,y))) { espace.addObstacle(o); }
		// Mur Ouest
		for (Obstacle o : createMur(new Coordonnees(x,y+1),new Coordonnees(x,y+largeur-2))) { espace.addObstacle(o); }
		// Mur Sud
		for (Obstacle o : createMur(new Coordonnees(x,y+largeur-1),new Coordonnees(x+longueur-1,y+largeur-1))) { espace.addObstacle(o); }
		// Mur Est
		for (Obstacle o : createMur(new Coordonnees(x+longueur-1,y+1),new Coordonnees(x+longueur-1,y+largeur-2))) { espace.addObstacle(o); }
		// On peut renvoyer la pièce ainsi constituée
		return espace;
	}
	
	// Crée une pièce rectangulaire comme la méthode ci-dessus
	// Par défaut sans coordonnées, le coin en haut à gauche est à (0,0) 
	public static Espace moteurPieceRectangulaire(int longueur, int largeur) {
		return createRectangleCreux(new Coordonnees(0,0), longueur,largeur);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Méthode permettant de créer nos salles, 
	// Cette méthode est utilisée dans la plupart de nos tests
	public static Espace moteurEspace() {
		Espace espace = moteurPieceRectangulaire(30,20);
		
		// On ajoute certains murs
		for (Obstacle o : createMur(new Coordonnees(9,3),new Coordonnees(9,8))) {espace.addObstacle(o);}
		for (Obstacle o : createMur(new Coordonnees(21,1),new Coordonnees(21,5))) {espace.addObstacle(o);}
		for (Obstacle o : createMur(new Coordonnees(22,5),new Coordonnees(25,5))) {espace.addObstacle(o);}
		for (Obstacle o : createMur(new Coordonnees(17,1),new Coordonnees(17,6))) {espace.addObstacle(o);}
		for (Obstacle o : createMur(new Coordonnees(27,5),new Coordonnees(28,5))) {espace.addObstacle(o);}
		for (Obstacle o : createMur(new Coordonnees(5,9),new Coordonnees(28,9))) {espace.addObstacle(o);}
		for (Obstacle o : createMur(new Coordonnees(1,13),new Coordonnees(15,13))) {espace.addObstacle(o);}
		for (Obstacle o : createMur(new Coordonnees(15,14),new Coordonnees(15,16))) {espace.addObstacle(o);}
		// On peut ajouter un mur par dessus un autre (les nouveaux obstacles vont écraser les anciens)
		for (Obstacle o : createMur(new Coordonnees(21,15),new Coordonnees(29,15))) {espace.addObstacle(o);}
		// On peut ajouter un mur "à l'envers"
		for (Obstacle o : createMur(new Coordonnees(17,6),new Coordonnees(17,1))) {espace.addObstacle(o);}
		// On ajoute d'autres obstacles
		Obstacle o1 = new Obstacle(new Coordonnees(9,1),"mur");
		Obstacle o2 = new Obstacle(new Coordonnees(21,15),"mur");
		Obstacle o3 = new Obstacle(new Coordonnees(21,18),"mur");
		espace.addObstacle(o1);
		espace.addObstacle(o2);
		espace.addObstacle(o3);
		// Ajout d'une table
		for (Obstacle o : createRectanglePlein(new Coordonnees(12,3),4,4,"table")) {espace.addObstacle(o);}
		
		// Ajout des sorties (la suppression des murs à cette place est automatique)
		espace.addSortie(new Sortie(new Coordonnees(29,11)));
		espace.addSortie(new Sortie(new Coordonnees(29,12)));
		espace.addSortie(new Sortie(new Coordonnees(19,0)));
		
		// On peut renvoyer l'espace ainsi constitué
		return espace;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Les méthodes ci-dessous permettent d'enregistrer et de récupérer les salles dans des fichiers txt
	
	// Enregistrement d'une salle	
	public static void EnregistrerEspace(String fileName,Espace espace) {
		// Cration du fichier
		String filePath = "RepertoireSalles\\" + fileName + ".txt";
		Path logFile = Paths.get(filePath);
		
		// Si le fichier n'existe pas on le cree
		if (! Files.exists(logFile)) {
			try { Files.createFile(logFile);
			} catch (IOException e) {e.printStackTrace ();}
		
		// buffer en ecriture ( ecrase l'existant ), encodage UTF8
		try (BufferedWriter writer = Files.newBufferedWriter(logFile,StandardCharsets.UTF_8, StandardOpenOption.WRITE)) {
			for (Obstacle o : espace.getObstacles()) {
				String txt = "o" + "," + o.getPosition().x + "," + o.getPosition().y + "," + o.getType();
				writer.write(txt + "\n");
			}
			for (Sortie s : espace.getSorties()) {
				String txt = "s" + "," + s.getPosition().x + "," + s.getPosition().y + "," + s.getOuvert();
				writer.write(txt + "\n");
			}
		} catch (IOException e) {e. printStackTrace ();}
		}
	}	
	
	// Récupération d'une salle
	public static Espace getEspaceAssocie(Object fileName) {
		// Initialisation de la liste d'obstacles
		List<Obstacle> obstacles = new ArrayList<Obstacle>();
		List<Sortie> sorties = new ArrayList<Sortie>();
		
		// Lecture d'un fichier
		String filePath = "RepertoireSalles\\" + fileName + ".txt";
		Path p = Paths.get(filePath); // creation de l'objet Path

		// ouverture d'un buffer en lecture
		try (BufferedReader reader = Files.newBufferedReader(p)) {
			String line;
			while ((line = reader.readLine()) != null) {
				List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
				String nature = items.get(0);
				Integer x = Integer.valueOf(items.get(1));
				Integer y = Integer.valueOf(items.get(2));
				if (nature.equals("o")) {
					String type = items.get(3);
					Obstacle o = new Obstacle(new Coordonnees(x,y),type);
					obstacles.add(o);
				} else if (nature.equals("s")) {
					Boolean ouverte = Boolean.valueOf(items.get(3)).booleanValue() ;
					Sortie s = new Sortie(new Coordonnees(x,y),ouverte);
					sorties.add(s);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Espace espace = new Espace(obstacles,sorties);
		return espace;
	}
	
	
	
	
	
	public static Espace moteurEspace2() {
		Coordonnees Coo = new Coordonnees(0,0);
		Espace espace = createRectangleCreux(Coo,44,38);
		
		// Ajout des sorties (la suppression des murs à cette place est automatique)
		espace.addSortie(new Sortie(new Coordonnees(43,24)));
		espace.addSortie(new Sortie(new Coordonnees(43,23)));
		
		
		// On ajoute certains murs
		//for (Obstacle o : createMur(new Coordonnees(1,9),new Coordonnees(12,9))) {espace.addObstacle(o);}
		
		
		//for (Obstacle o : createRectanglePlein(new Coordonnees(46,3),2,2,"mur")) {espace.addObstacle(o);}
		
		
		//espace.addObstacle(new Obstacle(new Coordonnees(12,15), "mur"));
		
		
		//for (Obstacle o : createRectanglePlein(new Coordonnees(1,6),1,2,"table")) {espace.addObstacle(o);}
		
		
		//espace.addObstacle(new Obstacle(new Coordonnees(18, 31), "table"));
		
		
		//for (Obstacle o : createRectanglePlein(new Coordonnees(4,1),4,3,"chaise")) {espace.addObstacle(o);}
		
		
		//espace.addObstacle(new Obstacle(new Coordonnees(2, 7), "chaise"));
		
		
		//Enregistrement de l'espace
		//EnregistrerEspace("SalleSaloon", espace);
		
		// On peut renvoyer l'espace ainsi constitué
		return espace;
	}
	
}