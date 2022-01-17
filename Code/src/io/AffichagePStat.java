package io;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AffichagePStat extends JFrame{

	private static final long serialVersionUID = 7609435274677796965L;
	private JPanel contentPane; //Def du corps
	private JPanel contentPane1; //Def d'un sous-corps
	private JPanel contentPane2; //Def d'un sous-corps
	private JPanel contentPane3; //Def d'un sous-corps
	private JPanel contentPane4; //Def d'un sous-corps
	private JPanel contentPane5; //Def d'un sous-corps
	private JPanel contentPane6; //Def d'un sous-corps
	
	private JTextField fieldnbpers = new JTextField(); //Creation d'une zone de texte
	private JTextField fieldlong = new JTextField(); //Creation d'une zone de texte
	private JTextField fieldlarg = new JTextField(); //Creation d'une zone de texte
	private JTextField fieldnbsim = new JTextField(); //Creation d'une zone de texte
	private JButton check = new JButton("Start"); //Creation d'un bouton
	
	//Constructeur de la fenetre
	public AffichagePStat() {
		super ("Application simulation d'un cas d'urgence de sortie de la foule"); //nom de la fenetre
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image_projet_foule\\cube_projet_foule_miniature.jpg")); //Icon de la fenetre
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //femeture de la fenetre et de l'application avec la croix
		this.setBounds(100, 100, 450, 500); //Dimension de la fenetre
		this.setLocationRelativeTo(null); //Localisation de la fenetre au centre de l'ecran
		
		//Creation du corps de la fenetre
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(6,1,10,10)); //Creation d'une grille dans le corps pour l'organisation des composants
		
		//Creation d'un sous-corps de la fenetre
		contentPane1 = new JPanel();
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane1.setLayout(new BorderLayout(0, 0));
		contentPane1.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel Labeltitle = new JLabel("Parametres de l'experience");
		contentPane1.add(Labeltitle);
		
		//Creation d'un sous-corps de la fenetre
		contentPane2 = new JPanel();
		contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane2.setLayout(new BorderLayout(0, 0));
		contentPane2.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel Labellong = new JLabel("Taille en longueur de la salle (entier)"); //Creation d'un texte
		contentPane2.add(Labellong); //Ajout du texte
		contentPane2.add(fieldlong); //Ajout de la zone de texte
		fieldlong.setPreferredSize(new Dimension(100,25)); //Redimension de la boite de texte
				
		//Creation d'un sous-corps de la fenetre
		contentPane3 = new JPanel();
		contentPane3.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane3.setLayout(new BorderLayout(0, 0));
		contentPane3.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel Labellarg = new JLabel("Taille en largeur de la salle (entier)"); //Creation d'un texte
		contentPane3.add(Labellarg); //Ajout du texte
		contentPane3.add(fieldlarg); //Ajout de la zone de texte
		fieldlarg.setPreferredSize(new Dimension(100,25)); //Redimension de la boite de texte
		
		//Creation d'un sous-corps de la fenetre
		contentPane4 = new JPanel();
		contentPane4.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane4.setLayout(new BorderLayout(0, 0));
		contentPane4.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel Labelnbpers = new JLabel("Nombres de personne dans la salle (entier)"); //Creation d'un texte
		contentPane4.add(Labelnbpers); //Ajout du texte
		contentPane4.add(fieldnbpers); //Ajout de la zone de texte
		fieldnbpers.setPreferredSize(new Dimension(100,25)); //Redimension de la boite de texte
		
		//Creation d'un sous-corps de la fenetre
		contentPane5 = new JPanel();
		contentPane5.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane5.setLayout(new BorderLayout(0, 0));
		contentPane5.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		JLabel Labelnbsim = new JLabel("Nombres de simulation (entier)"); //Creation d'un texte
		contentPane5.add(Labelnbsim); //Ajout du texte
		contentPane5.add(fieldnbsim); //Ajout de la zone de texte
		fieldnbsim.setPreferredSize(new Dimension(100,25)); //Redimension de la boite de texte
		
		//Creation d'un sous-corps de la fenetre
		contentPane6 = new JPanel();
		contentPane6.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane6.setLayout(new BorderLayout(0, 0));
		contentPane6.setLayout(new FlowLayout()); //Organisation des compasants du sous-corps, centré l'un à coté de l'autre
		check.addActionListener(this::Start); //Action du bouton
		contentPane6.add(check); //Ajoute un bouton
		
		//Ajout des sous-corps pour former la fenetre
		contentPane.add(contentPane1);
		contentPane.add(contentPane2);
		contentPane.add(contentPane3);
		contentPane.add(contentPane4);
		contentPane.add(contentPane5);
		contentPane.add(contentPane6);
		
	}
	
	//Action du bouton quand on l'action
	private void Start(ActionEvent event) {
		boolean erreur1 = Verificateur(fieldlong); // Verifie que la case soit bien remplit
		boolean erreur2 = Verificateur(fieldlarg); // Verifie que la case soit bien remplit
		boolean erreur3 = Verificateur(fieldnbpers); // Verifie que la case soit bien remplit
		boolean erreur4 = Verificateur(fieldnbsim); // Verifie que la case soit bien remplit
		
		//Renvoie une fenetre d'erreur si les cases sont mal remplis
		if (erreur1 || erreur2 || erreur3 || erreur4 ) {
			AffichageErreur frameErreur = new AffichageErreur(); 
			frameErreur.setVisible(true);
			}
		
		//Renvoie la fenetre de simulation
		else {
			int nbprs = Integer.parseInt(fieldnbpers.getText());
			int longeur = Integer.parseInt(fieldlong.getText());
			int largeur = Integer.parseInt(fieldlarg.getText());
			int nbsim = Integer.parseInt(fieldnbsim.getText());
			AffichageResultat frameSimulation = new AffichageResultat(longeur, largeur, nbprs, nbsim);
			frameSimulation.setVisible(true);
			}
	}
	
	//Methode de verification que les cases de saisie de texte on bien des int dedans et pas des string ou rien
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
}

