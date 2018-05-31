package edu.toronto.csc301.tests;

import static edu.toronto.csc301.util.TestUtil.createPathPlanner;
import static edu.toronto.csc301.util.TestUtil.createWarehouse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.robot.GridRobot;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;
import edu.toronto.csc301.util.SimpleGridImpl;
import edu.toronto.csc301.util.TestUtil;
import edu.toronto.csc301.warehouse.IPathPlanner;
import edu.toronto.csc301.warehouse.IWarehouse;
import edu.toronto.csc301.warehouse.PathPlanner;

public class MultipleRobotTests {

	// Helpers
	// ================================================================================================================
	
	/**
	 * A floor plan that looks like this:
	 * 
	 * OOOOOO
	 * OOOOOO
	 * OO  OO
	 * OO  OO
	 * OOOOOO
	 * OOOOOO
	 * 
	 * (each O represents an empty grid cell)
	 */
	public static <T> SimpleGridImpl<T> createOShapedFloorPlan(){
		Map<GridCell, T> cell2item = new HashMap<GridCell,T>();
		
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				if (!((x == 2 || x == 3) && (y == 2 || y == 3))){
					cell2item.put(GridCell.at(x, y), null);
				}
			}
		}
		
		return new SimpleGridImpl<T>(cell2item);
	}
	
	/**
	 * A floor plan that looks like this:
	 * 
	 * OOO OOO
	 * OOOOOOO
	 * OOO OOO
	 * 
	 * (each O represents an empty grid cell)
	 */
	public static <T> SimpleGridImpl<T> createHShapedFloorPlan(){
		Map<GridCell, T> cell2item = new HashMap<GridCell,T>();
		
		// 3x3 square space (sw corner at 0,0)
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				cell2item.put(GridCell.at(x, y), null);
			}
		}
		
		// A narrow hallway (1 cell wide and one cell long)
		cell2item.put(GridCell.at(3, 1), null);
		
		// Another 3x3 square space (sw corner at 4,0)
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				cell2item.put(GridCell.at(4 + x, y), null);
			}
		}
		
		return new SimpleGridImpl<T>(cell2item);
	}
	
	/**
	 * A floor plan that looks like this:
	 * 
	 * OOOOOO
	 * OOOOOO
	 * OOOOOO
	 * OOOOOO
	 * OOOOOO
	 * OOOOOO
	 * 
	 * (each O represents an empty grid cell)
	 */
	public static <T> SimpleGridImpl<T> createBoxShapedFloorPlan(){
		Map<GridCell, T> cell2item = new HashMap<GridCell,T>();
		
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				cell2item.put(GridCell.at(x, y), null);
			}
		}
		
		return new SimpleGridImpl<T>(cell2item);
	}
	
	/**
	 * A floor plan that looks like this:
	 * 
	 * OO
	 * OO
	 * 
	 * (each O represents an empty grid cell)
	 */
	public static <T> SimpleGridImpl<T> createSmallBoxShapedFloorPlan(){
		Map<GridCell, T> cell2item = new HashMap<GridCell,T>();
		
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				cell2item.put(GridCell.at(x, y), null);
			}
		}
		
		return new SimpleGridImpl<T>(cell2item);
	}
	/**
	 * A floor plan that looks like this:
	 * 
	 * OOO OO
	 *   O O
	 * OOO O
	 * O O OO
	 * O OO O
	 * O  OOO
	 * 
	 * (each O represents an empty grid cell)
	 */
	public static <T> SimpleGridImpl<T> createMazeShapedFloorPlan(){
		Map<GridCell, T> cell2item = new HashMap<GridCell,T>();
		
		cell2item.put(GridCell.at(0, 0), null);
		cell2item.put(GridCell.at(0, 1), null);
		cell2item.put(GridCell.at(0, 2), null);
		cell2item.put(GridCell.at(0, 3), null);
		cell2item.put(GridCell.at(1, 3), null);
		cell2item.put(GridCell.at(2, 3), null);
		cell2item.put(GridCell.at(2, 4), null);
		cell2item.put(GridCell.at(2, 5), null);
		cell2item.put(GridCell.at(1, 5), null);
		cell2item.put(GridCell.at(0, 5), null);
		cell2item.put(GridCell.at(2, 2), null);
		cell2item.put(GridCell.at(2, 1), null);
		cell2item.put(GridCell.at(3, 1), null);
		cell2item.put(GridCell.at(3, 0), null);
		cell2item.put(GridCell.at(4, 0), null);
		cell2item.put(GridCell.at(5, 0), null);
		cell2item.put(GridCell.at(5, 1), null);
		cell2item.put(GridCell.at(5, 2), null);
		cell2item.put(GridCell.at(4, 2), null);
		cell2item.put(GridCell.at(4, 3), null);
		cell2item.put(GridCell.at(4, 4), null);
		cell2item.put(GridCell.at(4, 5), null);
		cell2item.put(GridCell.at(5, 5), null);
		
		return new SimpleGridImpl<T>(cell2item);
	}
	
	//NEW
	// Taken from Warehouse.java code that wouldn't work for GridRobots without default delay
	// This is only useful for tests where all GridRobots have default delay
	public void moveAllRobotsToDest(IWarehouse warehouse, Map<IGridRobot, GridCell> robot2dest){
		PathPlanner planner = new PathPlanner();
		/*
		HashSet<IGridRobot> finishedRobots = new HashSet<IGridRobot>();
		while(finishedRobots != robot2dest.keySet()){
			for (Map.Entry<IGridRobot, GridCell> entry: robot2dest.entrySet()){
				if (!finishedRobots.contains(entry.getKey())){
					Entry<IGridRobot, Direction> dir = planner.nextStepOneRobot(warehouse, entry);
					if (dir != null){
						dir.getKey().step(dir.getValue());
					}
					else{
						finishedRobots.add(entry.getKey());
					}
				}
			}
			TestUtil.waitOnRobot();
		}
		*/
		boolean done = false;
		Map<IGridRobot, GridCell> prevPositions = new HashMap<IGridRobot, GridCell>();
		while (!done){
			warehouse.moveAllRobotsOneStep(robot2dest);
			TestUtil.waitOnRobot();
			Map<IGridRobot, GridCell> curPositions = new HashMap<IGridRobot, GridCell>();
			for (IGridRobot rob: robot2dest.keySet()){
				curPositions.put(rob, rob.getLocation());
			}
			if (prevPositions.equals(curPositions)){
				done = true;
			}
			prevPositions = curPositions;
		}
		
	}
	// =================================================================================================================
	
	@Test(timeout = (long) (7 * GridRobot.defaultDelayInMilliseconds * TestUtil.waitTimeMult * TestUtil.waitTimeMult))
	// Test takes 6 moves but moveAllRobotsToDest takes some time to check it's exit condition
	public void SimpleMultiRobotsTest() throws Exception {
		IWarehouse warehouse = createWarehouse(createOShapedFloorPlan());
		
		// Add the robots
		IGridRobot robot1 = warehouse.addRobot(GridCell.at(0, 1));
		GridCell destination1 = GridCell.at(0, 5);
		IGridRobot robot2 = warehouse.addRobot(GridCell.at(1, 0));
		GridCell destination2 = GridCell.at(5, 0);
		IGridRobot robot3 = warehouse.addRobot(GridCell.at(1, 1));
		GridCell destination3 = GridCell.at(4, 4);
		
		// Set the robots' final destination  
		Map<IGridRobot, GridCell> robot2dest = new HashMap<IGridRobot,GridCell>();
		robot2dest.put(robot1, destination1);
		robot2dest.put(robot2, destination2);
		robot2dest.put(robot3, destination3);
		
		moveAllRobotsToDest(warehouse, robot2dest);
		
		assertEquals(destination1, robot1.getLocation());
		assertEquals(destination2, robot2.getLocation());
		assertEquals(destination3, robot3.getLocation());
	}
	
	@Test(timeout = (long) (10 * GridRobot.defaultDelayInMilliseconds * TestUtil.waitTimeMult * TestUtil.waitTimeMult))
	// This case doesnt work
	// This could be solved with CSC384 knowledge (exploring state spaces) but this seems greatly out of the scope of this assignment
	public void RobotsBlockingEachOther() throws Exception {
		IWarehouse warehouse = createWarehouse(createHShapedFloorPlan());
		
		// Add the robots
		IGridRobot robot1 = warehouse.addRobot(GridCell.at(2, 1));
		GridCell destination1 = GridCell.at(5, 1);
		IGridRobot robot2 = warehouse.addRobot(GridCell.at(4, 1));
		GridCell destination2 = GridCell.at(1, 1);
		
		// Set the robots' final destination  
		Map<IGridRobot, GridCell> robot2dest = new HashMap<IGridRobot,GridCell>();
		robot2dest.put(robot1, destination1);
		robot2dest.put(robot2, destination2);
		
		moveAllRobotsToDest(warehouse, robot2dest);
		
		assertEquals(destination1, robot1.getLocation());
		assertEquals(destination2, robot2.getLocation());
	}
	
	@Test(timeout = (long) (7 * GridRobot.defaultDelayInMilliseconds * TestUtil.waitTimeMult * TestUtil.waitTimeMult))
	// Only one robot should go 'out of its way' - the other should continue forward
	public void RobotsMovingPastEachOther() throws Exception {
		IWarehouse warehouse = createWarehouse(createBoxShapedFloorPlan());
		
		// Add the robots
		IGridRobot robot1 = warehouse.addRobot(GridCell.at(0, 3));
		GridCell destination1 = GridCell.at(5, 3);
		IGridRobot robot2 = warehouse.addRobot(GridCell.at(5, 3));
		GridCell destination2 = GridCell.at(0, 3);
		
		// Set the robots' final destination  
		Map<IGridRobot, GridCell> robot2dest = new HashMap<IGridRobot,GridCell>();
		robot2dest.put(robot1, destination1);
		robot2dest.put(robot2, destination2);
		
		for (int i = 0; i < 2; i++){
			warehouse.moveAllRobotsOneStep(robot2dest);
			TestUtil.waitOnRobot();
		}
		// this isnt necessarily true
		//assertEquals(GridCell.at(2, 3), robot1.getLocation());
		//assertEquals(GridCell.at(3, 3), robot2.getLocation());
		
		warehouse.moveAllRobotsOneStep(robot2dest);
		TestUtil.waitOnRobot();
		
		assertTrue((GridCell.at(3, 3).equals(robot1.getLocation())) || (GridCell.at(2, 3).equals(robot2.getLocation())));
		
		for (int i = 0; i < 4; i++){
			warehouse.moveAllRobotsOneStep(robot2dest);
			TestUtil.waitOnRobot();
		}
		
		assertEquals(destination1, robot1.getLocation());
		assertEquals(destination2, robot2.getLocation());
	}
	
	@Test(timeout = (long) (4 * GridRobot.defaultDelayInMilliseconds * TestUtil.waitTimeMult * TestUtil.waitTimeMult))
	// This case doesnt work
	// This could be solved with CSC384 knowledge (exploring state spaces) but this seems greatly out of the scope of this assignment
	public void RobotsFormingALoop() throws Exception {
		IWarehouse warehouse = createWarehouse(createBoxShapedFloorPlan());
		
		// Add the robots
		IGridRobot robot1 = warehouse.addRobot(GridCell.at(0, 0));
		GridCell destination1 = GridCell.at(0, 1);
		IGridRobot robot2 = warehouse.addRobot(GridCell.at(0, 1));
		GridCell destination2 = GridCell.at(1, 1);
		IGridRobot robot3 = warehouse.addRobot(GridCell.at(1, 1));
		GridCell destination3 = GridCell.at(1, 0);
		IGridRobot robot4 = warehouse.addRobot(GridCell.at(1, 0));
		GridCell destination4 = GridCell.at(0, 0);
		
		// Set the robots' final destination  
		Map<IGridRobot, GridCell> robot2dest = new HashMap<IGridRobot,GridCell>();
		robot2dest.put(robot1, destination1);
		robot2dest.put(robot2, destination2);
		robot2dest.put(robot3, destination3);
		robot2dest.put(robot4, destination4);
		
		moveAllRobotsToDest(warehouse, robot2dest);
		
		assertEquals(destination1, robot1.getLocation());
		assertEquals(destination2, robot2.getLocation());
		assertEquals(destination3, robot3.getLocation());
		assertEquals(destination4, robot4.getLocation());
	}
	
	@Test(timeout = (long) (4 * GridRobot.defaultDelayInMilliseconds * TestUtil.waitTimeMult * TestUtil.waitTimeMult))
	public void RobotsFormingALoopImpossible() throws Exception {
		IWarehouse warehouse = createWarehouse(createSmallBoxShapedFloorPlan());
		
		// Add the robots
		GridCell initialLocation1 = GridCell.at(0, 0);
		IGridRobot robot1 = warehouse.addRobot(initialLocation1);
		GridCell initialLocation2 = GridCell.at(0, 1);
		IGridRobot robot2 = warehouse.addRobot(initialLocation2);
		GridCell initialLocation3 = GridCell.at(1, 1);
		IGridRobot robot3 = warehouse.addRobot(initialLocation3);
		GridCell initialLocation4 = GridCell.at(1, 0);
		IGridRobot robot4 = warehouse.addRobot(initialLocation4);
		
		GridCell destination1 = initialLocation2;
		GridCell destination2 = initialLocation3;
		GridCell destination3 = initialLocation4;
		GridCell destination4 = initialLocation1;
		
		// Set the robots' final destination  
		Map<IGridRobot, GridCell> robot2dest = new HashMap<IGridRobot,GridCell>();
		robot2dest.put(robot1, destination1);
		robot2dest.put(robot2, destination2);
		robot2dest.put(robot3, destination3);
		robot2dest.put(robot4, destination4);
		
		moveAllRobotsToDest(warehouse, robot2dest);
		
		// Nothing should've moved
		assertEquals(initialLocation1, robot1.getLocation());
		assertEquals(initialLocation2, robot2.getLocation());
		assertEquals(initialLocation3, robot3.getLocation());
		assertEquals(initialLocation4, robot4.getLocation());
	}
	
	@Test(timeout = (long) (6 * GridRobot.defaultDelayInMilliseconds * TestUtil.waitTimeMult * TestUtil.waitTimeMult))
	// Only one robot should go 'out of its way' - the other should continue forward
	public void RobotsMoveInAnX() throws Exception {
		IWarehouse warehouse = createWarehouse(createBoxShapedFloorPlan());
		
		// Add the robots
		IGridRobot robot1 = warehouse.addRobot(GridCell.at(0, 1));
		GridCell destination1 = GridCell.at(5, 1);
		IGridRobot robot2 = warehouse.addRobot(GridCell.at(4, 5));
		GridCell destination2 = GridCell.at(4, 0);
		
		// Set the robots' final destination  
		Map<IGridRobot, GridCell> robot2dest = new HashMap<IGridRobot,GridCell>();
		robot2dest.put(robot1, destination1);
		robot2dest.put(robot2, destination2);
		
		for (int i = 0; i < 4; i++){
			warehouse.moveAllRobotsOneStep(robot2dest);
			TestUtil.waitOnRobot();
		}
		assertTrue((GridCell.at(4, 1).equals(robot1.getLocation())) || (GridCell.at(4, 1).equals(robot2.getLocation())));
		
		// optimally would only take 1 more move
		for (int i = 0; i < 2; i++){
			warehouse.moveAllRobotsOneStep(robot2dest);
			TestUtil.waitOnRobot();
		}
		
		assertEquals(destination1, robot1.getLocation());
		assertEquals(destination2, robot2.getLocation());
	}
	
	@Test(timeout = (long) (18 * GridRobot.defaultDelayInMilliseconds * TestUtil.waitTimeMult * TestUtil.waitTimeMult))
	public void RobotMovesThroughMaze() throws Exception {
		IWarehouse warehouse = createWarehouse(createMazeShapedFloorPlan());
		
		// Add the robots
		IGridRobot robot1 = warehouse.addRobot(GridCell.at(0, 0));
		GridCell destination1 = GridCell.at(5, 5);

		
		// Set the robots' final destination  
		Map<IGridRobot, GridCell> robot2dest = new HashMap<IGridRobot,GridCell>();
		robot2dest.put(robot1, destination1);
		
		GridCell[] locations = {GridCell.at(0, 1), GridCell.at(0, 2), GridCell.at(0, 3), GridCell.at(1, 3), 
                GridCell.at(2, 3), GridCell.at(2, 2), GridCell.at(2, 1), GridCell.at(3, 1), 
                GridCell.at(3, 0), GridCell.at(4, 0), GridCell.at(5, 0), GridCell.at(5, 1), 
                GridCell.at(5, 2), GridCell.at(4, 2), GridCell.at(4, 3), GridCell.at(4, 4), 
                GridCell.at(4, 5), GridCell.at(5, 5)};
		for (int i = 0; i < 18; i++){
			warehouse.moveAllRobotsOneStep(robot2dest);
			TestUtil.waitOnRobot();
			assertEquals(locations[i], robot1.getLocation());
		}
		
		assertEquals(destination1, robot1.getLocation());
	}
}