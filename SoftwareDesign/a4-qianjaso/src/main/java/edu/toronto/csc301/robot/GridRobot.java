package edu.toronto.csc301.robot;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.toronto.csc301.grid.GridCell;

public class GridRobot implements IGridRobot{

	int x;
	int y;
	ArrayList<IGridRobot.StepListener> listeners = new ArrayList<IGridRobot.StepListener>();
	
	public GridRobot(GridCell location){
		x = location.x;
		y = location.y;
	}
	
	public GridCell getLocation() {
		return GridCell.at(x, y);
	}

	public void step(Direction direction) {
		for (IGridRobot.StepListener listener : listeners){
			listener.onStepStart(this, direction);
		}
		switch (direction) {
		case NORTH:
			y += 1;
			break;
		case EAST:
			x += 1;
			break;
		case SOUTH:
			y -= 1;
			break;
		case WEST:
			x -= 1;
			break;
		}
		for (IGridRobot.StepListener listener : listeners){
			listener.onStepEnd(this, direction);
		}
	}

	public void startListening(IGridRobot.StepListener listener) {
		listeners.add(listener);
	}

	public void stopListening(IGridRobot.StepListener listener) {
		listeners.remove(listener);
	}

}
