package bots;

import gameengine.Coordinates;

//Similar to Coordinates, except with a parent node.
public class Positions {
	 private int col,row;
	 private Positions parent;
	 private boolean explored=false;

	    public Positions(int row, int col, Positions parent) {
	        this.col = col;
	        this.row = row;
	        this.parent = parent;
	    }

	    public Positions(Coordinates coordinates, Positions parent) {
	        col = coordinates.getCol ();
	        row = coordinates.getRow ();
	    }

	    void add(Positions coordinates) {
	        col = col + coordinates.getCol ();
	        row = row + coordinates.getRow ();
	    }
	    
	    public Coordinates getCoordinates() {
	    	Coordinates res = new Coordinates(row, col);
	    	return res;
	    }

	    public int getRow() {
	        return row;
	    }

	    public int getCol() {
	        return col;
	    }
	    
	    public Positions getParent() {
	    	return parent;
	    }

	    public boolean isExplored() {
	    	return explored;
	    }
	    
	    public void visited() {
	    	this.explored = true;
	    }
	    
	    public String toString() {
	        return "(" + col + "," + row + ")";
	    }
}
