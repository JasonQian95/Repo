package edu.toronto.csc301.deserializeFlex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.toronto.csc301.AbsFlexDeserializeTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.warehouse.Rack;

public class Grid12Test extends AbsFlexDeserializeTest{
	
	@Override
	protected String getGridFileName() {
		return "grid.12.flex.txt";
	}

	@Override
	protected Map<GridCell, Rack> cell2item() {
		Map<GridCell, Rack> cell2item = new HashMap<GridCell, Rack>();
		cell2item.put(GridCell.at(1, 17), new Rack(7));
		return cell2item;		
	}

	@Override
	protected Set<GridCell> emptyCells() {
		Set<GridCell> cells = new HashSet<GridCell>();
		cells.add(GridCell.at(42, 301));
		cells.add(GridCell.at(-90, 210));
		return cells;
	}


}
