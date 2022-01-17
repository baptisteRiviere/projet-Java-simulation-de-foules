package coordonnees;

public class Coordonnees {
	// On laisse x et y publics car ils ont vocation � �tre manipul� par beaucoup d'autres classes
	public int x;
	public int y;

	// constructeur
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
	}
    
    // red�finition de la m�thode toString
    @Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

    // R��criture hashcode
    @Override
    public int hashCode() {
        return this.x*10000 + this.y;
    }
    
    // redefinition de la m�thode equals
    @Override
    public boolean equals(Object obj) {
	    if (obj == null) {return false;} //v�rifie que l'�l�ment est non null
	    if(!(obj instanceof Coordonnees)) {return false;} //v�rifie que l'�l�ment fait bien partie de la classe Coordonnees
	    Coordonnees position = (Coordonnees) obj;
	    return (this.x == position.x && this.y == position.y); 
	}

    
}
