package edu.toronto.csc301.deserializeRect;

import java.util.Collections;
import java.util.Map;

import edu.toronto.csc301.AbsRectDeserializeTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.warehouse.Rack;

public class Grid02Test extends AbsRectDeserializeTest{
	
	@Override
	protected String getGridFileName() {
		return "grid.02.rect.txt";
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
		return Collections.emptyMap();
	}

}
