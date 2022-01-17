package io;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class AffichageErreur extends JFrame {

	private static final long serialVersionUID = 8351262258220508422L;
	private JPanel contentPane;

	/**
	 * Creation de la fenetre
	 */
	public AffichageErreur() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image_projet_foule\\LogoErreur.png"));
		this.setTitle("Erreur");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 400, 200);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		ImageIcon img = new ImageIcon("image_projet_foule\\LogoErreur.png");
		Image image = img.getImage();
		Image imgscale = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(imgscale);
		JLabel lblNewLabel = new JLabel("Erreur, Invalide syntaxe", icon, JLabel.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.CENTER);
	}
	
	public AffichageErreur(boolean nada) {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image_projet_foule\\cube_projet_foule_miniature.jpg"));
		this.setTitle("Probleme");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 400, 200);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JLabel lblNewLabel = new JLabel("Des individus sont probablement resté bloqué ");
		contentPane.add(lblNewLabel, BorderLayout.CENTER);
		}

}
