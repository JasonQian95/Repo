package edu.toronto.csc301.util;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import edu.toronto.csc301.GridCell;
import edu.toronto.csc301.IBasicRobot;
import edu.toronto.csc301.IGridRobot;
import edu.toronto.csc301.SetupTest;
import edu.toronto.csc301.IGridRobot.Direction;
import edu.toronto.csc301.util.Helpers;


public class TestUtil {

	
	private static Random random = new Random(System.currentTimeMillis());
	
	
	/**
	 * Create a new instance of your BasicRobot implementation.
	 * 
	 * @param x Initial x-coordinate of the robot
	 * @param y Initial y-coordinate of the robot
	 * @param rotation Initial rotation of the robot
	 */
	public static IBasicRobot createBasicRobot(double x, double y, int rotation) throws Exception{
		return Helpers.newInstance(SetupTest.BASIC_ROBOT, 
				new Class<?>[]{Double.TYPE, Double.TYPE, Integer.TYPE}, 
				x, y, rotation);
	}
	
	
	/**
	 * Create a new instance of your GridRobot1 implementation.
	 * 
	 * @param robot A basic robot
	 * @param gridCellSize The size of a grid cell (i.e. the size of each 
	 *           grid cell is gridCellSize x gridCellSize).  
	 */
	public static IGridRobot createGridRobot1(IBasicRobot robot, int gridCellSize) throws Exception{
		return Helpers.newInstance(SetupTest.GRID_ROBOT1, 
				new Class<?>[]{IBasicRobot.class, Integer.TYPE}, 
				robot, gridCellSize);
	}
	
	
	/**
	 * Create a new instance of your GridRobot2 implementation.
	 * 
	 * @param initialLocation The initial location of the robot.
	 * @param initialDirection The direction the robot is facing initially.
	 * @param gridCellSize The size of a grid cell (i.e. the size of each 
	 *           grid cell is gridCellSize x gridCellSize).
	 */
	public static IGridRobot createGridRobot2(GridCell initialLocation, 
			Direction initialDirection, int gridCellSize) throws Exception{
		return Helpers.newInstance(SetupTest.GRID_ROBOT2, 
				new Class<?>[]{GridCell.class, Direction.class, Integer.TYPE}, 
				initialLocation, initialDirection, gridCellSize);
	}
	
	
	// ------------------------------------------------------------------------
	
	
	public static void assertDestinationAfterTakingSteps(IGridRobot robot, 
			List<Direction> steps, GridCell dest) throws Exception {
		
		for (Direction d : steps){
			robot.step(d);
		}
		assertEquals(dest, robot.getLocation());
	}
	
	
	public static void assertBasicRobotLocation(IBasicRobot robot, double x, double y) {
		assertEquals(x, robot.getXCoordinate(), 0.01);
		assertEquals(y, robot.getYCoordinate(), 0.01);
	}
	

	// ------------------------------------------------------------------------	

	public static GridCell randomGridCell() {
		return GridCell.at(randomInt(-10000, 10000), randomInt(-10000, 10000));
	}
	
	public static Direction randomDirection() {
		Direction[] directions = Direction.values();
		return directions[randomInt(0, directions.length)];
	}

	
	// ------------------------------------------------------------------------
	
	/**
	 * Return a random integer in the range [a, b).
	 * That is, including a and excluding b.
	 */
	public static int randomInt(int a, int b){
		return a + random.nextInt(b - a);
	}		
	

	public static GridCell oneCellOver(GridCell fromCell, Direction direction) {
		switch (direction) {
		case NORTH:
			return GridCell.at(fromCell.x, fromCell.y + 1);
		case EAST:
			return GridCell.at(fromCell.x + 1, fromCell.y);
		case SOUTH:
			return GridCell.at(fromCell.x, fromCell.y - 1);
		case WEST:
			return GridCell.at(fromCell.x - 1, fromCell.y);
		default:
			return null;
		}
	}

}
