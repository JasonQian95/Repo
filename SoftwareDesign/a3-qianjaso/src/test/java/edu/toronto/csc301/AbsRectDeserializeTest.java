package edu.toronto.csc301;

import static edu.toronto.csc301.util.TestUtil.createRectSerializerDeserializer;
import static edu.toronto.csc301.util.TestUtil.randomCellOutsideOfRectangle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.util.TestUtil;
import edu.toronto.csc301.warehouse.Rack;
import edu.toronto.csc301.warehouse.RackUtil;


/**
 * Test de-serialization from .rect.txt format.
 * 
 * In this test case, we read a file (one of the files under src/test/resources), 
 * deserialize it into an IGrid (using your code), and check that the various 
 * IGrid methods return the expected values.
 * 
 * Notice that this is an abstract class. Subclasses provide the name of the file 
 * we should read, and the values we expected to get from the de-serialized IGrid.
 */
public abstract class AbsRectDeserializeTest {


	// ------------------------------------------------------------------------
	
	
	// The name of the file we should read for this test 
	protected abstract String getGridFileName();

	//  The data we expect to get after reading and de-serializing the file
	protected abstract int width();
	protected abstract int height();
	protected abstract GridCell sw();
	protected abstract Map<GridCell,Rack> cell2item();


	// ------------------------------------------------------------------------

	
	private IGrid<Rack> grid;

	@Before
	public void setUp() throws Exception{
		this.grid = TestUtil.deserializeFromResource(
			"/" + getGridFileName(),  		// A file under src/test/resource 
			createRectSerializerDeserializer(),   
			RackUtil::bytes2rack			// Java 8 notation, equivalent to function-pointers in C
		);    
	}

	@After
	public void tearDown() throws Exception{
		this.grid = null;
	}


	// ========================================================================


	
	@Test
	public void testHasCellOnEveryCellInTheGrid() throws Exception {
		for (int x = sw().x; x < sw().x + width(); x++) {
			for (int y = sw().y; y < sw().y + height(); y++) {		
				assertTrue(grid.hasCell(GridCell.at(x, y)));
			}
		}
	}

	

	@Test
	public void testHasCellOutsideOfTheGrid() throws Exception {
		// Let's check the border around the rectangle
		for (int x = sw().x - 1; x < sw().x + width() + 1; x++) {
			assertFalse(grid.hasCell(GridCell.at(x, sw().y - 1)));
			assertFalse(grid.hasCell(GridCell.at(x, sw().y + height())));
		}
		for (int y = sw().y; y < sw().y + height(); y++) {
			assertFalse(grid.hasCell(GridCell.at(sw().x - 1, y)));
			assertFalse(grid.hasCell(GridCell.at(sw().x + width(), y)));
		}

		// Let's also check a random cell outside of the grid
		assertFalse(grid.hasCell(randomCellOutsideOfRectangle(width(), height(), sw())));
	}

	

	@Test
	public void testGetItemsOnAllNonEmptyCells() throws Exception {
		cell2item().forEach((cell, item) -> {
			assertEquals(item, grid.getItem(cell));
		});
	}



	@Test
	public void testGetItemsOnAllEmptyCells() throws Exception {
		for (int x = sw().x; x < sw().x + width(); x++) {
			for (int y = sw().y; y < sw().y + height(); y++) {
				GridCell cell = GridCell.at(x, y);
				// Skip non-empty cells
				if(cell2item().containsKey(cell)){
					continue;
				}
				assertNull(cell + " should be empty", grid.getItem(cell));
			}
		}
	}

	

	@Test(expected=IllegalArgumentException.class)
	public void testGetItemOutsideOfTheGrid() throws Exception{
		grid.getItem(randomCellOutsideOfRectangle(width(), height(), sw()));
	}



	@Test
	public void testGetCells() throws Exception {
		// Collect all the GridCells from the iterator into a set 
		Iterator<GridCell> cellIterator = grid.getGridCells();
		Set<GridCell> cellSet = new HashSet<GridCell>();
		while (cellIterator.hasNext()) {
			cellSet.add(cellIterator.next());
		}

		// Assert that the set is exactly as we expect
		assertEquals(width() * height(), cellSet.size());
		for (int x = sw().x; x < sw().x + width(); x++) {
			for (int y = sw().y; y < sw().y + height(); y++) {		
				assertTrue(cellSet.contains(GridCell.at(x, y)));
			}
		}
	}


}