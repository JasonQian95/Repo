package edu.toronto.csc301.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.function.Function;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;

/**
 * This class is here to ensure that you do not change the 
 * given interfaces.
 * If you change one of the given interface, then its dummy implementation
 * below will not compile.
 */
public class CSC301SafetyCheck {

	
	static class DummyGrid<T> implements IGrid<T>{

		@Override
		public T getItem(GridCell cell) {
			return null;
		}

		@Override
		public Iterator<GridCell> getGridCells() {
			return null;
		}

		@Override
		public boolean hasCell(GridCell cell) {
			return false;
		}
		
	}
	
	
	static class DummySerializerDeserializer implements IGridSerializerDeserializer{

		@Override
		public <T> void serialize(IGrid<T> grid, OutputStream output, 
				Function<T, byte[]> item2byteArray) throws IOException {
		}

		@Override
		public <T> IGrid<T> deserialize(InputStream input, 
				Function<byte[], T> byteArray2item) throws IOException {
			return null;
		}
		
	}
	
	
}
