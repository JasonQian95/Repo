package edu.toronto.csc301.warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.robot.GridRobot;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;

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
		GridRobot robot = new GridRobot(initialLocation);
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
}
