package edu.toronto.csc301.grid.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;
import edu.toronto.csc301.warehouse.Rack;
import edu.toronto.csc301.warehouse.RackUtil;

public class FlexGridSerializerDeserializer  implements IGridSerializerDeserializer{

	@Override
	public <T> void serialize(IGrid<T> grid, OutputStream output, Function<T, byte[]> item2byteArray)
			throws IOException {
		OutputStreamWriter outputWriter = new OutputStreamWriter(output);
		BufferedWriter out = new BufferedWriter(outputWriter);
		Iterator<GridCell> cells = grid.getGridCells();
		boolean skipSecond = true; 
		while (cells.hasNext()){
			GridCell currentCell = cells.next();
			if (skipSecond){
				cells.next();
				skipSecond = false;
			}
			//output.write((String.valueOf(currentCell.x) + ":" + String.valueOf(currentCell.y)).getBytes());
			out.write(currentCell.x + ":" + currentCell.y);
			if(currentCell.getItem() != null){
				//output.write((" R:" + currentCell.getItem().getCapacity()).getBytes());
				out.write(" R:" + currentCell.getItem().getCapacity());
			}
			//output.write(("\n").getBytes());
			out.newLine();
			/*
			T item = grid.getItem(currentCell);
			output.write("C".getBytes());
			output.write(currentCell.toString().getBytes());
			output.write("\n".getBytes());
			if (item != null){
				output.write("R".getBytes());
				output.write(item2byteArray.apply(grid.getItem(currentCell)));
				output.write("\n".getBytes());
			}
			*/
		}
		out.flush();
		out.close();
	}

