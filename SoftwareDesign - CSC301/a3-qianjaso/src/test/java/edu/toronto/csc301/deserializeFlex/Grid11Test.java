package edu.toronto.csc301.deserializeFlex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.toronto.csc301.AbsFlexDeserializeTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.warehouse.Rack;

public class Grid11Test extends AbsFlexDeserializeTest{
	
	@Override
	protected String getGridFileName() {
		return "grid.11.flex.txt";
	}
	

	@Override
	protected Map<GridCell, Rack> cell2item() {
		Map<GridCell, Rack> cell2item = new HashMap<GridCell, Rack>();
		cell2item.put(GridCell.at(1, 1), new Rack(4));
		cell2item.put(GridCell.at(2, 2), new Rack(5));
		return cell2item;		
	}

	@Override
	protected Set<GridCell> emptyCells() {
		Set<GridCell> cells = new HashSet<GridCell>();
		cells.add(GridCell.at(0, 0));
		cells.add(GridCell.at(1, 0));
		cells.add(GridCell.at(2, 0));
		cells.add(GridCell.at(2, 1));
		return cells;
	}


}
