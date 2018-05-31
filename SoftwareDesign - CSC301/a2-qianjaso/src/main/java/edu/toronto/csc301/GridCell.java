package edu.toronto.csc301;



public class GridCell {
	

	/**
	 * Static factory method.
	 */
	public static GridCell at(int x, int y){
		return new GridCell(x, y);
	}
	
	
	//-------------------------------------------------------------------------
	

	public final int x;
	public final int y;
	
	private GridCell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof GridCell && 
			this.x == ((GridCell) other).x &&
			this.y == ((GridCell) other).y;
	}
	
	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}

}
