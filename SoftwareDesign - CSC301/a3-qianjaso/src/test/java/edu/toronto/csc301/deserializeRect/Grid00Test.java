package edu.toronto.csc301.deserializeRect;

import java.util.Collections;
import java.util.Map;

import edu.toronto.csc301.AbsRectDeserializeTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.warehouse.Rack;

public class Grid00Test extends AbsRectDeserializeTest{

	@Override
	protected String getGridFileName() {
		return "grid.00.rect.txt";
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
		return GridCell.at(0, 0);
	}

	@Override
	protected Map<GridCell, Rack> cell2item() {
		return Collections.emptyMap();
	}

	

}
