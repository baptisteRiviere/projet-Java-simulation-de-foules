package graphe;

import coordonnees.Coordonnees;

public class Vertex {
	
    final private String id;
    final private Coordonnees position;
    boolean target;

	//////////////////////////////////////////////////////////////////////////////////////
    // Constructeur 1
    public Vertex(String id,Coordonnees position) {
        this.id = id;
        this.position = position;
        this.target = false;
    }

    // Constructeur 2
    public Vertex(String id,Coordonnees position,boolean target) {
        this.id = id;
        this.position = position;
        this.target = target;
    }

    // Constructeur 3
    public Vertex(String id) {
        this.id = id;
        this.position = new Coordonnees(0,0);
        this.target = false;
    }

	//////////////////////////////////////////////////////////////////////////////////////
    // Getters
    public String getId() { return id; }
    public Coordonnees getPosition() { return this.position; }
    public boolean getTarget() {return this.target; }

	//////////////////////////////////////////////////////////////////////////////////////
    // Setter
    public void setTarget(boolean target) {this.target = target;}

	//////////////////////////////////////////////////////////////////////////////////////
    // Réécriture hashcode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    // Réécriture equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Redéfinition
    @Override
    public String toString() {
        return this.id + ", " + this.position;
    }

}