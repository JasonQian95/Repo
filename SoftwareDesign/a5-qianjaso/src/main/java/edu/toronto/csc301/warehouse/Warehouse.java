package edu.toronto.csc301.warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.robot.GridRobot;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;
import edu.toronto.csc301.util.TestUtil;

public class Warehouse<T> implements IWarehouse, IGridRobot.StepListener{

	private IGrid<T> grid;
	private ArrayList<IGridRobot> robots = new ArrayList<IGridRobot>();
	private Map<IGridRobot, Direction> robotsInMotion = new HashMap<IGridRobot, Direction>();
	private ArrayList<Consumer<IWarehouse>> observers = new ArrayList<Consumer<IWarehouse>>();
	
	public Warehouse(IGrid<T> grid){
		if (grid == null){
			throw new NullPointerException();
		}
		this.grid = grid;
	}
	
	public IGrid<Rack> getFloorPlan() {
		return (IGrid<Rack>) grid;
	}

	public IGridRobot addRobot(GridCell initialLocation) {
		return addRobot(initialLocation, GridRobot.defaultDelayInMilliseconds);
	}
	
	//NEW
	public IGridRobot addRobot(GridCell initialLocation, long delayInMilliseconds) {
		boolean containsCell = false;
		Iterator<GridCell> cells = grid.getGridCells();
		while (cells.hasNext()){
			if (initialLocation.equals(cells.next())){
				containsCell = true;
				break;
			}
		}
		if (!containsCell){
			throw new IllegalArgumentException();
		}
		for (IGridRobot robot : robots){
			if (robot.getLocation().equals(initialLocation)){
				throw new IllegalArgumentException();
			}
		}
		GridRobot robot = new GridRobot(initialLocation, delayInMilliseconds);
		robots.add(robot);
		for (Consumer<IWarehouse> consumer : observers){
			consumer.accept(this);
		}
		robot.startListening(this);
		return robots.get(robots.size() - 1);
	}

	public Iterator<IGridRobot> getRobots() {
		return robots.iterator();
	}

	public Map<IGridRobot, Direction> getRobotsInMotion() {
		return Collections.unmodifiableMap(robotsInMotion);
	}

	public void subscribe(Consumer<IWarehouse> observer) {
		observers.add(observer);
	}

	public void unsubscribe(Consumer<IWarehouse> observer) {
		observers.remove(observer);
	}

	public void onStepStart(IGridRobot robot, Direction direction) {
		robotsInMotion.put(robot, direction);
		for (Consumer<IWarehouse> consumer : observers){
			consumer.accept(this);
		}
	}

	public void onStepEnd(IGridRobot robot, Direction direction) {
		robotsInMotion.remove(robot);
		for (Consumer<IWarehouse> consumer : observers){
			consumer.accept(this);
		}
	}
	
	//NEW
	// This is tested in MultipleRobotTests.java but not SingleRobotTests.java
	public void moveAllRobotsOneStep(Map<IGridRobot, GridCell> robot2dest){
		// If the passed map were contained GridRobots, we could count the longest delay and sleep this thread
		// But I'm not doing it to stay consistent with all the other methods (and to stay flexible)
		// This requires the tests to sleep themselves
		//long longestDelay = 0;
		PathPlanner planner = new PathPlanner();
		for (Map.Entry<IGridRobot, GridCell> entry: robot2dest.entrySet()){
			Entry<IGridRobot, Direction> dir = planner.nextStepOneRobot(this, entry);
			if (dir != null){
				dir.getKey().step(dir.getValue());
			}
		}
	}
	
	//NEW
	// Method wont work due to not knowing how long the delay on each robot is, since they are IGridRobots instead of GridRobots
	/*
	public void moveAllRobotsToDest(Map<IGridRobot, GridCell> robot2dest){
		HashSet<IGridRobot> finishedRobots = new HashSet<IGridRobot>();
		//long longestDelay = 0;
		PathPlanner planner = new PathPlanner();
		while(finishedRobots != robot2dest.keySet()){
			for (Map.Entry<IGridRobot, GridCell> entry: robot2dest.entrySet()){
				if (!finishedRobots.contains(entry.getKey())){
					Entry<IGridRobot, Direction> dir = planner.nextStepOneRobot(this, entry);
					if (dir != null){
						dir.getKey().step(dir.getValue());
					}
					else{
						finishedRobots.add(entry.getKey());
					}
				}
			}
			//long longestDelay = 0;
			//try {
			//	Thread.sleep((long) (longestDelay * 1.05));
			//} catch (InterruptedException e) { }
		}
	}
	*/
}
