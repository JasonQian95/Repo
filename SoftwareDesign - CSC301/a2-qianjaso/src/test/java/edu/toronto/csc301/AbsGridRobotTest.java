package edu.toronto.csc301;

import static edu.toronto.csc301.util.TestUtil.randomDirection;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import edu.toronto.csc301.IGridRobot.Direction;
import edu.toronto.csc301.util.TestUtil;

/**
 * A class containing tests that are common to both IGridRobot implementations.
 */
public abstract class AbsGridRobotTest {

	
	@Rule
    public Timeout globalTimeout = Timeout.seconds(2);
	
	
	protected abstract IGridRobot createRobotWithRandomStartingPoint() throws Exception;
	
	
	// ------------------------------------------------------------------------


	@Test
	public void faceMultipleTimes() throws Exception {
		IGridRobot robot = createRobotWithRandomStartingPoint();
		int n = 42; // Arbitrarily chosen
		for (int i = 0; i < n; i++) {
			faceAndCheckDirection(robot, randomDirection());
		}
	}

	/**
	 * Convenience helper method.
	 */
	protected void faceAndCheckDirection(IGridRobot robot, Direction direction) {
		robot.face(direction);
		assertEquals(direction, robot.getFacingDirection());
	}
	
	
	// ------------------------------------------------------------------------
	
	
	@Test
	public void stepNorth() throws Exception{
		stepOnceAndCheckLocation(createRobotWithRandomStartingPoint(), Direction.NORTH);
	}
	
	@Test
	public void stepEast() throws Exception{
		stepOnceAndCheckLocation(createRobotWithRandomStartingPoint(), Direction.EAST);
	}
	
	@Test
	public void stepSouth() throws Exception{
		stepOnceAndCheckLocation(createRobotWithRandomStartingPoint(), Direction.SOUTH);
	}
	
	@Test
	public void stepWest() throws Exception{
		stepOnceAndCheckLocation(createRobotWithRandomStartingPoint(), Direction.WEST);
	}
	
	
	@Test
	public void stepInEachDirectionStartingFromEachDirection() throws Exception{
		Direction[] directions = Direction.values();
		for (int i = 0; i < directions.length; i++) {
			for (int j = 0; j < directions.length; j++) {
				IGridRobot robot = createRobotWithRandomStartingPoint();
				robot.face(directions[i]);
				stepOnceAndCheckLocation(robot, directions[j]);
			}
		}
	}
	
	
	private void stepOnceAndCheckLocation(IGridRobot robot, Direction direction) throws Exception{
		GridCell start = robot.getLocation();
		robot.step(direction);
		assertEquals(TestUtil.oneCellOver(start, direction), robot.getLocation());
	}
	
	// ------------------------------------------------------------------------
	
	
	@Test
	public void multipleSteps_test1() throws Exception{
		IGridRobot robot = createRobotWithRandomStartingPoint();
		
		// If the robot takes one step in each direction (N/E/S/W) ...
		List<Direction> steps = Arrays.asList(Direction.values());
		Collections.shuffle(steps);
		// We expect it to end up where it started
		TestUtil.assertDestinationAfterTakingSteps(
				robot, steps, robot.getLocation());
	}
	
	
	@Test
	public void multipleSteps_test2() throws Exception{
		IGridRobot robot = createRobotWithRandomStartingPoint();
		List<Direction> steps = Arrays.asList(
				Direction.NORTH, Direction.NORTH, Direction.EAST);
		
		GridCell start = robot.getLocation();
		GridCell dest  = GridCell.at(start.x + 1, start.y + 2);
		TestUtil.assertDestinationAfterTakingSteps(robot, steps, dest);
	}
	
	
	@Test
	public void multipleSteps_test3() throws Exception{
		IGridRobot robot = createRobotWithRandomStartingPoint();
		List<Direction> steps = Arrays.asList(
				Direction.NORTH, Direction.WEST, 
				Direction.NORTH, Direction.EAST);
		
		GridCell start = robot.getLocation();
		GridCell dest  = GridCell.at(start.x, start.y + 2);
		TestUtil.assertDestinationAfterTakingSteps(robot, steps, dest);
	}
	
	
	@Test
	public void multipleSteps_test4() throws Exception{
		IGridRobot robot = createRobotWithRandomStartingPoint();
		
		List<Direction> steps = new ArrayList<Direction>();
		int n = 42; // arbitrarily chosen
		for (int i = 0; i < n; i++) {
			steps.add(Direction.NORTH);
			steps.add(Direction.SOUTH);
		}
		Collections.shuffle(steps);
		
		TestUtil.assertDestinationAfterTakingSteps(
				robot, steps, robot.getLocation());
	}
	
	
	@Test
	public void multipleSteps_test5() throws Exception{
		IGridRobot robot = createRobotWithRandomStartingPoint();
		
		List<Direction> steps = new ArrayList<Direction>();
		int n = 42; // arbitrarily chosen
		for (int i = 0; i < n; i++) {
			steps.add(Direction.EAST);
			steps.add(Direction.WEST);
		}
		Collections.shuffle(steps);
		
		TestUtil.assertDestinationAfterTakingSteps(
				robot, steps, robot.getLocation());
	}
	
	
	@Test
	public void multipleSteps_test6() throws Exception{
		IGridRobot robot = createRobotWithRandomStartingPoint();
		
		List<Direction> steps = new ArrayList<Direction>();
		int n = 42; // arbitrarily chosen
		for (int i = 0; i < n; i++) {
			steps.add(randomDirection());
		}
		
		GridCell start = robot.getLocation();
		
		// Let's compute the expected destination ...
		int destX = start.x;
		int destY = start.y;
		for(Direction d : steps){
			switch (d) {
			case NORTH:
				destY++; break;
			case EAST:
				destX++; break;
			case SOUTH:
				destY--; break;
			case WEST:
				destX--; break;
			}
		}
		
		TestUtil.assertDestinationAfterTakingSteps(
				robot, steps, GridCell.at(destX, destY));
	}
	
	
}
