package edu.toronto.csc301.tests;

import static edu.toronto.csc301.util.TestUtil.createPathPlanner;
import static edu.toronto.csc301.util.TestUtil.createWarehouse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.HashMap;
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
import edu.toronto.csc301.warehouse.Warehouse;

public class SingleRobotTests {

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
	// =================================================================================================================
	
	@Test
	public void dontWalkIntoHole() throws Exception {
		PathPlanner pathPlanner = new PathPlanner();
		IWarehouse warehouse = createWarehouse(createOShapedFloorPlan());
		
		// Add the robot
		IGridRobot robot = warehouse.addRobot(GridCell.at(0, 0));
		GridCell destination = GridCell.at(2,2);
		
		// Set the robot's final destination  
		Entry<IGridRobot, GridCell> robot2dest = new AbstractMap.SimpleEntry<IGridRobot,GridCell>(robot, destination);
		
		Entry<IGridRobot, Direction> nextStep = pathPlanner.nextStepOneRobot(warehouse, robot2dest);
		
		assertEquals(null, nextStep);
	}
	
	@Test(timeout = (long) (5 * GridRobot.defaultDelayInMilliseconds * TestUtil.waitTimeMult * TestUtil.waitTimeMult))
	public void walkAroundHole() throws Exception {
		PathPlanner pathPlanner = new PathPlanner();
		IWarehouse warehouse = createWarehouse(createOShapedFloorPlan());
		
		// Add the robot
		IGridRobot robot = warehouse.addRobot(GridCell.at(2, 1));
		GridCell destination = GridCell.at(2, 4);
		
		// Set the robot's final destination  
		Entry<IGridRobot, GridCell> robot2dest = new AbstractMap.SimpleEntry<IGridRobot,GridCell>(robot, destination);
		
		// The planner should get the robot to the destination (and do it efficiently)
		int stepLimit = 5;
		int stepCount = 0;
		while(stepCount < stepLimit){
			Entry<IGridRobot, Direction> nextStep = pathPlanner.nextStepOneRobot(warehouse, robot2dest);
			IGridRobot r = nextStep.getKey();
			Direction  d = nextStep.getValue();
			
			// Before we take the step, let's make sure we're staying on the grid
			GridCell endCell = GridRobot.oneCellOver(r.getLocation(), d);
			assertTrue(warehouse.getFloorPlan().hasCell(endCell));
			
			// Take the step ...
			r.step(nextStep.getValue());
			stepCount++;
			TestUtil.waitOnRobot();
		}
		
		assertEquals(destination, robot.getLocation());
	}
	
	@Test(expected=NullPointerException.class)
	public void NullGridCellRobot() throws Exception {
		IWarehouse warehouse = createWarehouse(createOShapedFloorPlan());
		// Add the robot
		IGridRobot robot = warehouse.addRobot(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void NullDestRobot() throws Exception {
		PathPlanner pathPlanner = new PathPlanner();
		IWarehouse warehouse = createWarehouse(createOShapedFloorPlan());
		
		// Add the robot
		IGridRobot robot = warehouse.addRobot(GridCell.at(0, 0));
		GridCell destination = null;
		
		// Set the robot's final destination  
		Entry<IGridRobot, GridCell> robot2dest = new AbstractMap.SimpleEntry<IGridRobot,GridCell>(robot, destination);
		
		pathPlanner.nextStepOneRobot(warehouse, robot2dest);
	}
	
	@Test(expected=NullPointerException.class)
	public void NoGridCellWarehouse() throws Exception {
		IWarehouse warehouse = new Warehouse(null);
	}
}