package edu.toronto.csc301;


import static edu.toronto.csc301.util.TestUtil.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.toronto.csc301.IGridRobot.Direction;



public class GridRobot1Test extends AbsGridRobotTest{

	
	
	@Override
	protected IGridRobot createRobotWithRandomStartingPoint() throws Exception {
		int cellSize = randomInt(2, 100);
		double x = cellSize * randomInt(-1000, 1000);
		double y = cellSize * randomInt(-1000, 1000);
		return createGridRobot1(createBasicRobotWithRandomRotation(x, y), cellSize);
	}
	
	
	
	// ========================================================================
	
	
	
	@Test(expected=NullPointerException.class)
	public void createInstanceWithNullBasicRobot() throws Exception { 
		createGridRobot1(null, 100);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithZeroGridCellSize() throws Exception{
		createGridRobot1(createBasicRobot(0, 0, 0), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithNegativeGridCellSize() throws Exception{
		createGridRobot1(createBasicRobot(0, 0, 0), randomInt(-1000, -1));
	}
	
	
	// ------------------------------------------------------------------------
	

	
	@Test
	public void createInstanceAndCheckLocation_1() throws Exception {
		IGridRobot robot = createGridRobot1(createBasicRobot(0, 0, 0), 100);
		assertEquals(GridCell.at(0,0), robot.getLocation());
	}
	
	
	@Test
	public void createInstanceAndCheckLocation_2() throws Exception {
		IGridRobot robot = createGridRobot1(createBasicRobot(200, -300, 0), 100);
		assertEquals(GridCell.at(2,-3), robot.getLocation());
	}
	
	
	@Test
	public void createInstanceAndCheckLocation_3() throws Exception {
		IGridRobot robot = createGridRobot1(createBasicRobot(84, -126, 0), 42);
		assertEquals(GridCell.at(2,-3), robot.getLocation());
	}
	
	
	@Test
	public void createInstanceAndCheckLocation_4() throws Exception {
		int gridCellSize = randomInt(2, 100);
		int i = randomInt(-100, 100);
		int j = randomInt(-100, 100);
		
		double x = i * gridCellSize;
		double y = j * gridCellSize;
		IBasicRobot basicRobot = createBasicRobot(x, y, 0);
		IGridRobot robot = createGridRobot1(basicRobot, gridCellSize);
		
		assertEquals(GridCell.at(i,j), robot.getLocation());
	}
	
	
	
	// ------------------------------------------------------------------------

	
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithInvalidLocation_1() throws Exception { 
		createGridRobot1(createBasicRobot(0, 99, 0), 100);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithInvalidLocation_2() throws Exception { 
		createGridRobot1(createBasicRobot(50, 200, 0), 100);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithInvalidLocation_3() throws Exception { 
		createGridRobot1(createBasicRobot(7, 57, 0), 42);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithInvalidLocation_4() throws Exception {
		int cellSize = randomInt(2, 100);
		// Generate a random invalid x-coordinate
		double x = (cellSize * randomInt(-1000, 1000)) + randomInt(1, cellSize);
		createGridRobot1(createBasicRobotWithRandomRotation(x, 0), cellSize);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithInvalidLocation_5() throws Exception {
		int cellSize = randomInt(2, 100);
		// Generate a random invalid y-coordinate
		double y = (cellSize * randomInt(-1000, 1000)) + randomInt(1, cellSize);
		createGridRobot1(createBasicRobotWithRandomRotation(0, y), cellSize);
	}

	
	// ------------------------------------------------------------------------
	

	@Test
	public void createInstanceAndCheckDirection_North() throws Exception {
		IGridRobot robot = createGridRobot1(createBasicRobot(0, 0, 0), 100);
		assertEquals(Direction.NORTH, robot.getFacingDirection());
	}
	
	
	@Test
	public void createInstanceAndCheckDirection_East() throws Exception {
		IGridRobot robot = createGridRobot1(createBasicRobot(0, 0, 90), 100);
		assertEquals(Direction.EAST, robot.getFacingDirection());
	}
	
	
	@Test
	public void createInstanceAndCheckDirection_South() throws Exception {
		IGridRobot robot = createGridRobot1(createBasicRobot(0, 0, 180), 100);
		assertEquals(Direction.SOUTH, robot.getFacingDirection());
	}
	
	
	@Test
	public void createInstanceAndCheckDirection_West() throws Exception {
		IGridRobot robot = createGridRobot1(createBasicRobot(0, 0, 270), 100);
		assertEquals(Direction.WEST, robot.getFacingDirection());
	}

	
	// ------------------------------------------------------------------------
	
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithInvalidRotation_1() throws Exception {
		// Rotation must be either 0, 90, 180 or 270 (i.e. North/East/South/West) 
		createGridRobot1(createBasicRobot(0, 0, 1), 100);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithInvalidRotation_2() throws Exception {
		// Rotation must be either 0, 90, 180 or 270 (i.e. North/East/South/West) 
		createGridRobot1(createBasicRobot(0, 0, 50), 100);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createInstanceWithInvalidRotation_3() throws Exception {
		// Rotation must be either 0, 90, 180 or 270 (i.e. North/East/South/West) 
		createGridRobot1(createBasicRobot(0, 0, 269), 100);
	}
	
	

	// ------------------------------------------------------------------------
	
	
	
	@Test
	public void callingFaceOnTheGridRobotShouldRotateTheBasicRobot() throws Exception {
		IBasicRobot basicRobot = createBasicRobotWithRandomRotation(0, 0);
		IGridRobot robot = createGridRobot1(basicRobot, 100);
		
		Direction direction = randomDirection();
		robot.face(direction);
		
		switch (direction) {
		case NORTH:
			assertEquals(0, basicRobot.getRotation());
			break;
		case EAST:
			assertEquals(90, basicRobot.getRotation());
			break;
		case SOUTH:
			assertEquals(180, basicRobot.getRotation());
			break;
		case WEST:
			assertEquals(270, basicRobot.getRotation());
			break;
		}
	}
	
	
	@Test
	public void rotatingTheBasicRobotShouldAffectTheGridRobotFacingDirection_1() throws Exception {
		IBasicRobot basicRobot = createBasicRobot(0, 0, 0);
		IGridRobot robot = createGridRobot1(basicRobot, 100);
		
		assertEquals(Direction.NORTH, robot.getFacingDirection());
		basicRobot.rotateRight(90);
		assertEquals(Direction.EAST, robot.getFacingDirection());
	}
	
	@Test
	public void rotatingTheBasicRobotShouldAffectTheGridRobotFacingDirection_2() throws Exception {
		IBasicRobot basicRobot = createBasicRobot(0, 0, 180);
		IGridRobot robot = createGridRobot1(basicRobot, 100);
		
		assertEquals(Direction.SOUTH, robot.getFacingDirection());
		basicRobot.rotateRight(180);
		assertEquals(Direction.NORTH, robot.getFacingDirection());
	}
	
	@Test
	public void rotatingTheBasicRobotShouldAffectTheGridRobotFacingDirection_3() throws Exception {
		IBasicRobot basicRobot = createBasicRobot(0, 0, 270);
		IGridRobot robot = createGridRobot1(basicRobot, 100);
		
		assertEquals(Direction.WEST, robot.getFacingDirection());
		basicRobot.rotateLeft(90);
		assertEquals(Direction.SOUTH, robot.getFacingDirection());
	}
	
	
	// ------------------------------------------------------------------------
	
	@Test
	public void movingTheBasicRobotShouldAffectTheGridRobotLocation_1() throws Exception{
		int gridCellSize = randomInt(2, 100);
		IBasicRobot basicRobot = createBasicRobot(0, 0, 0);
		IGridRobot robot = createGridRobot1(basicRobot, gridCellSize);
		
		int i = randomInt(1, 10);
		basicRobot.moveForward(i * gridCellSize);
		assertEquals(GridCell.at(0, i), robot.getLocation());
	}
	
	
	@Test
	public void movingTheBasicRobotShouldAffectTheGridRobotLocation_2() throws Exception{
		int gridCellSize = randomInt(2, 100);
		IBasicRobot basicRobot = createBasicRobot(0, 0, 270);
		IGridRobot robot = createGridRobot1(basicRobot, gridCellSize);
		
		int i = randomInt(1, 10);
		basicRobot.moveForward(i * gridCellSize);
		assertEquals(GridCell.at(-i, 0), robot.getLocation());
	}
	
	@Test
	public void movingTheBasicRobotShouldAffectTheGridRobotLocation_3() throws Exception{
		int gridCellSize = randomInt(2, 100);
		IBasicRobot basicRobot = createBasicRobot(0, 0, 180);
		IGridRobot robot = createGridRobot1(basicRobot, gridCellSize);
		
		int i = randomInt(1, 10);
		basicRobot.moveForward(i * gridCellSize);
		assertEquals(GridCell.at(0, -i), robot.getLocation());
		
		robot.face(Direction.EAST);
		assertEquals(90, basicRobot.getRotation());
		
		int j = randomInt(1, 10);
		basicRobot.moveForward(j * gridCellSize);
		assertEquals(GridCell.at(j, -i), robot.getLocation());
	}
	
	
	
	@Test
	public void steppingNorthShouldAffectBasicRobotLocation() throws Exception{
		stepAndCheckBasicRobotCoordinates(Direction.NORTH);
	}
	
	@Test
	public void steppingEastShouldAffectBasicRobotLocation() throws Exception{
		stepAndCheckBasicRobotCoordinates(Direction.EAST);
	}
	
	@Test
	public void steppingSouthShouldAffectBasicRobotLocation() throws Exception{
		stepAndCheckBasicRobotCoordinates(Direction.SOUTH);
	}
	
	@Test
	public void steppingWestShouldAffectBasicRobotLocation() throws Exception{
		stepAndCheckBasicRobotCoordinates(Direction.WEST);
	}
	
	
	
	// ================================== Helpers =============================
	
	
	
	
	private void stepAndCheckBasicRobotCoordinates(Direction direction) throws Exception{
		int cellSize = randomInt(2, 100);
		double x = cellSize * randomInt(-1000, 1000);
		double y = cellSize * randomInt(-1000, 1000);
		
		// Create the underlying basic robot, and the higher-level grid robot
		IBasicRobot basicRobot = createBasicRobotWithRandomRotation(x, y); 
		IGridRobot robot = createGridRobot1(basicRobot, cellSize);
		
		// Take a step, and check the location of the underlying basic robot
		robot.step(direction);
		switch (direction) {
		case NORTH:
			assertBasicRobotLocation(basicRobot, x, y + cellSize);
			break;
		case EAST:
			assertBasicRobotLocation(basicRobot, x + cellSize, y);
			break;
		case SOUTH:
			assertBasicRobotLocation(basicRobot, x, y - cellSize);
			break;
		case WEST:
			assertBasicRobotLocation(basicRobot, x - cellSize, y);
			break;
		default:
			throw new Exception("Unsupported direction, " + direction);
		}
	}
	

	
	private IBasicRobot createBasicRobotWithRandomRotation(double x, double y) throws Exception{
		return createBasicRobot(x, y, randomInt(0, 4) * 90);
	}

	
}
