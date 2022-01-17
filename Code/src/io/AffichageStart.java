package io;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class AffichageStart extends JFrame {

	private static final long serialVersionUID = 8128827350357459209L;
	private JPanel contentPane; //Def du corps
	private JPanel contentPane2; //Def d'un sous-corps
	private JButton btnSim = new JButton("Simulation"); //Creation d'un bouton
	private JButton btnExp = new JButton("Experience"); //Creation d'un bouton
	private JButton btnStat = new JButton("Experiences Comparées"); //Creation d'un bouton


	/**
	 * Creation de la fenetre principale du jeu.
	 */
	public AffichageStart() {
		super ("Application simulation d'un cas d'urgence de sortie de la foule"); //nom de la fenetre
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image_projet_foule\\cube_projet_foule_miniature.jpg")); //Icon de la fenetre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //femeture de la fenetre et de l'application avec la croix
		this.setBounds(100, 100, 450, 300); //Dimension de la fenetre
		this.setLocationRelativeTo(null); //Localisation de la fenetre au centre de l'ecran
		
		//Creation du corps de la fenetre
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//Ajout d'une image dans le corps de la fenetre avec une redimenssion de l'image
		ImageIcon img = new ImageIcon("image_projet_foule\\cube_projet_foule_miniature.jpg");
		Image image = img.getImage();
		Image imgscale = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(imgscale);
		JLabel Label = new JLabel("",icon,JLabel.CENTER);
		contentPane.add(Label, BorderLayout.NORTH);
		
		//Creation d'un sous-corps de la fenetre
		contentPane2 = new JPanel();
		contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane2.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		btnSim.addActionListener(this::AfficheExp); //Action du bouton
		btnExp.addActionListener(this::Manipulation); //Action du bouton
		btnStat.addActionListener(this::AffichePStat); //Action du bouton
		contentPane2.add(btnSim); //Ajoute un bouton
		contentPane2.add(btnExp); //Ajoute un bouton
		contentPane2.add(btnStat); //Ajoute un bouton
		contentPane.add(contentPane2, BorderLayout.CENTER);//Ajout du sous-corps au centre du corps
	}

	//methode qui fait l'action du bouton Simulation envoie la fenetre suivante
	private void AfficheExp(ActionEvent event) {
		AfficheExperience affExp = new AfficheExperience();
		affExp.setVisible(true);
	}
	
	//methode qui fait l'action du bouton Experience envoie la fenetre suivante
	private void Manipulation(ActionEvent event) {
		AffichageManipulation affMan = new AffichageManipulation();
		affMan.setVisible(true);
	}
	
	//methode qui fait l'action du bouton Experience statistique envoie la fenetre suivante
	private void AffichePStat(ActionEvent event) {
		AffichagePStat affStat = new AffichagePStat();
		affStat.setVisible(true);
	}

}
