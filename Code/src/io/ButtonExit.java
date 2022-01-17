package io;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import espace.Sortie;
import simulation.Simulation;

public class ButtonExit extends JPanel{

	private static final long serialVersionUID = 9110988772579736145L;
	private JButton but;
	private Sortie exit;
	private Simulation sim;
	
	//Constructeur
	public ButtonExit(String name,Sortie exit, Simulation sim) {
		this.but = new JButton(name);
		this.exit = exit;
		this.sim = sim;
		this.but.addActionListener(this::sortieOF); //Action du bouton
	}

	//Getters and Setters
	@SuppressWarnings("exports")
	public JButton getBut() {return but;}
	@SuppressWarnings("exports")
	public void setBut(JButton but) {this.but = but;}
	public Sortie getExit() {return exit;}
	public void setExit(Sortie exit) {this.exit = exit;}
	
	public String getButText() {return this.but.getText();}
	public void setButText(String name) {this.but.setText(name);}
	
	//Methode qui permet de ouvrir ou fermer la porte choisie avec changement de l'action en changeant le texte du bouton
	@SuppressWarnings("exports")
	public void sortieOF(ActionEvent event) {
		String m = but.getText();
		int taille = m.length();
		String nb = m.substring(2, 3);
		int nbint = Integer.parseInt(nb);
		String sEtat = m.substring(4, taille);
		 if (sEtat.equals("ouverte")) {
			 but.setText("S "+ nb +" ferme");//change le texte du bouton pour changer son action
			 sim.fermerSortie(sim.espace.getSorties().get(nbint-1));//ferme la sortie
		 }
		 else {
			 if (sEtat.equals("ferme")) {
				 but.setText("S "+ nb +" ouverte");//change le texte du bouton pour changer son action
				 sim.ouvrirSortie(sim.espace.getSorties().get(nbint-1));//ouvre la sortie
			 }
		 }
	}
}
