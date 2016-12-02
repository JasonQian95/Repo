package edu.toronto.csc301.deserializeRect;

import java.util.Collections;
import java.util.Map;

import edu.toronto.csc301.AbsRectDeserializeTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.warehouse.Rack;

public class Grid03Test extends AbsRectDeserializeTest{
	
	@Override
	protected String getGridFileName() {
		return "grid.03.rect.txt";
	}
	
	@Override
	protected int width() {
		return 5;
	}
	
	@Override
	protected int height() {
		return 10;
	}	

	@Override
	protected GridCell sw() {
		return GridCell.at(-4,2);
	}

	@Override
	protected Map<GridCell, Rack> cell2item() {
		return Collections.emptyMap();
	}

}
