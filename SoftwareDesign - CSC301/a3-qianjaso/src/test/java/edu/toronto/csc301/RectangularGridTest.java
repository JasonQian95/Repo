package edu.toronto.csc301;

import static edu.toronto.csc301.util.TestUtil.randomCell;
import static edu.toronto.csc301.util.TestUtil.randomCellOutsideOfRectangle;
import static edu.toronto.csc301.util.TestUtil.randomInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.util.TestUtil;
import edu.toronto.csc301.warehouse.Rack;

public class RectangularGridTest {


	/**
	 * Convenience helper function.
	 * Create (and return) a new rectangular grid of the given width and height
	 * whose south-west corner is <code>sw</code>.
	 */
	private IGrid<Rack> createGrid(int width, int height, GridCell sw) throws Exception{
		return TestUtil.createRectangularGrid(Rack.class, width, height, sw);
	}


	// ========================================================================
	// Invalid argument checks ...

	@Test(expected=NullPointerException.class)
	public void cannotCreateRectangularGridWithNullSWCorner() throws Exception {
		createGrid(randomInt(1, 100), randomInt(1, 100), null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void cannotCreateRectangularGridWithZeroWidth() throws Exception {
		createGrid(0, randomInt(1, 100), randomCell());
	}

	@Test(expected=IllegalArgumentException.class)
	public void cannotCreateRectangularGridWithNegativeWidth() throws Exception {
		createGrid(randomInt(-100, 0), randomInt(1, 100), randomCell());
	}

	@Test(expected=IllegalArgumentException.class)
	public void cannotCreateRectangularGridWithZeroHeight() throws Exception {
		createGrid(randomInt(1, 100), 0, randomCell());
	}

	@Test(expected=IllegalArgumentException.class)
	public void cannotCreateRectangularGridWithNegativeHeight() throws Exception {
		createGrid(randomInt(1, 100), randomInt(-100, 0), randomCell());
	}


	// ------------------------------------------------------------------------


	@Test
	public void createEmptyGridThenCheckHasCellOnEveryCell() throws Exception {
		int width = randomInt(1, 100);
		int height = randomInt(1, 100);
		GridCell sw = randomCell();
		IGrid<Rack> grid = createGrid(width, height, sw);

		for (int x = sw.x; x < sw.x + width; x++) {
			for (int y = sw.y; y < sw.y + height; y++) {		
				assertTrue(grid.hasCell(GridCell.at(x, y)));
			}
		}
	}


	@Test
	public void createEmptyGridThenCheckHasCellOutsideOfTheGrid() throws Exception {
		int width = randomInt(1, 100);
		int height = randomInt(1, 100);
		GridCell sw = randomCell();
		IGrid<Rack> grid = createGrid(width, height, sw);
		
		// Let's check the border around the rectangle
		for (int x = sw.x - 1; x < sw.x + width + 1; x++) {
			assertFalse(grid.hasCell(GridCell.at(x, sw.y - 1)));
			assertFalse(grid.hasCell(GridCell.at(x, sw.y + height)));
		}
		for (int y = sw.y; y < sw.y + height; y++) {
			assertFalse(grid.hasCell(GridCell.at(sw.x - 1, y)));
			assertFalse(grid.hasCell(GridCell.at(sw.x + width, y)));
		}

		// Let's also check a random cell outside of the grid
		assertFalse(grid.hasCell(randomCellOutsideOfRectangle(width, height, sw)));
	}


	@Test
	public void createEmptyGridThenCheckGetItemOnEveryCell() throws Exception {
		int width = randomInt(1, 100);
		int height = randomInt(1, 100);
		GridCell sw = randomCell();
		IGrid<Rack> grid = createGrid(width, height, sw);

		for (int x = sw.x; x < sw.x + width; x++) {
			for (int y = sw.y; y < sw.y + height; y++) {		
				assertNull(grid.getItem(GridCell.at(x, y)));
			}
		}
	}


	@Test(expected=IllegalArgumentException.class)
	public void createEmptyGridThenCheckGetItemOutsideOfTheGrid() throws Exception {
		int width = randomInt(1, 100);
		int height = randomInt(1, 100);
		GridCell sw = randomCell();
		IGrid<Rack> grid = createGrid(width, height, sw);

		grid.getItem(randomCellOutsideOfRectangle(width, height, sw));
	}


	@Test
	public void createEmptyGridThenCheckGetGridCells() throws Exception {
		int width = randomInt(1, 100);
		int height = randomInt(1, 100);
		GridCell sw = randomCell();

		IGrid<Rack> grid = createGrid(width, height, sw);

		// Collect all the GridCells from the iterator into a set 
		Iterator<GridCell> cellIterator = grid.getGridCells();
		Set<GridCell> cells = new HashSet<GridCell>();
		while (cellIterator.hasNext()) {
			cells.add(cellIterator.next());
		}

		// Assert that the set is exactly as we expect
		assertEquals(width * height, cells.size());
		for (int x = sw.x; x < sw.x + width; x++) {
			for (int y = sw.y; y < sw.y + height; y++) {		
				assertTrue(cells.contains(GridCell.at(x, y)));
			}
		}
	}


}
