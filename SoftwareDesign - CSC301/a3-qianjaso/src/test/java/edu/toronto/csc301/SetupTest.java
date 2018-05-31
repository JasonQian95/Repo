package edu.toronto.csc301;

import org.junit.Test;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;

import static edu.toronto.csc301.util.Asserts.*;

public class SetupTest{

	private static final String PKG = "edu.toronto.csc301.grid.impl.";
	
	public static final String  RECT_GRID = PKG + "RectangularGrid";
	public static final String  FLEX_GRID = PKG + "FlexGrid";
	public static final String  RECT_GRID_SERIALIZER_DESERIALIZER = PKG + 
			"RectangularGridSerializerDeserializer";
	public static final String  FLEX_GRID_SERIALIZER_DESERIALIZER = PKG + 
			"FlexGridSerializerDeserializer";
	
	
	
	
	@Test
	public void checkExistence_RectangularGrid() {
		assertClassExists(RECT_GRID);
	}
	
	@Test
	public void checkExistence_FlexGrid() {
		assertClassExists(FLEX_GRID);
	}
	
	
	@Test
	public void checkExistence_RectangularGridSerializerDeserializer() {
		assertClassExists(RECT_GRID_SERIALIZER_DESERIALIZER);
	}
	
	@Test
	public void checkExistence_FlexGridSerializerDeserializer() {
		assertClassExists(FLEX_GRID_SERIALIZER_DESERIALIZER);
	}
	
	
	
	
	@Test
	public void checkInterface_RectangularGrid() throws ClassNotFoundException {
		assertClassImplementsInterface(RECT_GRID, IGrid.class);
	}
	
	@Test
	public void checkInterface_FlexGrid() throws ClassNotFoundException {
		assertClassImplementsInterface(FLEX_GRID, IGrid.class);
	}
	
	
	@Test
	public void checkInterface_RectangularGridSerializerDeserializer() 
											throws ClassNotFoundException {
		assertClassImplementsInterface(RECT_GRID_SERIALIZER_DESERIALIZER, 
										IGridSerializerDeserializer.class);
	}
	
	@Test
	public void checkInterface_FlexGridSerializerDeserializer() 
											throws ClassNotFoundException {
		assertClassImplementsInterface(FLEX_GRID_SERIALIZER_DESERIALIZER, 
										IGridSerializerDeserializer.class);
	}
	
	
	
	
	@Test
	public void checkConstructor_RectangularGrid() throws ClassNotFoundException {
		assertClassHasConstructor(RECT_GRID, 
									Integer.TYPE, Integer.TYPE, GridCell.class);
	}
	
	@Test
	public void checkConstructor_FlexGrid() throws ClassNotFoundException {
		assertClassHasDefaultConstructor(FLEX_GRID);
	}
	
	@Test
	public void checkConstructor_RectangularGridSerializerDeserializer() 
												throws ClassNotFoundException {
		assertClassHasDefaultConstructor(RECT_GRID_SERIALIZER_DESERIALIZER);
	}
	
	@Test
	public void checkConstructor_FlexGridSerializerDeserializer() 
												throws ClassNotFoundException {
		assertClassHasDefaultConstructor(FLEX_GRID_SERIALIZER_DESERIALIZER);
	}
	
}
