package espace;

import coordonnees.Coordonnees;

public abstract class Element {
	
	protected Coordonnees position;
	
	public Element(Coordonnees position) {
		this.position = position;
	}

	public Coordonnees getPosition() {
		return position;
	}

	public void setPosition(Coordonnees position) {
		this.position = position;
	}
	
}