	@Override
	public <T> IGrid<T> deserialize(InputStream input, Function<byte[], T> byteArray2item) throws IOException {
		ArrayList<GridCell> cells = new ArrayList<GridCell>();
		InputStreamReader inputReader = new InputStreamReader(input);
		BufferedReader in = new BufferedReader(inputReader);
		String str;
		int x = 0;
		int y = 0;
		GridCell SWCell = null;
		
		while ((str = in.readLine()) != null){
			if (str.startsWith("width:")){
				x = Integer.parseInt(str.substring(str.indexOf(":") + 2), str.length());
			}
			else if (str.startsWith("height:")){
				y = Integer.parseInt(str.substring(str.indexOf(":") + 2), str.length());
			}
			else if (str.startsWith("south-west:")){
				SWCell = GridCell.at(Integer.parseInt(str.substring(str.indexOf(":") + 2, str.lastIndexOf(":"))), Integer.parseInt(str.substring(str.lastIndexOf(":") + 1, str.length())));
			}
			else if (str.startsWith("\n")){
				
			}
			else{
				GridCell c = GridCell.at(Integer.parseInt(str.substring(0, str.indexOf(":"))), Integer.parseInt(str.substring(str.indexOf(":") + 1, str.indexOf("R") < 0 ? str.length() : str.indexOf("R") - 1)));
				cells.add(c);
				if (str.indexOf("R") != -1){
					Rack rack = new Rack(Integer.parseInt(str.substring(str.indexOf("R") + 2, str.length())));
					c.addItem(rack);
				}
			}
		}

		if (SWCell != null){
			for (int i = SWCell.x; i < SWCell.x + x; i++){
				for (int j = SWCell.y; j < SWCell.y + y; j++){
					if (!cells.contains(GridCell.at(i, j))){
						cells.add(GridCell.at(i, j));
					}
				}
			}
		}
		else if (SWCell == null){
			
		}
		
		/*
		ArrayList<GridCell> cells = new ArrayList<GridCell>();
		int in;
		char ch;
		StringBuffer lineBuffer;
		StringBuffer chBuffer;
		String str;
		
		in = input.read();
		lineBuffer = new StringBuffer();
		lineBuffer.append((char) in);
		chBuffer = new StringBuffer();
		chBuffer.append((char) in);
		str = chBuffer.toString();
		if (str.equals("w")){
			int x = 0;
			int y = 0;
			GridCell SWCell = null;
			boolean onX = true;
			boolean onY = false;
			boolean onSW = false;
			while((in = input.read()) != -1){
				lineBuffer.append((char) in);
				chBuffer = new StringBuffer();
				chBuffer.append((char) in);
				str = chBuffer.toString();
				if(str.equals("\n")){
					str = lineBuffer.toString();
					if (onX){
						x = Integer.parseInt(str.substring(str.indexOf(":") + 2), str.length() - 1);
						onX = false;
						onY = true;
					}
					else if (onY){
						y = Integer.parseInt(str.substring(str.indexOf(":") + 2), str.length() - 1);
						onY = false;
						onSW = true;
					}
					else if (onSW){
						SWCell = GridCell.at(Integer.parseInt(str.substring(str.indexOf(":"), str.lastIndexOf(":"))), Integer.parseInt(str.substring(str.lastIndexOf(":") + 1, str.indexOf("R") < 0 ? str.length() - 1 : str.indexOf("R") - 1)));
						onSW = false;
					}
					lineBuffer = new StringBuffer();
				}
			}
			for (int i = SWCell.x; i <= SWCell.x + x; i++){
				for (int j = SWCell.y; j <= SWCell.y + y; j++){
					cells.add(GridCell.at(i, j));
				}
			}
		}
		else{
			while((in = input.read()) != -1){
				lineBuffer.append((char) in);
				chBuffer = new StringBuffer();
				chBuffer.append((char) in);
				str = chBuffer.toString();
				if(str.equals("\n")){
					str = lineBuffer.toString();
					cells.add(GridCell.at(Integer.parseInt(str.substring(0, str.indexOf(":"))), Integer.parseInt(str.substring(str.indexOf(":") + 1, str.indexOf("R") < 0 ? str.length() - 1 : str.indexOf("R") - 1))));
					lineBuffer = new StringBuffer();
				}
			}	
		}
		*/
		/*
		ArrayList<GridCell> cells = new ArrayList<GridCell>();
		ArrayList<T> items = new ArrayList<T>();
		int in;
		char ch;
		StringBuffer buffer;
		String str;
		while ((in = input.read()) != -1){
			buffer = new StringBuffer();
			buffer.append((char) in);
			str = buffer.toString();
			if (str.equals("C")){
				int x = 0;
				int y = 0;
				boolean onX = false;
				boolean onY = false;
				boolean done = false;
				while (!done){
					if ((in = input.read()) == -1){
						//error
					}
					buffer = new StringBuffer();
					buffer.append((char) in);
					str = buffer.toString();
					if (str.equals("[")){
						onX = true;
					}
					while (onX){
						in = input.read();
						buffer = new StringBuffer();
						buffer.append((char) in);
						str = buffer.toString();
						if (str.equals(",")){
							onX = false;
							onY = true;
						}
						else{
							x = x * 10;
							x += Integer.parseInt(str);
						}
					}
					while (onY){
						in = input.read();
						buffer = new StringBuffer();
						buffer.append((char) in);
						str = buffer.toString();
						if (str.equals("]")){
							onY = false;
							done = true;
							in = input.read();
							buffer = new StringBuffer();
							buffer.append((char) in);
							str = buffer.toString();
							//str should be "\n"
						}
						else{
							y = y * 10;
							y += Integer.parseInt(str);
						}
					}
				}
				GridCell cell = GridCell.at(x, y);
				cells.add(cell);
				items.add(null);
			}
			else if (str.equals("R")){
				boolean done = false;
				ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
				while (!done){
					in = input.read();
					buffer = new StringBuffer();
					buffer.append((char) in);
					str = buffer.toString();
					if (str.equals("\n")){
						done = true;
					}
					else{
						byteBuff.write(in);
					}
				}
				Rack rack = RackUtil.bytes2rack(byteBuff.toByteArray());
				items.set(items.size() - 1, (T) rack);
				
			}
			else {
				//error
			}
		}
		*/
		FlexGrid<T> flex = new FlexGrid<T>();
		flex.cells = cells;
		return flex;
	}

}