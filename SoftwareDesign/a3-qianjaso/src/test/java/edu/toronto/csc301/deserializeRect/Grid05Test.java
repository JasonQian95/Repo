package edu.toronto.csc301.deserializeRect;

import java.util.HashMap;
import java.util.Map;

import edu.toronto.csc301.AbsRectDeserializeTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.warehouse.Rack;

public class Grid05Test extends AbsRectDeserializeTest{
	
	@Override
	protected String getGridFileName() {
		return "grid.05.rect.txt";
	}
	
	@Override
	protected int width() {
		return 1;
	}
	
	@Override
	protected int height() {
		return 1;
	}	

	@Override
	protected GridCell sw() {
		return GridCell.at(-30,1);
	}

	@Override
	protected Map<GridCell, Rack> cell2item() {
		Map<GridCell, Rack> cell2item = new HashMap<GridCell, Rack>();
		cell2item.put(GridCell.at(-30, 1), new Rack(4));
		return cell2item;		
	}
	
}
