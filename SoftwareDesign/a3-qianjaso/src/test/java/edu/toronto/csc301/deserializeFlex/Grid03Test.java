package edu.toronto.csc301.deserializeFlex;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.toronto.csc301.AbsFlexDeserializeTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.warehouse.Rack;

public class Grid03Test extends AbsFlexDeserializeTest{
	
	@Override
	protected String getGridFileName() {
		return "grid.03.flex.txt";
	}
	

	@Override
	protected Map<GridCell, Rack> cell2item() {
		return Collections.emptyMap();
	}


	@Override
	protected Set<GridCell> emptyCells() {
		Set<GridCell> cells = new HashSet<GridCell>();
		for (int x = -4; x < 1; x++) {
			for (int y = 2; y < 12; y++) {
				cells.add(GridCell.at(x,y));
			}
		}
		return cells;
	}


}
