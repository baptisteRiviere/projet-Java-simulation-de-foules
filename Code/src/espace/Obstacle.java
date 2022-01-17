package espace;

import coordonnees.Coordonnees;

public class Obstacle extends Element {

	private String type;
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Constructeur 1
	public Obstacle(Coordonnees position) {
		super(position);
		this.type = null;
	}
	
	// Constructeur 2
	public Obstacle(Coordonnees position,String type) {
		super(position);
		this.type = type;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Getters
    public String getType() {return this.type;}
    
	//////////////////////////////////////////////////////////////////////////////////////
    // red�finition de la m�thode toString
    @Override
	public String toString() {
        return this.type + ", " + this.position.toString();
    }

	//////////////////////////////////////////////////////////////////////////////////////
    // R��criture hashcode
    @Override
    public int hashCode() {
        return this.getPosition().x*1000 + this.getPosition().y;
    }

    // R��criture equals
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if( ! (obj instanceof Obstacle) ) return false;
        Obstacle other = (Obstacle) obj;
        // On s'appuie essentiellement sur la red�finition de la m�thode toString de Coordonnees
        return this.getPosition().equals(other.getPosition());
    }
    
}