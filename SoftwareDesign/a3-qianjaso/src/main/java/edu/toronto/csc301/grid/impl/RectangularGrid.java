package edu.toronto.csc301.grid.impl;

import java.util.ArrayList;
import java.util.Iterator;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;

public class RectangularGrid<T> implements IGrid<T>{
	
	int x, y;
	ArrayList<GridCell> cells = new ArrayList<GridCell>();
	GridCell swCell = null;
	
	public RectangularGrid (int x, int y, GridCell cell){
		if (x <= 0 || y <= 0){
			throw new IllegalArgumentException();
		}
		if (cell == null){
			throw new NullPointerException();
		}
		this.x = x;
		this.y = y;
		cells.add(cell);
		swCell = cell;
		for (int xPos = swCell.x; xPos < swCell.x + this.x; xPos++) {
			for (int yPos = swCell.y; yPos < swCell.y + this.y; yPos++) {		
				cells.add(GridCell.at(xPos, yPos));
			}
		}
	}

	public T getItem(GridCell cell) {
		if ((cell.x >= swCell.x + this.x) || (cell.y >= swCell.y + this.y)
		|| (cell.x < swCell.x) || (cell.y < swCell.y)){
			throw new IllegalArgumentException();			
		}
		for (GridCell c : cells){
			if (c.equals(cell)){
				return (T) c.getItem();
			}
		}
		return null;
	}

	public Iterator<GridCell> getGridCells() {
		return cells.iterator();
	}

	public boolean hasCell(GridCell cell) {
		for (GridCell c : cells){
			if (c.equals(cell)){
				return true;
			}
		}
		return false;
	}
}