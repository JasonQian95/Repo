package edu.toronto.csc301.robot;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import edu.toronto.csc301.grid.GridCell;

public class GridRobot implements IGridRobot, Runnable {

	
	// =========================== Static Helper(s) ===========================
	
	
	public static GridCell oneCellOver(GridCell location, Direction direction){
		switch (direction) {
		case NORTH:
			return GridCell.at(location.x, location.y + 1);
		case EAST:
			return GridCell.at(location.x + 1, location.y);
		case SOUTH:
			return GridCell.at(location.x, location.y - 1);
		case WEST:
			return GridCell.at(location.x - 1, location.y);
		default:
			return null;
		}
	}
	
	
	// ========================================================================
	
	
	
	private GridCell location;
	private long delayInMilliseconds;
	private Set<StepListener> stepListeners;
	private Thread t; //max one thread per robot
	private GridCell initialLocation; //used for the thread name
	private Direction direction; //used for threading
	private boolean moving;
	public static final long defaultDelayInMilliseconds = 500; //I know convention is to put constants in all CAPS but it looks ugly ) :
	
	
	
	public GridRobot(GridCell initialLocation, long delayInMilliseconds) {
		Objects.requireNonNull(initialLocation);
		this.location = initialLocation;
		this.initialLocation = initialLocation;
		this.delayInMilliseconds = delayInMilliseconds;
		this.stepListeners = new HashSet<StepListener>();
		this.moving = false;
	}
	
	public GridRobot(GridCell initialLocation) {
		this(initialLocation, defaultDelayInMilliseconds);   // Default delay is half a second
	}
	
	
	
	
	@Override
	public GridCell getLocation() {
		return location;
	}


	@Override
	//NEW
	public void step(Direction direction) {
		if (moving){
			try {
				throw new alreadyMovingException();
			} catch (alreadyMovingException e) {
				return;
			}
		}
		this.direction = direction;
		t = new Thread(this, initialLocation.toString());
		t.start();
	}
	
	@Override
	//NEW
	public void run() {
		moving = true;
		for(StepListener listener : stepListeners){
			listener.onStepStart(this, this.direction);
		}
		
		// Simulate a the time it takes for a robot to move by sleeping 
		try {
			Thread.sleep(delayInMilliseconds);
		} catch (InterruptedException e) { }

		location = GridRobot.oneCellOver(location, this.direction); 
		
		for(StepListener listener : stepListeners){
			listener.onStepEnd(this, this.direction);
		}
		moving = false;
	}

	@Override
	public void startListening(StepListener listener) {
		stepListeners.add(listener);
	}

	@Override
	public void stopListening(StepListener listener) {
		stepListeners.remove(listener);
	}
	
	private class alreadyMovingException extends Exception{
		public alreadyMovingException(){
			super("Robot is already in motion");
		}
	}

}
