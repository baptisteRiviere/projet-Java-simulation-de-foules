package io;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import coordonnees.Coordonnees;
import espace.Espace;
import espace.MoteurEspace;
import espace.Obstacle;
import espace.Sortie;
import foule.Foule;
import foule.MoteurFoule;
import simulation.Simulation;

public class AffichageResultat extends JFrame{

	private static final long serialVersionUID = -2167166736477861517L;
	private JPanel contentPane;
	private JPanel contentPane1;
	private JPanel contentPane2;
	private JPanel contentPane3;
	private JPanel contentPane4;
	private Espace espaceSansObs;
	private Espace espaceAvecObs;
	private MoteurFoule mf1;
	private MoteurFoule mf2;
	private MoteurFoule mf3;
	private MoteurFoule mf4;
	private Foule fouleSansObs1;
	private Foule fouleAvecObs1;
	private Foule fouleSansObs2;
	private Foule fouleAvecObs2;
	private Simulation simulation1;
	private Simulation simulation2;
	private Simulation simulation3;
	private Simulation simulation4;
	private List<Integer> tempsACAO;
	private List<Integer> tempsACSO;
	private List<Integer> tempsSCAO;
	private List<Integer> tempsSCSO;
	
	
	public AffichageResultat(int longeur, int largeur, int prs, int nbsim) {
		super("Resultat des experiences");
		this.tempsACAO = new ArrayList<Integer>();
		this.tempsACSO = new ArrayList<Integer>();
		this.tempsSCAO = new ArrayList<Integer>();
		this.tempsSCSO = new ArrayList<Integer>();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image_projet_foule\\cube_projet_foule_miniature.jpg")); //Icon de la fenetre
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Parametre et dimension de la fenetre
		this.setBounds(100, 100, 450, 300);
		this.setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5,1,10,10));
		
		//Creatiuon de la salle sans obstacle
		this.espaceSansObs = MoteurEspace.createRectangleCreux(new Coordonnees(0, 0), longeur, largeur); //Creation de l'espace
		int cooExit = longeur/2;
		Sortie exit = new Sortie(new Coordonnees(cooExit, 0));// Creation de la sortie au centre de la longeur de la salle
		this.espaceSansObs.addSortie(exit);
		
		//Creation de la salle avec obstacle
		this.espaceAvecObs = MoteurEspace.createRectangleCreux(new Coordonnees(0, 0), longeur, largeur); //Creation de l'espace
		this.espaceAvecObs.addSortie(exit);
		if (longeur>=5 & longeur<11 & largeur>=5) {
			this.espaceAvecObs.addObstacle(new Obstacle(new Coordonnees(cooExit, 2), "mur"));
		}
		if (longeur>=11 & largeur>=9) {
			for (Obstacle obs : MoteurEspace.createRectanglePlein(new Coordonnees(cooExit-1, 2), 3, 3)) {this.espaceAvecObs.addObstacle(obs);}
		}
		
		for (int i = 0; i<nbsim; i++) {
			this.mf1 = new MoteurFoule(this.espaceSansObs);
			this.fouleSansObs1 = mf1.moteurFoule(prs, espaceSansObs, false);//Creation de la foule
			this.mf2 = new MoteurFoule(this.espaceAvecObs);
			this.fouleAvecObs1 = mf2.moteurFoule(prs, espaceAvecObs, false);//Creation de la foule
			this.mf3 = new MoteurFoule(this.espaceSansObs);
			this.fouleSansObs2 = mf3.moteurFoule(prs, espaceSansObs, false);//Creation de la foule
			this.mf4 = new MoteurFoule(this.espaceAvecObs);
			this.fouleAvecObs2 = mf4.moteurFoule(prs, espaceAvecObs, false);//Creation de la foule
			
			this.simulation1 = new Simulation(fouleSansObs1, espaceSansObs);//Simulation avec collision sans obstacle
			this.simulation2 = new Simulation(fouleAvecObs1, espaceAvecObs);//Simulation avec collision avec obstacle
			this.simulation3 = new Simulation(fouleSansObs2, espaceSansObs);//Simulation sans collision sans obstacle
			this.simulation4 = new Simulation(fouleAvecObs2, espaceAvecObs);//Simulation sans collision avec obstacle
			simulation1.finDeSimulation(true);
			simulation2.finDeSimulation(true);
			simulation3.finDeSimulation(false);
			simulation4.finDeSimulation(false);
			tempsACAO.add(simulation2.temps);
			tempsACSO.add(simulation1.temps);
			tempsSCAO.add(simulation4.temps);
			tempsSCSO.add(simulation3.temps);
		}
		
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		int sum4 = 0;
		for (int i = 0; i<nbsim; i++) {
			sum1 += tempsACSO.get(i);
			sum2 += tempsACAO.get(i);
			sum3 += tempsSCSO.get(i);
			sum4 += tempsSCAO.get(i);
		}
		double moy1 = sum1/nbsim;
		double moy2 = sum2/nbsim;
		double moy3 = sum3/nbsim;
		double moy4 = sum4/nbsim;
		
		contentPane1 = new JPanel();
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane1.setLayout(new BorderLayout(0, 0));
		contentPane1.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel text1 = new JLabel("Moyenne du nombre de sortie avec collision et sans obstacle " + moy1);
		contentPane1.add(text1);
		
		contentPane2 = new JPanel();
		contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane2.setLayout(new BorderLayout(0, 0));
		contentPane2.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel text2 = new JLabel("Moyenne du nombre de sortie avec collision et avec obstacle " + moy2);
		contentPane2.add(text2);
		
		contentPane3 = new JPanel();
		contentPane3.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane3.setLayout(new BorderLayout(0, 0));
		contentPane3.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel text3 = new JLabel("Moyenne du nombre de sortie sans collision et sans obstacle " + moy3);
		contentPane3.add(text3);
		
		contentPane4 = new JPanel();
		contentPane4.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane4.setLayout(new BorderLayout(0, 0));
		contentPane4.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel text4 = new JLabel("Moyenne du nombre de sortie sans collision et avec obstacle " + moy4);
		contentPane4.add(text4);
		
		contentPane.add(contentPane1);
		contentPane.add(contentPane2);
		contentPane.add(contentPane3);
		contentPane.add(contentPane4);	
		}

}
