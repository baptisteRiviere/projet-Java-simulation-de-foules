package io;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AfficheExperience extends JFrame{

	private static final long serialVersionUID = 109456034468145113L;
	private JPanel contentPane; //Def du corps
	private JPanel contentPane1; //Def d'un sous-corps
	private JPanel contentPane2; //Def d'un sous-corps
	private JPanel contentPane3; //Def d'un sous-corps
	private JPanel contentPane4; //Def d'un sous-corps
	private JPanel contentPane5; //Def d'un sous-corps
	
	private JComboBox<Object> choixsalles;
	private JCheckBox collis = new JCheckBox("Avec Collision"); //Creation d'une boite a check
	private JTextField fieldnbpers = new JTextField(); //Creation d'une zone de text
	private JButton check = new JButton("Start"); //Creation d'un bouton
	private JLabel imgsalle;

	
	/**
	 * Creation de la fenetre principale du jeu.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AfficheExperience() {
		super ("Application simulation d'un cas d'urgence de sortie de la foule"); //nom de la fenetre
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image_projet_foule\\cube_projet_foule_miniature.jpg")); //Icon de la fenetre
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //femeture de la fenetre et de l'application avec la croix
		this.setBounds(100, 100, 450, 300); //Dimension de la fenetre
		this.setLocationRelativeTo(null); //Localisation de la fenetre au centre de l'ecran
		
		//Creation du corps de la fenetre
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2,1,10,10)); //Creation d'une grille dans le corps pour l'organisation des composants
		
		//Creation d'un sous-corps de la fenetre
		contentPane1 = new JPanel();
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane1.setLayout(new BorderLayout(0, 0));
		contentPane1.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		
		//Creation d'une liste d'objet pour le JComboBox, des differentes salles
		Object[] salles2 = new Object[]{"SalleTest", "Avion", "SalleComplex", "SalledeClass", "SalleImmeuble","SalleArt","SalleBanque","SalleSaloon"};
		choixsalles = new JComboBox(salles2);
		
		//Image de la salle choisie que l'on fait apparaitre 
		ImageIcon img = new ImageIcon("image_salles\\" + choixsalles.getSelectedItem() + ".png");
		Image image = img.getImage();
		Image imgscale = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(imgscale);
		imgsalle = new JLabel("",icon,JLabel.CENTER);
		
		//Ajout des composants
		contentPane1.add(imgsalle);
		contentPane1.add(choixsalles, BorderLayout.CENTER);
		choixsalles.addActionListener(this::Next); // Permet le changement de l'image relié au nom de la salle
		
		//Creation d'un sous-corps de la fenetre
		contentPane2 = new JPanel();
		contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane2.setLayout(new BorderLayout(0, 0));
		contentPane2.setLayout(new GridLayout(3,1,0,0)); //Creation d'une grille dans le corps pour l'organisation des composants
		
		//Creation d'un sous-corps de la fenetre
		contentPane3 = new JPanel();
		contentPane3.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane3.setLayout(new BorderLayout(0, 0));
		contentPane3.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		contentPane3.add(collis); //Ajout de la boite a check
		
		//Creation d'un sous-corps de la fenetre
		contentPane4 = new JPanel();
		contentPane4.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane4.setLayout(new BorderLayout(0, 0));
		contentPane4.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel Labelnbpers = new JLabel("Nombres de personne dans la salle (entier)"); //Creation d'un text
		contentPane4.add(Labelnbpers); //Ajout du text
		contentPane4.add(fieldnbpers); //Ajout de la zone de text
		fieldnbpers.setPreferredSize(new Dimension(100,25)); //Redimension de la boite de texte
		
		//Creation d'un sous-corps de la fenetre
		contentPane5 = new JPanel();
		contentPane5.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane5.setLayout(new BorderLayout(0, 0));
		contentPane5.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		check.addActionListener(this::Start); //Action du bouton
		contentPane5.add(check); //Ajoute un bouton
		
		//Ajout des sous-corps dans le corps principale
		contentPane.add(contentPane1);
		contentPane.add(contentPane2);
		contentPane2.add(contentPane3);
		contentPane2.add(contentPane4);
		contentPane2.add(contentPane5);
	
		
	}
	
	//Action du bouton quand on l'action
	private void Start(ActionEvent event) {
		boolean erreur = Verificateur(fieldnbpers); // Verifie que la case soit bien remplit
		
		//Renvoie une fenetre d'erreur si les cases sont mal remplis
		if (erreur) {
			AffichageErreur frameErreur = new AffichageErreur(); 
			frameErreur.setVisible(true);
			}
		
		//Renvoie la fenetre de simulation
		else {
			int nbprs = Integer.parseInt(fieldnbpers.getText());
			AffichageSimulation frameSimulation = new AffichageSimulation(nbprs, collis.isSelected(), choixsalles.getSelectedItem());
			frameSimulation.setVisible(true);
			}
	}
	
	//Methode de verification que les cases de saisie de text on bien des int dedans et pas des string ou rien
	@SuppressWarnings({ "exports", "unused" })
	public boolean Verificateur(JTextField JTF) {
		boolean verif = false;
		try {
			int nb = Integer.parseInt(JTF.getText());
		} catch (NumberFormatException e) {
			verif = true;
		}
		return verif;
	}
	
	//Methode permetant le changement de l'image quand on choisi une autre salle dans le JComboBox
	private void Next(ActionEvent event) {
		ImageIcon img = new ImageIcon("image_salles\\" + choixsalles.getSelectedItem() + ".png");
		Image image = img.getImage();
		Image imgscale = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(imgscale);
		imgsalle.setIcon(icon); //Mise à jour de l'image
	}

}
