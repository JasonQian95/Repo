package edu.toronto.csc301;

import static edu.toronto.csc301.util.TestUtil.createRectangularGrid;
import static edu.toronto.csc301.util.TestUtil.deserializeFromResource;
import static edu.toronto.csc301.util.TestUtil.randomCell;
import static edu.toronto.csc301.util.TestUtil.randomInt;
import static edu.toronto.csc301.util.TestUtil.resourceAsString;
import static edu.toronto.csc301.util.TestUtil.serialize2String;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;
import edu.toronto.csc301.util.TestUtil;
import edu.toronto.csc301.warehouse.Rack;
import edu.toronto.csc301.warehouse.RackUtil;



public class SerializeDeserializeTest {

	
	// ------------------------------------------------------------------------

	private IGridSerializerDeserializer rectSD;
	private IGridSerializerDeserializer flexSD;


	@Before
	public void setUp() throws Exception {
		rectSD = TestUtil.createRectSerializerDeserializer();
		flexSD = TestUtil.createFlexGridSerializer();
	}
	

	// ------------------------------------------------------------------------
	
	
	
	@Test
	public void serializeEmptyRectangularGrid() throws Exception{
		int width = randomInt(1, 100);
		int height = randomInt(1, 100);
		GridCell sw = randomCell();
		IGrid<Rack> grid = createRectangularGrid(width, height, sw);
		
		// Create a String 
		String expected = new StringBuilder()
			.append(String.format("width: %d\n", width))
			.append(String.format("height: %d\n", height))
			.append(String.format("south-west: %d:%d\n", sw.x, sw.y))
			.toString();
		
		String actual = TestUtil.serialize2String(grid, rectSD, RackUtil::rack2bytes);
		
		assertEquals(expected.trim(), actual.trim());
	}
	
	
	
	// ------------------------------------------------------------------------
	


	@Test
	public void rect2flex_00() throws Exception{
		testRect2Flex("grid.00.rect.txt", "grid.00.flex.txt");
	}
	
	@Test
	public void rect2flex_01() throws Exception{
		testRect2Flex("grid.01.rect.txt", "grid.01.flex.txt");
	}
	
	@Test
	public void rect2flex_02() throws Exception{
		testRect2Flex("grid.02.rect.txt", "grid.02.flex.txt");
	}
	
	@Test
	public void rect2flex_03() throws Exception{
		testRect2Flex("grid.03.rect.txt", "grid.03.flex.txt");
	}
	
	@Test
	public void rect2flex_04() throws Exception{
		testRect2Flex("grid.04.rect.txt", "grid.04.flex.txt");
	}
	
	@Test
	public void rect2flex_05() throws Exception{
		testRect2Flex("grid.05.rect.txt", "grid.05.flex.txt");
	}
	
	@Test
	public void rect2flex_06() throws Exception{
		testRect2Flex("grid.06.rect.txt", "grid.06.flex.txt");
	}
	
	
	/**
	 * Test the following sequence of conversions:
	 * 
	 *   Text in rect.txt format --> IGrid<Rack>  --> Text flex.txt format
	 * 
	 * @param pathRect Path (relative to src/test/resources) to a .rect.txt file
	 * @param pathFlex Path (relative to src/test/resources) to a .flex.txt file
	 */
	private void testRect2Flex(String pathRect, String pathFlex) throws Exception{
		IGrid<Rack> grid = deserializeFromResource(pathRect, rectSD, RackUtil::bytes2rack);
		String s = serialize2String(grid, flexSD, RackUtil::rack2bytes);
		
		// Let's make sure that the grid was serialized properly to .flex.txt format ...
 
		String[] lines = s.split("\n");
		Arrays.sort(lines);
		
		String[] expectedLines = resourceAsString(pathFlex).split("\n");
		Arrays.sort(expectedLines);
		
		assertArrayEquals(expectedLines, lines);
	}

	
	// ------------------------------------------------------------------------

	
	@Test
	public void flex2rect_00() throws Exception{
		testFlex2Rect("grid.00.flex.txt", "grid.00.rect.txt");
	}
	
	@Test
	public void flex2rect_01() throws Exception{
		testFlex2Rect("grid.01.flex.txt", "grid.01.rect.txt");
	}
	
	@Test
	public void flex2rect_02() throws Exception{
		testFlex2Rect("grid.02.flex.txt", "grid.02.rect.txt");
	}
	
	@Test
	public void flex2rect_03() throws Exception{
		testFlex2Rect("grid.03.flex.txt", "grid.03.rect.txt");
	}
	
	@Test
	public void flex2rect_04() throws Exception{
		testFlex2Rect("grid.04.flex.txt", "grid.04.rect.txt");
	}
	
	@Test
	public void flex2rect_05() throws Exception{
		testFlex2Rect("grid.05.flex.txt", "grid.05.rect.txt");
	}
	
	@Test
	public void flex2rect_06() throws Exception{
		testFlex2Rect("grid.06.flex.txt", "grid.06.rect.txt");
	}
	
	
	/**
	 * Test the following sequence of conversions:
	 * 
	 *   Text in flex.txt format --> IGrid<Rack>  --> Text rect.txt format
	 * 
	 * @param pathFlex Path (relative to src/test/resources) to a .flex.txt file
	 * @param pathRect Path (relative to src/test/resources) to a .rect.txt file
	 */
	private void testFlex2Rect(String pathFlex, String pathRect) throws Exception{
		IGrid<Rack> grid = deserializeFromResource(pathFlex, flexSD, RackUtil::bytes2rack);
		String s = serialize2String(grid, rectSD, RackUtil::rack2bytes);
		
		// Let's make sure that the grid was serialized properly to .rect.txt format ...
 
		String[] lines = s.split("\n");
		String[] expectedLines = resourceAsString(pathRect).split("\n");
		
		// The first three lines should be the same
		assertEquals(expectedLines[0], lines[0]); // width
		assertEquals(expectedLines[1], lines[1]); // height
		assertEquals(expectedLines[2], lines[2]); // south-west
		
		// Then, the remaining lines
		lines = Arrays.copyOfRange(lines, 3, lines.length);
		expectedLines = Arrays.copyOfRange(expectedLines, 3, expectedLines.length);
		
		// May appear in different order ...
		Arrays.sort(lines);
		Arrays.sort(expectedLines);
		assertArrayEquals(expectedLines, lines);
	}
	

	// ------------------------------------------------------------------------
	

	
	@Test(expected=IllegalArgumentException.class)
	public void flex2rect_10() throws Exception{
		flex2rectOnNonRectangularGrid("grid.10.flex.txt");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void flex2rect_11() throws Exception{
		flex2rectOnNonRectangularGrid("grid.11.flex.txt");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void flex2rect_12() throws Exception{
		flex2rectOnNonRectangularGrid("grid.12.flex.txt");
	}
	
	
	
	/**
	 * Deserialize the .flex.txt resource with the given <code>pathFlex</code>,
	 * and then try to serialize it to .rect.txt format.
	 * This method is expected to throw an IllegalArgumentException.
	 */
	private void flex2rectOnNonRectangularGrid(String pathFlex) throws Exception{
		// Load a flex-grid that does NOT have a rectangular shape
		IGrid<Rack> grid = deserializeFromResource(pathFlex, flexSD, RackUtil::bytes2rack);
		// Try to serialize it with the rect-serializer
		serialize2String(grid, rectSD, RackUtil::rack2bytes);
	}
	
}
