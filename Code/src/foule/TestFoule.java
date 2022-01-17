package foule;


import espace.Espace;
import espace.MoteurEspace;

public class TestFoule {
	
	public static void testFoule() {
		
		Espace espace = MoteurEspace.moteurEspace();
		MoteurFoule mf = new MoteurFoule(espace);
		Foule foule = mf.moteurFoule(50,espace,true);
		
		System.out.println(foule);
		System.out.println("\n");
		System.out.println(foule.getRepartition().get(50-1).getChemin());
		
	}
}
