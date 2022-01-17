package io;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import espace.Espace;
import espace.Obstacle;
import espace.Sortie;
import foule.Foule;
import foule.Individu;
import foule.MoteurFoule;
import simulation.Simulation;

public class GraphiqueSimulation extends JPanel {

	private static final long serialVersionUID = 8306746899568864740L;
	public Espace espace;
	public MoteurFoule mf;
	public Foule foule;
	public Simulation simulation;
	private int nbeasteregg;
	
	// Constructeur 1 pour visualiser une simulation
	public GraphiqueSimulation(Simulation simulation) {	
		this.simulation = simulation;
		this.espace = simulation.espace;
		this.foule = simulation.foule;
		this.nbeasteregg = simulation.foule.getRepartition().size();
	}
	
	//Creation du graphique de l'espace et de la foule sur l'interface graphique
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//mise en place des obstacles
		for (Obstacle obs : espace.getObstacles()) {
			if (obs.getType().equals("mur")) {
				g.setColor(Color.BLACK);
				g.fillRect(obs.getPosition().x*10+10, obs.getPosition().y*10+10, 10 ,10);
			}
			if (obs.getType().equals("table")) {
				g.setColor(Color.DARK_GRAY);
				g.fillRect(obs.getPosition().x*10+10, obs.getPosition().y*10+10, 10 ,10);
			}
			if (obs.getType().equals("chaise")) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(obs.getPosition().x*10+10, obs.getPosition().y*10+10, 10 ,10);
			}
		}
		
		//mise en place des sorties
		for (Sortie exit : espace.getSorties()) {
			if (exit.getOuvert()) {
				g.setColor(Color.YELLOW);
				g.fillRect(exit.getPosition().x*10+10, exit.getPosition().y*10+10, 10 ,10);
			}
			else {g.setColor(Color.ORANGE);
				g.fillRect(exit.getPosition().x*10+10, exit.getPosition().y*10+10, 10 ,10);
			}
		}
		
		if (nbeasteregg == 666 & simulation.temps>2) {//Mise en place d'un easter-egg dispariton de la foule
			for (Individu indiv : foule.getRepartition()) {
				g.setColor(new Color(0,0,0,0));
				g.fillOval(indiv.getPosition().x*10+10, indiv.getPosition().y*10+10, 10, 10);
			}
		}
		else {
			//mise en place des individus
			for (Individu indiv : foule.getRepartition()) {
				if(indiv.getInterieur()) { //Pour afficher que les individus dans la salle
					if(indiv.getATerre()) {
						g.setColor(Color.BLUE);
						g.fillOval(indiv.getPosition().x*10+10, indiv.getPosition().y*10+10, 10, 10);
					}
					else {g.setColor(Color.RED);
						g.fillOval(indiv.getPosition().x*10+10, indiv.getPosition().y*10+10, 10, 10);
					}
				}
			}
		}
		
		if (nbeasteregg == 1968) { 	//Mise en place d'un easter-egg si on met 1968 personnes dans une salle les personnes deviennent multicolor
			//Pouquoi 1968 ? C'est la date du passage de la télévision en couleur
			ArrayList<Color> color = new ArrayList<Color>();
			color.add(Color.BLACK);
			color.add(Color.BLUE);
			color.add(Color.CYAN);
			color.add(Color.DARK_GRAY);
			color.add(Color.GRAY);
			color.add(Color.GREEN);
			color.add(Color.LIGHT_GRAY);
			color.add(Color.MAGENTA);
			color.add(Color.ORANGE);
			color.add(Color.PINK);
			color.add(Color.RED);
			color.add(Color.WHITE);
			color.add(Color.YELLOW);
			for (Individu indiv : foule.getRepartition()) {
				Random random = new Random();
				g.setColor(color.get(random.nextInt(13)));
				g.fillOval(indiv.getPosition().x*10+10, indiv.getPosition().y*10+10, 10, 10);
			}
		}
		
		//Legende
		g.setColor(Color.BLACK);
		g.fillRect(this.getWidth()-100, 10, 10, 10);
		g.drawString("Mur", this.getWidth()-85, 20);
		g.drawString("Table", this.getWidth()-85, 40);
		g.drawString("Chaise", this.getWidth()-85, 60);
		g.drawString("Sortie ouverte", this.getWidth()-85, 80);
		g.drawString("Sortie ferme", this.getWidth()-85, 100);
		g.drawString("Individu a terre", this.getWidth()-85, 120);
		g.drawString("Individu debout", this.getWidth()-85, 140);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.getWidth()-100, 30, 10, 10);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(this.getWidth()-100, 50, 10, 10);
		g.setColor(Color.YELLOW);
		g.fillRect(this.getWidth()-100, 70, 10, 10);
		g.setColor(Color.ORANGE);
		g.fillRect(this.getWidth()-100, 90, 10, 10);
		g.setColor(Color.RED);
		g.fillRect(this.getWidth()-100, 130, 10, 10);
		g.setColor(Color.BLUE);
		g.fillRect(this.getWidth()-100, 110, 10, 10);
		
	}
}
