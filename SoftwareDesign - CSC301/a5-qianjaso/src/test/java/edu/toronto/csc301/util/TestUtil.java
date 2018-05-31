package edu.toronto.csc301.util;

import java.util.Random;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.robot.GridRobot;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;
import edu.toronto.csc301.warehouse.IPathPlanner;
import edu.toronto.csc301.warehouse.IWarehouse;
import edu.toronto.csc301.warehouse.PathPlanner;
import edu.toronto.csc301.warehouse.Rack;
import edu.toronto.csc301.warehouse.Warehouse;

/**
 * Helper functions used by other test classes.
 */
public class TestUtil {
	
	public static final double waitTimeMult = 1.05;
	
	private static Random random = new Random();
	
	

	public static IGridRobot createGridRobot(GridCell initialLocation) throws Exception {
		return new GridRobot(initialLocation);
	}

	
	public static IWarehouse createWarehouse(IGrid<Rack> floorPlan) throws Exception {
		return new Warehouse(floorPlan);
	}
	
	public static IPathPlanner createPathPlanner() throws Exception {
		return new PathPlanner();
	}
	
	
	
	
	public static Direction randomDirection(){
		Direction[] directions = Direction.values();
		return directions[random.nextInt(directions.length)];
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
	
	//NEW
	public static void waitOnRobot(){
		try {
			Thread.sleep((long) (GridRobot.defaultDelayInMilliseconds * TestUtil.waitTimeMult));
		} catch (InterruptedException e) { }
	}

}
