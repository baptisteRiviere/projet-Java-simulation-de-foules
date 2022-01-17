package espace;

import java.util.ArrayList;
import java.util.List;

import coordonnees.Coordonnees;
import graphe.Graph;
import graphe.Vertex;
import graphe.Edge;
import java.lang.Math;

public class Espace {
	
	private List<Obstacle> obstacles;
	private List<Sortie> sorties;
	private List<Element> elements;
    private Graph graphe;
    // Il s'agit de [xmin,xmax,ymin,ymax]
    private int[] surface;
	
    //////////////////////////////////////////////////////////////////////////////////////
	// Constructeur 1
	public Espace() {
		this.obstacles = new ArrayList<Obstacle>();
		this.sorties = new ArrayList<Sortie>();
		this.elements = new ArrayList<Element>();
        this.surface = null;
        this.graphe = new Graph();
    }
	
	// Constructeur 2
	public Espace(List<Obstacle> obstacles,List<Sortie> sorties) {
		this.obstacles = obstacles;
		this.sorties = sorties;
		this.elements = new ArrayList<Element>();
		for (Sortie s : sorties) { this.elements.add((Element) s);}
		for (Obstacle o : obstacles) { this.elements.add((Element) o);}
		// On peut initialiser l'attribut surface et le mettre � jour
		this.surface = null;
	    for (Obstacle o : obstacles) {this.miseAJourSurface(o.getPosition());}
	    for (Sortie s : sorties) {this.miseAJourSurface(s.getPosition());}
		// On cr�e alors le graphe associ�
	    this.construireGrapheAssocie();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
    // Getters
    public List<Obstacle> getObstacles() {return this.obstacles;}
    public List<Sortie> getSorties() {return this.sorties;}
    public List<Element> getElements() {return this.elements;}
    public Graph getGraphe() {return this.graphe;}
    public int[] getSurface() {return this.surface;}
    public List<Coordonnees> getPosSorties(){
	    ArrayList<Coordonnees> coord = new ArrayList<Coordonnees>();
	    for(Sortie exit : this.sorties) {
	      coord.add(exit.getPosition());
	    }
	    return coord;
	}
	
    /////////////////////////////////////////////////////////////////////////////////////
	// red�finition de la m�thode toString
	@Override
	public String toString() {
		String txt = "Espace compos� de : \n";
		txt += "Sorties :";
		for (Sortie exit : this.sorties) {
			txt += "\n" + exit.toString();}
		txt += "\n" + "Obstacles:";
		for (Obstacle obst : this.obstacles) {
			txt += "\n" + obst.toString();}
		txt += "\nsurface :"; 
		for (Integer i : this.surface) {
			txt += "\n" + i.toString();}
		return txt;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Les m�thodes suivantes permettent d'ajouter ou de supprimer un obstacle 
	// ou une sortie d'un espace tout en reconstruisant le graphe associ�
	
	// ajoute une sortie � la liste des sorties
	public void addSortie(Sortie s) {
		// Si un obstacle ou une sortie existe d�j� sur cette coordonn�e : on l'�crase	
		for (Element e : this.elements) {
			if (s.getPosition().equals(e.getPosition())) {
				this.obstacles.remove((Obstacle) e);
				this.elements.remove(e);
				break;
			}
		}
		// On peut alors ajouter l'obstacle d�sir�
		this.sorties.add(s);
		this.elements.add(s);
		// On doit reconstruire la surface et le graphe associ� 
		this.miseAJourSurface(s.getPosition());
		this.construireGrapheAssocie();
	}
	
	// ajoute un obstacle � la liste Obstacle
	public void addObstacle(Obstacle o) {
		// Si un obstacle ou une sortie existe d�j� sur cette coordonn�e : on l'�crase		
		for (Element e : this.elements) {
			if (o.getPosition().equals(e.getPosition())) {
				this.obstacles.remove((Obstacle) e);
				this.elements.remove(e);
				break;
			}
		}
		// On peut alors ajouter l'obstacle d�sir�
		this.obstacles.add(o);
		this.elements.add(o);
		// On doit reconstruire la surface et le graphe associ� 
		this.miseAJourSurface(o.getPosition());
		this.construireGrapheAssocie();
	}
	
	// Supprime un �l�ment de l'espace, qu'il soit obstacle ou sortie
	public void removeElement(Element e) {
		this.elements.remove(e);
		if (e instanceof Sortie) { this.sorties.remove(e); }
		if (e instanceof Obstacle) { this.obstacles.remove(e); }
		// On doit reconstruire la surface et le graphe associ� 
		this.miseAJourSurface(e.getPosition());
		this.construireGrapheAssocie();
	}
		
	//////////////////////////////////////////////////////////////////////////////////////
    // Met � jour la surface de l'espace
    protected void miseAJourSurface(Coordonnees c) {
    	if (this.surface == null) {
    		this.surface = new int[] {c.x,c.x,c.y,c.y};
    	} else {
    		// Pour mettre � jour la surface avant l'ajout d'un nouvel objet (sortie ou obstacle) 
        	if (c.x < this.surface[0]) {this.surface[0] = c.x;}
        	if (c.x > this.surface[1]) {this.surface[1] = c.x;} 
        	if (c.y < this.surface[2]) {this.surface[2] = c.y;} 
        	if (c.y > this.surface[3]) {this.surface[3] = c.y;} 
    	}
    }
    
	//////////////////////////////////////////////////////////////////////////////////////
    // Les m�thodes suivantes permettent de g�rer l'ouverture et la fermeture de sorties
    
    // M�thode pour ouvrir une sortie proprement
    public void ouvrirSortie(Sortie s) {
    	s.ouvrirSortie();
    	// Il est alors n�cessaire de reconstruire le graphe associ� � l'espace, 
    	this.construireGrapheAssocie();
    }
    
    // M�thode pour fermer une sortie proprement
    public void fermerSortie(Sortie s) {
    	s.fermerSortie();
    	// Il est alors n�cessaire de reconstruire le graphe associ� � l'espace, 
    	this.construireGrapheAssocie();
    }
    
	// Methode pour ouvrir toutes les sorties proprement
	public void ouvrirToutesSorties() {
		for (Sortie s : this.sorties) { s.ouvrirSortie(); }
		// Il est alors n�cessaire de reconstruire le graphe associ� � l'espace, 
		this.construireGrapheAssocie();
	}
	
	// Methode pour fermer toutes les sorties proprement
	public void fermerToutesSorties() {
		for (Sortie s : this.sorties) { s.fermerSortie(); }
		// Il est alors n�cessaire de reconstruire le graphe associ� � l'espace, 
		this.construireGrapheAssocie();
	}
    
	//////////////////////////////////////////////////////////////////////////////////////
	// La finalit� des m�thodes suivantes est de construire le graphe associ� � l'espace
    
	// M�thode renvoyant les coordonn�es des obsacles voisins d'un obstacle donn�
    protected List<Coordonnees> findPositionsObstaclesVoisins(Obstacle o1) {
        List<Coordonnees> positionsObsVoisins = new ArrayList<Coordonnees>();
        int x1 = o1.getPosition().x;
        int y1 = o1.getPosition().y;
        for (Obstacle o2 : this.obstacles) {
        	// On v�rifie que o1 != o2
        	if (!(o1.equals(o2))) {
        		int x2 = o2.getPosition().x;
        		int y2 = o2.getPosition().y;
        		if  ((x1 - 1 <= x2) && (x2 <= x1 + 1)
        		&&  (y1 - 1 <= y2) && (y2 <= y1 + 1)) {
        			// Dans ce cas l'obstacle o2 est voisin de o1, on ajoute ses coordonn�es � la liste 
        			positionsObsVoisins.add(new Coordonnees(x2,y2));
        		}
            }
        }
        return positionsObsVoisins;
    }



    // M�thode prenant en argument un obstacle et renvoyant les points d'int�ret autour de cet obstacle
    protected List<Coordonnees> pointsInteretObstacle(Obstacle o1) {
    	// On r�cup�re les voisins
        List<Coordonnees> positionsObsVoisins = findPositionsObstaclesVoisins(o1);
        // On initialise la liste de points d'int�ret
        List<Coordonnees> pointsInteret = new ArrayList<Coordonnees>();

        // On simplifie l'�criture : x et y sont les positions de l'obstacle
        int x = o1.getPosition().x;
        int y = o1.getPosition().y;

        // On a 4 points d'int�rets possibles autour de l'obstacle (dans les "coins")
        // Un point d'int�ret est d�fini comme un point sans obstacle dont les deux voisins en commun avec l'obstacle �tudi� sont sans obstacle

        // En haut � gauche
        if  ( !((positionsObsVoisins.contains(new Coordonnees(x-1,y-1)))
            ||  (positionsObsVoisins.contains(new Coordonnees(x-1,y)))
            ||  (positionsObsVoisins.contains(new Coordonnees(x,y-1)))
        )) { pointsInteret.add(new Coordonnees(x-1,y-1)) ;}

        // En haut � droite
        if  ( !((positionsObsVoisins.contains(new Coordonnees(x+1,y-1)))
        	||  (positionsObsVoisins.contains(new Coordonnees(x,y-1)))
        	||  (positionsObsVoisins.contains(new Coordonnees(x+1,y)))
        )) { pointsInteret.add(new Coordonnees(x+1,y-1)) ;}

        // En bas � gauche
        if  ( !((positionsObsVoisins.contains(new Coordonnees(x-1,y+1)))
        	||  (positionsObsVoisins.contains(new Coordonnees(x-1,y)))
        	||  (positionsObsVoisins.contains(new Coordonnees(x,y+1)))
        )) { pointsInteret.add(new Coordonnees(x-1,y+1)) ;}

        // En bas � droite
        if  ( !((positionsObsVoisins.contains(new Coordonnees(x+1,y+1)))
        	||  (positionsObsVoisins.contains(new Coordonnees(x,y+1)))
        	||  (positionsObsVoisins.contains(new Coordonnees(x+1,y)))
        )) { pointsInteret.add(new Coordonnees(x+1,y+1)) ;}
    
        // On peut retourner la liste des points d'int�rets
        return pointsInteret;
    }


    // M�thode renvoyant tous les points d'int�ret de l'espace (sans les sorties)
    protected List<Coordonnees> findPointsInteret() {
        List<Coordonnees> pointsInteret = new ArrayList<Coordonnees>();
        // On ajoute les "coins" d'obstacles (en prenant en compte les doublons et les points hors de la sc�ne)
        for (Obstacle o : this.obstacles) {
            // Pour chaque obstacle on r�cup�re les points d'int�rets qui le bordent
            List<Coordonnees> pointsInteretObstacle = pointsInteretObstacle(o);
            // On les ajoute � la liste des points d'interet sauf s'ils y sont d�j�
            // Ou s'ils sont hors de la sc�ne (permet d'�liminer environ la moiti� des noeuds (inutiles))
            for (Coordonnees pi : pointsInteretObstacle) {
                if ( (!	(pointsInteret.contains(pi))) 		// pi n'est pas d�j� dans la liste
                	&&	(pi.x >= this.surface[0])		// et pi pas trop � gauche
                	&& 	(pi.x <= this.surface[1])		// et pi pas trop � droite
                	&&	(pi.y >= this.surface[2])		// et pi pas trop bas
                	&&	(pi.y <= this.surface[3])) { 	// et pi pas trop haut
                    pointsInteret.add(pi);
                }
            }
        }
        // on peut renvoyer la liste ainsi consitu�e
        return pointsInteret;
    }


    
    // Cette m�thode permet de d�terminer s'il existe un obstacle entre deux noeuds v1 et v2
    public boolean sontRelies(Vertex v1,Vertex v2) {
    	// On r�cup�re les positions des noeuds
        Coordonnees c1 = v1.getPosition();
        Coordonnees c2 = v2.getPosition();
        // On r�cup�re les coordonn�es
        int x1 = c1.x;
        int y1 = c1.y;
        int x2 = c2.x;
        int y2 = c2.y;
        // On peut maintenant parcourir tous les obstacles pour v�rifier si l'un d'eux coupe l'arc
        for (Obstacle o : this.obstacles) {
            // On r�cup�re ses coordonn�es
            int x = o.getPosition().x;
            int y = o.getPosition().y;
            // on v�rifie que la droite peut couper l�obstacle avant d�aller plus loin                     
            if (((x1 <= x) && (x2 >= x) && (y1 <= y) && (y2 >= y))
            ||  ((x2 <= x) && (x1 >= x) && (y2 <= y) && (y1 >= y))
            ||  ((x1 <= x) && (x2 >= x) && (y2 <= y) && (y1 >= y))
            ||  ((x2 <= x) && (x1 >= x) && (y1 <= y) && (y2 >= y))) {
                // Dans ce cas on peut v�rifier que le segment coupe l'obstacle
            	if (!(x1 == x2)) { 
            		double a = ( (double) (y1 - y2) / (x1 - x2));   // Pente 
            		double b = y1 - a * x1;             			// Ord � l'orig
                    double ym = a * (x - 0.5) + b;      
                    double yp = a * (x + 0.5) + b;
                    if 	(!(	((yp <= (y-0.5)) && (ym <= (y-0.5))) 
                        ||  ((yp <= (y-0.5)) && (ym <= (y-0.5))))) {
                    		// Dans ce cas le segment coupe l'obstacle
                        	return false;
                    } 	
            	} else {
            		// Si on a une pente infinie (segment vertical)
            		// On est certain que l'obstacle est coup� par le segment puisqu'il a pass� le premier crit�re
            		return false;
            	}    
                
            }
        }
        // On a pass� tous les obstacles sans que le segment soit coup�
        return true;
    }
    
    
    // M�thode construisant le graphe associ� � l'espace
    protected void construireGrapheAssocie() {
        List<Vertex> nodes = new ArrayList<Vertex>();
        List<Edge> edges = new ArrayList<Edge>();
        Integer i = 0;
        
        // On ajoute les sorties non ferm�es comme noeud
        for (Sortie s : this.sorties) { if (s.getOuvert()) {
        	nodes.add(new Vertex("sortie num" + i.toString(),s.getPosition(),true));
        	i++;
        }}
        i = 0;
        // On ajoute les points d'int�ret
        List<Coordonnees> pointsInteret = this.findPointsInteret();
        for (Coordonnees c : pointsInteret) {
            nodes.add(new Vertex("pi num" + i.toString(),c));
            i++;
        }

        i = 0;
        // Puis on consruit les arcs
        // Pour cela on parcourt tous les noeuds
        for (Vertex v1 : nodes) {
            // Et on les compare � tous les autres noeuds
            // Le faire 2 fois permet de cr�er des arcs non orient�s
            for (Vertex v2 : nodes) {
            	// Si v1 et v2 sont diff�rents et "peuvent se voir"
                if ((!(v1.equals(v2))) && (sontRelies(v1,v2))) {
                	// On peut ajouter l'arc � la liste des arcs
                    // avec la distance euclidienne comme poids
                	int x1 = v1.getPosition().x;
                    int y1 = v1.getPosition().y;
                    int x2 = v2.getPosition().x;
                    int y2 = v2.getPosition().y;
                    double distanceEucl = Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
                    Edge arc = new Edge(i.toString(),v1,v2,distanceEucl);
                    edges.add(arc);
                    i++;
                }
            }
        }

        // On peut construire le graphe et l'affecter en attribut
        this.graphe = new Graph(nodes,edges);
    }
    
    
    
	//////////////////////////////////////////////////////////////////////////////////////
    // M�thode renvoyant le graphe li� � l'espace avec un individu en noeud source,
    // Tout en ne modifiant pas le graphe de base
    public Graph addIndividu(Vertex source) {
		Graph newGraph = this.graphe.copyGraph();
    	newGraph.addVertex(source);
    	Integer i = 0;
    	// On compare la source � tous les autres noeuds
        for (Vertex v2 : newGraph.getVertexes()) {
        	// Si source et v2 sont diff�rents et "peuvent se voir"
            if ((!(source.equals(v2))) && (sontRelies(source,v2))) {
            	// On peut ajouter l'arc � la liste des arcs
                // avec la distance euclidienne comme poids
            	int x1 = source.getPosition().x;
                int y1 = source.getPosition().y;
                int x2 = v2.getPosition().x;
                int y2 = v2.getPosition().y;
                double distanceEucl = Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
                Edge arc = new Edge("ai_n" + i.toString(),source,v2,distanceEucl);
                newGraph.addEdge(arc);
                i++;
            }
        }
    	return newGraph;
    }
}
