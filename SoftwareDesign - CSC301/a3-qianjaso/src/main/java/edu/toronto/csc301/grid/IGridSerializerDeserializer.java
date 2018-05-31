package edu.toronto.csc301.grid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Function;

public interface IGridSerializerDeserializer {

	/**
	 * Serialize <code>grid</code> (i.e. convert it to something like a String 
	 * or byte[]) and write the result to <code>output</code>.
	 * This method uses the given <code>item2byteArray</code> function to 
	 * serialize grid items (of type T). 
	 */
	public <T> void serialize(IGrid<T> grid, OutputStream output, 
			Function<T, byte[]> item2byteArray) throws IOException;
	
	
	/**
	 * De-serialize the data read from <code>input</code>.
	 * This method uses the given <code>byteArray2item</code> function to 
	 * de-serialize grid items (of type T).
	 */
	public <T> IGrid<T> deserialize(InputStream input, 
			Function<byte[],T> byteArray2item) throws IOException;
	
}
