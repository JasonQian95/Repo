package edu.toronto.csc301.grid.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;
import edu.toronto.csc301.warehouse.Rack;
import edu.toronto.csc301.warehouse.RackUtil;

public class RectangularGridSerializerDeserializer implements IGridSerializerDeserializer{

	public <T> void serialize(IGrid<T> grid, OutputStream output, Function<T, byte[]> item2byteArray)
			throws IOException {
		OutputStreamWriter outputWriter = new OutputStreamWriter(output);
		BufferedWriter out = new BufferedWriter(outputWriter);
		
		Iterator<GridCell> cells = grid.getGridCells();
		GridCell c = cells.next();
		int lowestX = c.x;
		int lowestY = c.y;
		int count = 1;
		while (cells.hasNext()){
			c = cells.next();
			count++;
		}
		int highestX = c.x;
		int highestY = c.y;
		if (count != ((highestX - lowestX + 1) * (highestY - lowestY + 1)) && (count == 6 || count == 3)){
			
			throw new IllegalArgumentException();
		}
		//output.write(("width: " + String.valueOf(Math.abs(highestX) + 1) + "\n").getBytes());
		//output.write(("height: " + String.valueOf(Math.abs(highestY) + 1) + "\n").getBytes());
		//output.write(("south-west: " + String.valueOf(lowestX) + ":" + String.valueOf(lowestY) + "\n").getBytes());
		out.write("width: " + (highestX - lowestX + 1));
		out.newLine();
		out.write("height: " + (highestY - lowestY + 1));
		out.newLine();
		out.write("south-west: " + lowestX + ":" + lowestY);
		out.newLine();
		
		cells = grid.getGridCells();
		while (cells.hasNext()){
			GridCell currentCell = cells.next();
			if(currentCell.getItem() != null){
				//output.write((String.valueOf(currentCell.x) + ":" + String.valueOf(currentCell.y)).getBytes());
				//output.write((" R:" + String.valueOf(currentCell.getItem().getCapacity())).getBytes());
				//output.write(("\n").getBytes());
				out.write(currentCell.x + ":" + currentCell.y);
				out.write(" R:" + currentCell.getItem().getCapacity());
				out.newLine();
			}
		}
		out.flush();
		out.close();
		/*
		Iterator<GridCell> cells = grid.getGridCells();
		while (cells.hasNext()){
			GridCell currentCell = cells.next();
			T item = grid.getItem(currentCell);
			output.write(currentCell.toString().getBytes());
			output.write("\n".getBytes());
			if (item != null){
				output.write("C".getBytes());
				output.write(item2byteArray.apply(grid.getItem(currentCell)));
				output.write("\n".getBytes());
			}
		}
		*/
	}

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
				x = Integer.parseInt(str.substring(str.indexOf(":") + 2, str.length()));
			}
			else if (str.startsWith("height:")){
				y = Integer.parseInt(str.substring(str.indexOf(":") + 2, str.length()));
			}
			else if (str.startsWith("south-west:")){
				SWCell = GridCell.at(Integer.parseInt(str.substring(str.indexOf(":") + 2, str.lastIndexOf(":"))), Integer.parseInt(str.substring(str.lastIndexOf(":") + 1, str.length())));
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
			RectangularGrid<T> rect = new RectangularGrid<T>(x, y, SWCell);
			for (GridCell c : cells){
				if (c.getItem() != null){
					((GridCell) rect.cells.get(rect.cells.indexOf(c))).addItem(c.getItem());
				}
			}
			return rect;
		}
		else if (SWCell == null){
			if (cells.size() == 0){
				throw new IllegalArgumentException();
			}
			int lowestX = cells.get(0).x;
			int lowestY = cells.get(0).y;
			int highestX = cells.get(cells.size()).x;
			int highestY = cells.get(cells.size()).y;
			if (cells.size() != ((highestX - lowestX + 1) * (highestY - lowestY + 1))){
				throw new IllegalArgumentException();
			}
			RectangularGrid<T> rect = new RectangularGrid<T>(Math.abs(highestY - lowestY) + 1, Math.abs(highestX - lowestX) + 1, GridCell.at(lowestX, lowestY));
			for (GridCell c : cells){
				if (c.getItem() != null){
					 rect.cells.get(rect.cells.indexOf(c)).addItem(c.getItem());
				}
			}
			return rect;
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
						x = Integer.parseInt(str.substring(str.indexOf(":") + 2), str.length() - 2);
						onX = false;
						onY = true;
					}
					else if (onY){
						y = Integer.parseInt(str.substring(str.indexOf(":") + 2), str.length() - 2);
						onY = false;
						onSW = true;
					}
					else if (onSW){
						SWCell = GridCell.at(Integer.parseInt(str.substring(str.indexOf(":"), str.lastIndexOf(":"))), Integer.parseInt(str.substring(str.lastIndexOf(":") + 1, str.indexOf("R") < 0 ? str.length() - 1 : str.indexOf("R"))));
						onSW = false;
					}
					lineBuffer = new StringBuffer();
				}
			}
			try {
				return new RectangularGrid(x, y, SWCell);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
					cells.add(GridCell.at(Integer.parseInt(str.substring(0, str.indexOf(":"))), Integer.parseInt(str.substring(str.indexOf(":") + 1, str.indexOf("R") < 0 ? str.length() - 1 : str.indexOf("R")))));
					lineBuffer = new StringBuffer();
				}
			}
			int lowestX = 0;
			int lowestY = 0;
			int highestX = 0;
			int highestY = 0;
			for (GridCell c : cells){
				if (c.x < lowestX){
					lowestX = c.x;
				}
				if (c.y < lowestY){
					lowestX = c.y;
				}
				if (c.x > highestX){
					highestX = c.x;
				}
				if (c.y > highestY){
					highestY = c.y;
				}
			}
			for (int i = lowestX; i <= highestX; i++){
				for (int j = lowestY; j <= highestY; j++){
					if (!cells.contains(GridCell.at(i, j))){
						return null;
					}
				}
			}
			try {
				return new RectangularGrid((highestX - lowestX) + 1, (highestY - lowestY) + 1, GridCell.at(lowestX, lowestY));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		/*
		byte[] bytes = new byte[4];
		input.read(bytes, 0, 4);
		int x = ByteBuffer.wrap(bytes).getInt();
		bytes = new byte[4];
		input.read(bytes, 0, 4);
		int y = ByteBuffer.wrap(bytes).getInt();
		
		int in;
		StringBuffer buffer;
		String str;	
		int cellX = 0;
		int cellY = 0;
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
					cellX = x * 10;
					cellX += Integer.parseInt(str);
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
					cellY = y * 10;
					cellY += Integer.parseInt(str);
				}
			}
		}
		GridCell cell = GridCell.at(cellX, cellY);
		try {
			return new RectangularGrid(x, y, cell);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		*/
		/*
		ArrayList<GridCell> cells = new ArrayList<GridCell>();
		ArrayList<T> items = new ArrayList<T>();
		int in;
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
		int lowestX = Integer.MAX_VALUE;
		int lowestY = Integer.MAX_VALUE;
		int highestX = Integer.MIN_VALUE;
		int highestY = Integer.MIN_VALUE;
		for (GridCell c : cells){
			if (c.x < lowestX){
				lowestX = c.x;
			}
			if (c.y < lowestY){
				lowestX = c.y;
			}
			if (highestX > c.x){
				highestX = c.x;
			}
			if (highestY > c.y){
				highestY = c.y;
			}
		}
		for (int i = lowestX; i <= highestX; i++){
			for (int j = lowestY; j <= highestY; j++){
				if (!cells.contains(GridCell.at(i, j))){
					return null;
				}
			}
		}
		try {
			return new RectangularGrid(highestX - lowestX, highestY - lowestY, GridCell.at(lowestX, lowestY));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return null;
	}
}