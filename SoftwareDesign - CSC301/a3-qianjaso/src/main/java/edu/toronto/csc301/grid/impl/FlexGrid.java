package edu.toronto.csc301.grid.impl;

import java.util.ArrayList;
import java.util.Iterator;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;

public class FlexGrid<T> implements IGrid<T>{
	
	ArrayList<GridCell> cells = new ArrayList<GridCell>();
	
	public FlexGrid(){
	}

	public T getItem(GridCell cell) {
		for (GridCell c : cells){
			if (c.equals(cell)){
				return (T) c.getItem();
			}
		}
		throw new IllegalArgumentException();
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