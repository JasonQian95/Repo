package edu.toronto.csc301;

import static edu.toronto.csc301.util.TestUtil.randomCell;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.util.TestUtil;
import edu.toronto.csc301.warehouse.Rack;

public class FlexGridTest {

	
	@Test
	public void newGridHasNoCells() throws Exception{
		IGrid<Rack> grid = TestUtil.createFlexGrid(Rack.class); 
		assertFalse(grid.hasCell(randomCell()));
	}

	
}
