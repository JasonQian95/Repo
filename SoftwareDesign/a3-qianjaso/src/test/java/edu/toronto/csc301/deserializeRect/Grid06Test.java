package edu.toronto.csc301.deserializeRect;

import java.util.HashMap;
import java.util.Map;

import edu.toronto.csc301.AbsRectDeserializeTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.warehouse.Rack;

public class Grid06Test extends AbsRectDeserializeTest{
	
	@Override
	protected String getGridFileName() {
		return "grid.06.rect.txt";
	}
	
	@Override
	protected int width() {
		return 3;
	}
	
	@Override
	protected int height() {
		return 2;
	}	

	@Override
	protected GridCell sw() {
		return GridCell.at(0,0);
	}

	@Override
	protected Map<GridCell, Rack> cell2item() {
		Map<GridCell, Rack> cell2item = new HashMap<GridCell, Rack>();
		cell2item.put(GridCell.at(0, 0), new Rack(3));
		cell2item.put(GridCell.at(2, 1), new Rack(5));
		return cell2item;		
	}

}
