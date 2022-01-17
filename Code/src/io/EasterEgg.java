package io;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class EasterEgg extends JFrame implements ActionListener{

	private static final long serialVersionUID = 2415996747497691638L;
	private JPanel contentPane;
	private JLabel label;
	private Timer timer;
	
	public EasterEgg() {
		super("Erreur"); //Mise en place du nom de la fenetre
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image_projet_foule\\LogoErreur.png")); //Icon de la fenetre
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Parametre et dimension de la fenetre
		this.setBounds(100, 100, 1200, 800);
		this.setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		ImageIcon img = new ImageIcon("image_projet_foule\\diable.jpg");
		Image image = img.getImage();
		Image imgscale = image.getScaledInstance(700, 700, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(imgscale);
		this.label = new JLabel("Vous avez invoqué le diable",icon,JLabel.CENTER);
		
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		contentPane.add(label);
		
		this.timer = new Timer(10000, this);
		timer.start();
	}
	
	public EasterEgg(boolean t) {
		super("Bravo"); //Mise en place du nom de la fenetre
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image_projet_foule\\cube_projet_foule_miniature.jpg")); //Icon de la fenetre
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Parametre et dimension de la fenetre
		this.setBounds(100, 100, 1200, 800);
		this.setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		ImageIcon img = new ImageIcon("image_projet_foule\\savoir.jpg");
		Image image = img.getImage();
		Image imgscale = image.getScaledInstance(1000, 600, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(imgscale);
		this.label = new JLabel("Vous avez trouvé la réponse à la vie",icon,JLabel.CENTER);
		
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		contentPane.add(label);
	}
	
	
	@SuppressWarnings("exports")
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.stop();
		this.dispose();
		
	}
	

}
