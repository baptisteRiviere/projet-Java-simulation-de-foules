package coordonnees;

public class Coordonnees {
	// On laisse x et y publics car ils ont vocation à être manipulé par beaucoup d'autres classes
	public int x;
	public int y;

	// constructeur
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
	}
    
    // redéfinition de la méthode toString
    @Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

    // Réécriture hashcode
    @Override
    public int hashCode() {
        return this.x*10000 + this.y;
    }
    
    // redefinition de la méthode equals
    @Override
    public boolean equals(Object obj) {
	    if (obj == null) {return false;} //vérifie que l'élément est non null
	    if(!(obj instanceof Coordonnees)) {return false;} //vérifie que l'élément fait bien partie de la classe Coordonnees
	    Coordonnees position = (Coordonnees) obj;
	    return (this.x == position.x && this.y == position.y); 
	}

    
}
