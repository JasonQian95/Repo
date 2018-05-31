package edu.toronto.csc301.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.function.Function;

import edu.toronto.csc301.SetupTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;
import edu.toronto.csc301.warehouse.Rack;

/**
 * Helper functions used by other test classes.
 */
public class TestUtil {

	private static Random random = new Random();


	// ------------------------------------------------------------------------
	// Convenience factory methods

	public static <T> IGrid<T> createRectangularGrid(Class<?> T, int width, 
			int height, GridCell southWestCorner) throws Exception{
		return Helpers.newInstance(SetupTest.RECT_GRID, 
				new Class<?>[]{Integer.TYPE, Integer.TYPE, GridCell.class}, 
				width, height, southWestCorner);
	}
	
	public static IGrid<Rack> createRectangularGrid(int width, int height, GridCell sw) throws Exception{
		return createRectangularGrid(Rack.class, width, height, sw);
	}

	public static <T> IGrid<T> createFlexGrid(Class<?> T) throws Exception{
		return Helpers.newInstance(SetupTest.FLEX_GRID);
	}


	public static IGridSerializerDeserializer createRectSerializerDeserializer() throws Exception{
		return Helpers.newInstance(SetupTest.RECT_GRID_SERIALIZER_DESERIALIZER);
	}

	public static IGridSerializerDeserializer createFlexGridSerializer() throws Exception {
		return Helpers.newInstance(SetupTest.FLEX_GRID_SERIALIZER_DESERIALIZER);
	}


	// ------------------------------------------------------------------------

	/**
	 * @param path Path to a file under src/test/resources
	 */
	public static <T> IGrid<T> deserializeFromResource(String path, 
			IGridSerializerDeserializer gridDeserializer, 
			Function<byte[], T> itemDeserializer) throws Exception{
		
		try(InputStream input = System.class.getResourceAsStream(resourcePath(path))){
			return gridDeserializer.deserialize(input, itemDeserializer);
		}
	}
	
	
	public static String resourceAsString(String path) throws Exception{
		return new String(Files.readAllBytes(
			Paths.get(
				System.class.getResource(resourcePath(path)).toURI()
			)
		));
	}
	
	
	// Convenience helper
	private static String resourcePath(String path){
		if (path.startsWith("/")){
			return path;
		} else {
			return "/" + path;
		}
	}


	public static <T> String serialize2String(IGrid<T> grid, 
			IGridSerializerDeserializer serializer, 
			Function<T,byte[]> item2bytes) throws IOException{

		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
			serializer.serialize(grid, baos, item2bytes);
			baos.flush();
			return new String(baos.toByteArray());
		}
	}




	public static GridCell randomCellOutsideOfRectangle(int width, int height, GridCell sw){
		// Either west or east of the rectangle
		int x = random.nextBoolean() ? 
				randomInt(sw.x - randomInt(1, 1000), sw.x) :
				randomInt(sw.x + width, sw.x + width + randomInt(1, 1000));

		// Either south or north of the rectangle
		int y = random.nextBoolean() ? 
				randomInt(sw.y - randomInt(1, 1000), sw.y) :
				randomInt(sw.y + height, sw.y + height + randomInt(1, 1000));

		return GridCell.at(x, y);
	}



	/**
	 * @return A cell that is different than the given <code>cell</code> (in its 
	 * x-coordinate, y-coordinate or both).
	 */
	public static GridCell randomCellOtherThan(GridCell cell){
		return randomCellOutsideOfRectangle(1, 1, cell);
	}


	public static GridCell randomCell(){
		return GridCell.at(randomInt(-10000, 10000), randomInt(-10000, 10000));
	}

	/**
	 * Return a random integer in the range [a, b).
	 * That is, including a and excluding b.
	 */
	public static int randomInt(int a, int b){
		return a + random.nextInt(b - a);
	}


}
