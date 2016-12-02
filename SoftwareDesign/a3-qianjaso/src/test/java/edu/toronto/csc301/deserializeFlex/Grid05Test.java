package edu.toronto.csc301.deserializeFlex;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.toronto.csc301.AbsFlexDeserializeTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.warehouse.Rack;

public class Grid05Test extends AbsFlexDeserializeTest{
	
	@Override
	protected String getGridFileName() {
		return "grid.05.flex.txt";
	}
	
	@Override
	protected Map<GridCell, Rack> cell2item() {
		Map<GridCell, Rack> cell2item = new HashMap<GridCell, Rack>();
		cell2item.put(GridCell.at(-30, 1), new Rack(4));
		return cell2item;		
	}


	@Override
	protected Set<GridCell> emptyCells() {
		return Collections.emptySet();
	}


}
