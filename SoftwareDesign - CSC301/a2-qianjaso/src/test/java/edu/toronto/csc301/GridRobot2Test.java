package edu.toronto.csc301;

import static edu.toronto.csc301.util.TestUtil.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.toronto.csc301.IGridRobot.Direction;
import edu.toronto.csc301.util.TestUtil;

public class GridRobot2Test extends AbsGridRobotTest {



	@Override
	protected IGridRobot createRobotWithRandomStartingPoint() throws Exception {
		return createGridRobot2(
				randomGridCell(), randomDirection(), randomInt(2, 100));
	}


	// ------------------------------------------------------------------------


	@Test
	public void createInstanceAndCheckXCoordinate_1() throws Exception{
		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				GridCell.at(0, 0), Direction.NORTH, 10);
		assertEquals(0, robot.getXCoordinate(), 0.001);
	}

	@Test
	public void createInstanceAndCheckXCoordinate_2() throws Exception{
		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				GridCell.at(3, 0), Direction.NORTH, 10);
		assertEquals(30, robot.getXCoordinate(), 0.001);
	}

	@Test
	public void createInstanceAndCheckXCoordinate_3() throws Exception{
		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				GridCell.at(3, 0), Direction.NORTH, 14);
		assertEquals(42, robot.getXCoordinate(), 0.001);
	}

	@Test
	public void createInstanceAndCheckXCoordinate_4() throws Exception{
		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				GridCell.at(-9, 0), Direction.NORTH, 200);
		assertEquals(-1800, robot.getXCoordinate(), 0.001);
	}

	@Test
	public void createInstanceAndCheckXCoordinate_5() throws Exception{
		GridCell location = randomGridCell();
		int gridCellSize = randomInt(2, 100);

		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				location, randomDirection(), gridCellSize);
		assertEquals(location.x * gridCellSize, robot.getXCoordinate(), 0.001);
	}


	// ------------------------------------------------------------------------


	@Test
	public void createInstanceAndCheckYCoordinate_test1() throws Exception{
		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				GridCell.at(0, 0), Direction.NORTH, 10);
		assertEquals(0, robot.getYCoordinate(), 0.001);
	}


	@Test
	public void createInstanceAndCheckYCoordinate_2() throws Exception{
		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				GridCell.at(0, 3), Direction.NORTH, 10);
		assertEquals(30, robot.getYCoordinate(), 0.001);
	}


	@Test
	public void createInstanceAndCheckYCoordinate_3() throws Exception{
		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				GridCell.at(0, 3), Direction.NORTH, 14);
		assertEquals(42, robot.getYCoordinate(), 0.001);
	}


	@Test
	public void createInstanceAndCheckYCoordinate_4() throws Exception{
		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				GridCell.at(0, -9), Direction.NORTH, 200);
		assertEquals(-1800, robot.getYCoordinate(), 0.001);
	}


	@Test
	public void createInstanceAndCheckYCoordinate_5() throws Exception{
		GridCell location = randomGridCell();
		int gridCellSize  = randomInt(2, 100);

		IBasicRobot robot = (IBasicRobot) TestUtil
				.createGridRobot2(location, randomDirection(), gridCellSize);
		assertEquals(location.y * gridCellSize, robot.getYCoordinate(), 0.001);
	}


	// ------------------------------------------------------------------------



	@Test
	public void createNorthFacingInstanceAndCheckRotation() throws Exception{
		createInstanceAndCheckRotation(Direction.NORTH, 0);
	}

	@Test
	public void createEastFacingInstanceAndCheckRotation() throws Exception{
		createInstanceAndCheckRotation(Direction.EAST, 90);
	}

	@Test
	public void createSouthFacingInstanceAndCheckRotation() throws Exception{
		createInstanceAndCheckRotation(Direction.SOUTH, 180);
	}

	@Test
	public void createWestFacingInstanceAndCheckRotation() throws Exception{
		createInstanceAndCheckRotation(Direction.WEST, 270);
	}


	private void createInstanceAndCheckRotation(Direction direction, int expectedRotation) throws Exception{
		int gridCellSize  = randomInt(2, 100);		
		IBasicRobot robot = (IBasicRobot) createGridRobot2(
				randomGridCell(), direction, gridCellSize);
		assertEquals(expectedRotation, robot.getRotation());
	}



	// ------------------------------------------------------------------------


	@Test(expected=NullPointerException.class)
	public void createInstanceWithNullLocation() throws Exception{
		int gridCellSize = randomInt(2, 100);
		createGridRobot2(null, randomDirection(), gridCellSize);
	}

	@Test(expected=NullPointerException.class)
	public void createInstanceWithNullDirection() throws Exception{
		int gridCellSize = randomInt(2, 100);
		createGridRobot2(randomGridCell(), null, gridCellSize);
	}

	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithZeroGridCellSize() throws Exception{
		createGridRobot2(randomGridCell(), randomDirection(), 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithNegativeGridCellSize() throws Exception{
		int gridCellSize = randomInt(-100, -1);
		createGridRobot2(randomGridCell(), randomDirection(), gridCellSize);
	}



	// ------------------------------------------------------------------------



	@Test
	public void faceNorthThenCheckRotation() throws Exception {
		faceThenCheckRotation(Direction.NORTH, 0);
	}

	@Test
	public void faceEastThenCheckRotation() throws Exception {
		faceThenCheckRotation(Direction.EAST, 90);
	}

	@Test
	public void faceSouthThenCheckRotation() throws Exception {
		faceThenCheckRotation(Direction.SOUTH, 180);
	}

	@Test
	public void faceWestThenCheckRotation() throws Exception {
		faceThenCheckRotation(Direction.WEST, 270);
	}


	private void faceThenCheckRotation(Direction direction, int expectedRotation) throws Exception {
		IGridRobot robot = createRobotWithRandomStartingPoint();
		robot.face(direction);
		assertEquals(expectedRotation, ((IBasicRobot) robot).getRotation());
	}


	
	// ------------------------------------------------------------------------


	@Test(expected=IllegalArgumentException.class)
	public void rotateRightRequiresDegreesToBeAMultipleOf90() throws Exception {
		IGridRobot robot = createRobotWithRandomStartingPoint();
		int r = (90 * randomInt(-10, 10)) + randomInt(1, 90); 
		((IBasicRobot) robot).rotateRight(r);
	}

	@Test(expected=IllegalArgumentException.class)
	public void rotateLeftRequiresDegreesToBeAMultipleOf90() throws Exception {
		IGridRobot robot = createRobotWithRandomStartingPoint();
		int r = (90 * randomInt(-10, 10)) + randomInt(1, 90);
		((IBasicRobot) robot).rotateLeft(r);
	}


	// ------------------------------------------------------------------------
	

	@Test
	public void rotateThenCheckDirection_1() throws Exception {
		IGridRobot robot = createGridRobot2(
				randomGridCell(), Direction.NORTH , randomInt(2, 100));
		((IBasicRobot) robot).rotateRight(90);
		assertEquals(Direction.EAST, robot.getFacingDirection());
	}

	@Test
	public void rotateThenCheckDirection_2() throws Exception {
		IGridRobot robot = createGridRobot2(
				randomGridCell(), Direction.SOUTH , randomInt(2, 100));
		((IBasicRobot) robot).rotateLeft(180);
		assertEquals(Direction.NORTH, robot.getFacingDirection());
	}

	@Test
	public void rotateThenCheckDirection_3() throws Exception {
		IGridRobot robot = createGridRobot2(
				randomGridCell(), Direction.EAST , randomInt(2, 100));
		((IBasicRobot) robot).rotateLeft(90);
		assertEquals(Direction.NORTH, robot.getFacingDirection());
	}

	@Test
	public void rotateThenCheckDirection_4() throws Exception {
		IGridRobot robot = createGridRobot2(
				randomGridCell(), Direction.WEST , randomInt(2, 100));
		((IBasicRobot) robot).rotateRight(450);
		assertEquals(Direction.NORTH, robot.getFacingDirection());
	}
	
	@Test
	public void rotateThenCheckDirection_5() throws Exception {
		IGridRobot robot = createGridRobot2(
				randomGridCell(), Direction.EAST , randomInt(2, 100));
		((IBasicRobot) robot).rotateRight(-900);
		assertEquals(Direction.WEST, robot.getFacingDirection());
	}
	



	// ------------------------------------------------------------------------

	
	@Test
	public void stepAndCheckCoordinates_1() throws Exception{
		GridCell initialLocation = GridCell.at(0, 0);
		Direction initialDirection = Direction.EAST;
		int gridCellSize = 42;
		
		IGridRobot robot = createGridRobot2(initialLocation , initialDirection, gridCellSize);
		robot.step(Direction.SOUTH);
		assertBasicRobotLocation((IBasicRobot) robot, 0, -42);
	}
	
	@Test
	public void stepAndCheckCoordinates_2() throws Exception{
		GridCell initialLocation = GridCell.at(-30, 1);
		Direction initialDirection = Direction.NORTH;
		int gridCellSize = 10;
		
		IGridRobot robot = createGridRobot2(initialLocation , initialDirection, gridCellSize);
		robot.step(Direction.WEST);
		assertBasicRobotLocation((IBasicRobot) robot, -310, 10);
	}
	
	@Test
	public void stepAndCheckCoordinates_3() throws Exception{
		GridCell initialLocation = GridCell.at(9, -21);
		Direction initialDirection = Direction.SOUTH;
		int gridCellSize = 5;
		
		IGridRobot robot = createGridRobot2(initialLocation , initialDirection, gridCellSize);
		robot.step(Direction.EAST);
		assertBasicRobotLocation((IBasicRobot) robot, 50, -105);
	}
	
	@Test
	public void stepAndCheckCoordinates_4() throws Exception{
		// Select random starting point, direction and grid cell-size.
		GridCell initialLocation = randomGridCell();
		Direction initialDirection = randomDirection();
		Direction step = randomDirection();
		int gridCellSize = randomInt(2, 100);
		
		IGridRobot robot = createGridRobot2(initialLocation , initialDirection, gridCellSize);

		// Compute where we expect the robot to end up
		IBasicRobot bRobot = (IBasicRobot) robot;
		double expectedX = bRobot.getXCoordinate();
		double expectedY = bRobot.getYCoordinate();
		switch (step) {
		case NORTH:
			expectedY += gridCellSize;
			break;
		case EAST:
			expectedX += gridCellSize;
			break;
		case SOUTH:
			expectedY -= gridCellSize;
			break;
		case WEST:
			expectedX -= gridCellSize;
			break;
		}

		// Step and check that the x/y coordinates are as expected
		robot.step(step);
		assertBasicRobotLocation(bRobot, expectedX, expectedY);
	}
	
	
	// ------------------------------------------------------------------------
	
	
	@Test
	public void callMoveThenCheckGridRobotLocation_1() throws Exception{
		int gridCellSize = randomInt(2, 100);
		IGridRobot robot = createGridRobot2(
				GridCell.at(0, 0) , Direction.WEST, gridCellSize);
		
		((IBasicRobot) robot).moveForward(3 * gridCellSize);
		assertEquals(GridCell.at(-3, 0), robot.getLocation());
	}
	
	@Test
	public void callMoveThenCheckGridRobotLocation_2() throws Exception{
		int gridCellSize = randomInt(2, 100);
		IGridRobot robot = createGridRobot2(
				GridCell.at(0, 0) , Direction.NORTH, gridCellSize);
		
		((IBasicRobot) robot).moveForward(7 * gridCellSize);
		assertEquals(GridCell.at(0, 7), robot.getLocation());
	}
	
	
	@Test
	public void callMoveThenCheckGridRobotLocation_3() throws Exception{
		GridCell location = randomGridCell();
		int cellSize = randomInt(2, 100);
		Direction direction = randomDirection();
		IGridRobot robot = createGridRobot2(location , direction, cellSize);
		
		int i = randomInt(1, 100);
		((IBasicRobot) robot).moveForward(i * cellSize);
		
		GridCell expectedLocation = null;
		switch (direction) {
		case NORTH:
			expectedLocation = GridCell.at(location.x, location.y + i);
			break;
		case EAST:
			expectedLocation = GridCell.at(location.x + i, location.y);
			break;
		case SOUTH:
			expectedLocation = GridCell.at(location.x, location.y - i);
			break;
		case WEST:
			expectedLocation = GridCell.at(location.x - i, location.y);
			break;
		}
		
		assertEquals(expectedLocation, robot.getLocation());
	}
	

	// ------------------------------------------------------------------------
	
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldNotBeAllowedToMoveOffTheGrid() throws Exception{
		int gridCellSize = randomInt(2, 100);
		IGridRobot robot = createGridRobot2(
				randomGridCell() , randomDirection(), gridCellSize);
		
		// Generate a random integer that is NOT a multiple of gridCellSize
		int x = randomInt(1, 10000);
		if(x % gridCellSize == 0){
			x += randomInt(1, gridCellSize);
		}
		
		((IBasicRobot) robot).moveForward(x);
	}
	
	
}
