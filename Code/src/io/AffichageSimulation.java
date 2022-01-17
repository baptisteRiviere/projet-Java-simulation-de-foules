package io;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import coordonnees.Coordonnees;
import espace.Espace;
import espace.MoteurEspace;
import espace.Obstacle;
import espace.Sortie;
import foule.Foule;
import foule.Individu;
import foule.MoteurFoule;
import simulation.Simulation;

public class AffichageSimulation extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = -417418027217610492L;
	private JPanel contentPane;
	private JPanel contentPaneSouth;
	private JPanel contentPaneEast;
	private JButton playstop;
	private JLabel text;
	private JLabel sorties;
	private JButton exits;
	private List<ButtonExit> lexits;
	private Timer timer;
	private GraphiqueSimulation gSim;
	private boolean collis;
	private Simulation sim;
	private boolean plusieursPortes;
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Constructeur principal
	public AffichageSimulation(Simulation sim, boolean collis) {
		super("Simulation de l'experience"); //Mise en place du nom de la fenetre
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image_projet_foule\\cube_projet_foule_miniature.jpg")); //Icon de la fenetre
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Parametre et dimension de la fenetre
		this.setBounds(100, 100, 1000, 700);
		this.setLocationRelativeTo(null);
		this.collis = collis;
		this.sim = sim;
		if (sim.espace.getSorties().size()>1) {//Regarde si il y a plusieur porte ou une seule
			this.plusieursPortes = true;
		}
		else {this.plusieursPortes = false;}
		
		//Creation du corps principal de la fenetre
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		gSim = new GraphiqueSimulation(sim);
		contentPane.add(gSim, BorderLayout.CENTER); // Ajout du graphique au centre
		
		//Creation du corps Sud de la fenetre
		contentPaneSouth = new JPanel();
		contentPaneSouth.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPaneSouth.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		playstop = new JButton("Play"); //Creation d'un bouton
		playstop.addActionListener(this::playAndStop); //Action du bouton
		contentPaneSouth.add(playstop); // Ajout du bouton dans le corps
		
		text = new JLabel(); //Creation d'un texte
		text.setText("Temps : "+ gSim.simulation.temps); //Modification du texte
		contentPaneSouth.add(text); //Ajout du texte a la fenetre
		
		lexits = new ArrayList<ButtonExit>(); //Creation d'un liste de bouton sortie
		int nbSortie = sim.espace.getSorties().size();
		
		//Creation du corps Est de la fenetre
		contentPaneEast = new JPanel();
		contentPaneEast.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPaneEast.setLayout(new GridLayout(nbSortie+2,1,0,0)); //Creation d'une grille dans le corps pour l'organisation des composants
		
		//Creation d'un sous-corps
		JPanel titleContentPane = new JPanel();
		sorties = new JLabel("Sorties :"); //Creation d'un texte
		titleContentPane.add(sorties); // Ajout du texte
		contentPaneEast.add(titleContentPane); //Ajout du sous-corps
		
		if (plusieursPortes) {
			for (int i = 1 ; i<=nbSortie; i++) {
				JPanel sousContentPane = new JPanel(); //Creation d'un sous-corps
				sousContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				sousContentPane.setLayout(new BorderLayout(0, 0));
				sousContentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
				ButtonExit etat = new ButtonExit("S "+i+" ouverte", sim.espace.getSorties().get(i-1), sim); //creation des boutons pour chaque sorties
				lexits.add(etat);//Ajout des boutons a la liste
			
				sousContentPane.add(etat.getBut()); //Ajout du bouton au sous-corps
				contentPaneEast.add(sousContentPane); //Ajout du sous-corps
			}
		 
			//Creation d'un sous-corps
			JPanel endContentPane = new JPanel();
			exits = new JButton("Fermer toutes les sorties"); //Creation d'un bouton
			exits.addActionListener(this::sCloseOpen); // Action du bouton
			endContentPane.add(exits); //Ajout du bouton
			contentPaneEast.add(endContentPane); //Ajot du sous-corps
		}
		else {
			//Creation d'un sous-corps
			JPanel endContentPane = new JPanel();
			exits = new JButton("Fermer la porte"); //Creation d'un bouton
			exits.addActionListener(this::sOpenClose); // Action du bouton
			endContentPane.add(exits); //Ajout du bouton
			contentPaneEast.add(endContentPane); //Ajot du sous-corps
		}
		
		//Ajout des deux corps Sud et Est dans le corps princepale
		contentPane.add(contentPaneSouth, BorderLayout.SOUTH);
		contentPane.add(contentPaneEast, BorderLayout.EAST);
		timer = new Timer(500,this); //Creation du timer
		
	}
	//////////////////////////////////////////////////////////////////////////////////////
	// 2e constructeur prenant seulement un nombre de personne et un nom d'espace pour créer la simulation (pour le choix de salle)
	public AffichageSimulation(int prs, boolean collis, Object str) {
		this(getSimAssociee(prs,str),collis);
	}
	
	private static Simulation getSimAssociee(int prs, Object str) {
		Espace espace = MoteurEspace.getEspaceAssocie(str); //Recuperation de l'espace choisi
		MoteurFoule mf = new MoteurFoule(espace); 
		Foule foule = new Foule();
		if (str.equals("Avion")) {
			foule = mf.moteurFouleAvion(prs, espace);
		}
		else {if (str.equals("SalledeClass")) {
			foule = mf.moteurFouleClass(prs, espace);
			}
			else {
				foule = mf.moteurFoule(prs, espace, false); //Creation de la foule
			}
		}
		Simulation sim = new Simulation(foule, espace);
		return sim;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////	
	// 3e constructeur prenant la longueur et la largeur pour créer un espace (plus la présence ou non d'un obstacle)
	// et prenant un nombre de personnes pour créer la foule
	public AffichageSimulation(int longueur, int largeur, int prs, boolean collis, boolean obstacleDevantSortie) {
		this(getSimAssociee(longueur,largeur,prs,obstacleDevantSortie),collis);
	}
	
	
	private static Simulation getSimAssociee(int longeur, int largeur, int prs, boolean obstacleDevantSortie) {
		Espace espace = MoteurEspace.createRectangleCreux(new Coordonnees(0, 0), longeur, largeur); //Creation de l'espace
		int cooExit = longeur/2;
		Sortie exit = new Sortie(new Coordonnees(cooExit, 0)); // Creation de la sortie au centre de la longeur de la salle
		espace.addSortie(exit); // Ajout de la sortie
		if(obstacleDevantSortie) { // Mise en place d'un obstacle devant la porte la taille de l'obstacle depend de la taille de la salle pour ne pas avoir de probleme d'obstacle plus gros que la salle
			if (longeur>=5 & longeur<11 & largeur>=5) {
				espace.addObstacle(new Obstacle(new Coordonnees(cooExit, 2), "mur"));
			}
			if (longeur>=11 & largeur>=9) {
				for (Obstacle obs : MoteurEspace.createRectanglePlein(new Coordonnees(cooExit-1, 2), 3, 3)) {espace.addObstacle(obs);}
			}
		}
		MoteurFoule mf = new MoteurFoule(espace);
		Foule foule = mf.moteurFoule(prs, espace, false);//Creation de la foule
		Simulation sim = new Simulation(foule, espace);//Creation de la simulation
		return sim;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	// 4e constructeur prenant en paramètre un espace pour le visualiser
	public AffichageSimulation(Espace espace) {
		this(getSimAssociee(espace),true);
	}
	
	private static Simulation getSimAssociee(Espace espace) {
		Foule foule = new Foule();
		Simulation sim = new Simulation(foule, espace);
		return sim;
	}	
	
	//////////////////////////////////////////////////////////////////////////////////////
	//Action de la fenetre quand le timer se met en route 
	@Override
	public void actionPerformed(@SuppressWarnings("exports") ActionEvent e) {
		gSim.simulation.etape(collis); //fait une etape de la simulation
		text.setText("Temps : "+ gSim.simulation.temps); //change le temps
		if (plusieursPortes) { //Verification des boutons sorties quand on est dans une simulation pour qu'ils concordent
			verificationSorties(exits, lexits);
		}
		if (sim.foule.getRepartition().size()==666 & sim.temps == 11) { //Mise en place d'un easter-egg invocation du diable
			timer.stop();
			this.dispose();
			EasterEgg easteregg = new EasterEgg();
			easteregg.setVisible(true);
		}
		if (sim.foule.getRepartition().size()==42 & verifSorties() & verifPersonnes()) {//Mise en place d'un easter-egg la reponse a la vie
			timer.stop();
			playstop.setText("Play");
			EasterEgg easteregg = new EasterEgg(true);
			easteregg.setVisible(true);
		}
		repaint(); //refait le graphique comme on a fait une etape ce qui permet de visualiser l'avancé des individus
		if (gSim.foule.individusSortis()) { //Test si tout les individus sont sorties pour mettre fin à la simulation
			timer.stop(); //Arret du timer
			playstop.setText("End");
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////
	//Methode d'action du bouton playstop permettant la mise en marche de la simulation et l'arret
	@SuppressWarnings("exports")
	public void playAndStop(ActionEvent event) {
		if (playstop.getText().equals("Stop")) {
			timer.stop();
			playstop.setText("Play");
		}
		else {
			if (playstop.getText().equals("Play")) {
			timer.start();
			playstop.setText("Stop");
			}
			else {
				if (playstop.getText().equals("End")) {
					this.dispose();
				}
			}
		}
	}
	

	//////////////////////////////////////////////////////////////////////////////////////
	// Methode du bouton exits pour fermer toutes les portes ou les ouvrir avec modification des boutons individuels des portes
	// Quand on fait une simulation avec choix de la salle
	@SuppressWarnings("exports")
	public void sCloseOpen(ActionEvent event) {
		if(exits.getText().equals("Fermer toutes les sorties")) {
			sim.fermerToutesSorties();
			exits.setText("Ouvrir toutes les sorties");
			for (ButtonExit butexit1 : lexits) { //Change le nom des boutons individuels des portes pour changer leur fonctionalité
				String m1 = butexit1.getButText();
				String txt1 = m1.substring(0, 4);
				butexit1.setButText(txt1 + "ferme");
			}
		}
		else {
			if(exits.getText().equals("Ouvrir toutes les sorties")) {
				sim.ouvrirToutesSorties();;
				exits.setText("Fermer toutes les sorties");
				for (ButtonExit butexit2 : lexits) { //Change le nom des boutons individuels des portes pour changer leur fonctionalité
					String m2 = butexit2.getButText();
					String txt2 = m2.substring(0, 4);
					butexit2.setButText(txt2 + "ouverte");
				}
			}
		}
	}
	
	// Action du bouton exits pour la simulation de l'experience quand l'on definit la salle
	private void sOpenClose(ActionEvent event) {
		if (exits.getText().equals("Fermer la porte")) {
			sim.fermerToutesSorties();
			exits.setText("Ouvrir la porte");
		}
		else {
			if (exits.getText().equals("Ouvrir la porte")) {
				sim.ouvrirToutesSorties();
				exits.setText("Fermer la porte");
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	//Methode qui concorde les boutons individuels des portes et le bouton pour tout fermer ou tout ouvrir
	private void verificationSorties(JButton exits2, List<ButtonExit> lexits2) {
		boolean open = true;
		boolean close = true;
		for (ButtonExit ext : lexits2) {
			if (ext.getExit().getOuvert()) { //Test si toutes les sorties sont fermees
				close = false;
			}
			if (!(ext.getExit().getOuvert())) { //Test si toutes les sorties sont ouvertes
				open = false;
			}
		}
		if (exits2.getText().equals("Fermer toutes les sorties")) { // regarde si le bouton exits ferme les portes
			if (close) { // si les portes sont toutes fermees
				exits2.setText("Ouvrir toutes les sorties"); //Change le texte du bouton pour changer son action
			}
		}
		else {
			if (exits2.getText().equals("Ouvrir toutes les sorties")) { // regarde si le bouton exits ouvre les portes
				if (open) { // si les portes sont toutes ouvertes
					exits2.setText("Fermer toutes les sorties"); //Change le texte du bouton pour changer son action
				} 
			}
		}
	}
		
	//////////////////////////////////////////////////////////////////////////////////////
	// Methode qui verifie que toutes les sorties sont fermees
	private boolean verifSorties() {
		boolean close = true;
		if (this.plusieursPortes) {
			for (ButtonExit ext : this.lexits) {
				if (ext.getExit().getOuvert()) { //Test si toutes les sorties sont fermees
					close = false;
				}
			}
		}
		else if (exits.getText().equals("Fermer la porte")) {
			close = false;
		}
		return close;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	//Methode qui verifie que les personnes sont bien dans la salle
	private boolean verifPersonnes() {
		boolean prs = true;
		for (Individu indiv : sim.foule.getRepartition()) {
			if (!(indiv.getInterieur())) {
				prs = false;
			}
		}
		return prs;
	}
}
