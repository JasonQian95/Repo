package edu.toronto.csc301.grid;

import edu.toronto.csc301.warehouse.Rack;

/**
 * Convenience class - Immutable pair of x and y coordinates.
 * 
 * @author Joey Freund
 */
public class GridCell {	
	
	
	/**
	 * Static factory method, for convenience.
	 * @return A grid-cell instance with the given XY-coordinates.
	 */
	public static GridCell at(int x, int y){
		return new GridCell(x, y);
	}
	
	
	// ------------------------------------------------------------------------
	

	public final int x;
	public final int y;
	
	private GridCell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	// ------------------------------------------------------------------------
	
	
	@Override
	public boolean equals(Object other) {
		return other instanceof GridCell && 
			this.x == ((GridCell) other).x &&
			this.y == ((GridCell) other).y;
	}
	
	
	@Override
	public int hashCode() {
		return (int) (Math.pow(2, x) * Math.pow(3, y));
	}
	
	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}
	
	Rack item = null;
	public void addItem(Rack item){
		this.item = item;
	}
	public Rack getItem(){
		return this.item;
	}

}
