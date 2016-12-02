package edu.toronto.csc301;

import static edu.toronto.csc301.util.TestUtil.createFlexGridSerializer;
import static edu.toronto.csc301.util.TestUtil.randomCell;
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
 * Test de-serialization from .flex.txt format.
 * 
 * In this test case, we read a file (one of the files under src/test/resources), 
 * de-serialize it into an IGrid (using your code), and check that the various 
 * IGrid methods return the expected values.
 * 
 * Notice that this is an abstract class. Subclasses provide the name of the file 
 * we should read, and the values we expected to get from the de-serialized IGrid.
 * 
 */
public abstract class AbsFlexDeserializeTest {
	
	// ------------------------------------------------------------------------
	

	// The name of the file we should read for this test 
	protected abstract String getGridFileName();

	/** 
	 * @return Map of all non-empty cell to the items they contain
	 */
	protected abstract Map<GridCell,Rack> cell2item();
	
	/**
	 * @return A set of all empty grid cells. 
	 */
	protected abstract Set<GridCell> emptyCells();


	// ------------------------------------------------------------------------

	
	private IGrid<Rack> grid;

	@Before
	public void setUp() throws Exception{
		this.grid = TestUtil.deserializeFromResource(
			"/" + getGridFileName(),  		// A file under src/test/resource 
			createFlexGridSerializer(),   
			RackUtil::bytes2rack			// Java 8 notation, equivalent to function-pointers in C
		);    
	}

	@After
	public void tearDown() throws Exception{
		this.grid = null;
	}

	
	/**
	 * Helper method that returns a grid-cell that is expected to be
	 * outside of the grid.
	 */
	private GridCell arbitraryCellOutsideOfGrid(){
		// Loop until we find a cell that is not on the grid 
		GridCell cell = randomCell();
		while(cell2item().containsKey(cell) || emptyCells().contains(cell)){
			cell = randomCell();
		}
		
		// Note: The loop above is generally a very bad idea (because the 
		//       runtime is unpredictable). For the purpose of your assignments 
		//       (where code readability is the most important factor), 
		//       I am allowing myself to use it.
		
		return cell;
	}

	
	// ========================================================================

	
	
	@Test
	public void testHasCellOnAllGridCells(){
		for(GridCell cell : cell2item().keySet()){
			assertTrue(grid.hasCell(cell));
		}
	}

	
	
	@Test
	public void testHasCellOutsideOfTheGrid() throws Exception {
		assertFalse(grid.hasCell(arbitraryCellOutsideOfGrid()));
	}

	

	@Test
	public void testGetItemOnAllNonEmptyCells() throws Exception {
		cell2item().forEach((cell, item) -> {
			assertEquals(item, grid.getItem(cell));
		});
	}
	
	
	
	@Test
	public void testGetItemsOnAllEmptyCells() throws Exception {
		emptyCells().forEach(cell -> {
			assertNull(cell + " should be empty.", grid.getItem(cell));
		});
	}
	
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetItemOutsideOfTheGrid() throws Exception {
		grid.getItem(arbitraryCellOutsideOfGrid());
	}
	

		
	@Test
	public void testGetCells() throws Exception {
		// Collect all the GridCells from the iterator into a set 
		Iterator<GridCell> cellIterator = grid.getGridCells();
		Set<GridCell> cellSet = new HashSet<GridCell>();
		while (cellIterator.hasNext()) {
			cellSet.add(cellIterator.next());
		}
		
		Set<GridCell> expectedCellSet = new HashSet<GridCell>();
		expectedCellSet.addAll(cell2item().keySet());
		expectedCellSet.addAll(emptyCells());

		// Assert that the set is exactly as we expect
		assertEquals(expectedCellSet, cellSet);
	}


}
