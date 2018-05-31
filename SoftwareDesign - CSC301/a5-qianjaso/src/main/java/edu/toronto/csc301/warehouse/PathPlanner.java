package edu.toronto.csc301.warehouse;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.robot.GridRobot;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;

public class PathPlanner implements IPathPlanner{

	public Entry<IGridRobot, Direction> nextStep(IWarehouse warehouse, Map<IGridRobot, GridCell> robot2dest) {
		//Map<IGridRobot, Direction> steps = new HashMap<IGridRobot, Direction>();
		for (Map.Entry<IGridRobot, GridCell> entry: robot2dest.entrySet()){
			if (entry.getKey() == null || entry.getValue() == null){
				throw new NullPointerException();
			}
			IGridRobot robot = entry.getKey();
			GridCell dest = entry.getValue();
			Direction dir = bfs(robot, dest, warehouse);
			if (dir != null){
				//steps.put(robot, dir);
				return new AbstractMap.SimpleEntry<IGridRobot, Direction>(robot, dir);
			}

		}
		//if (steps.isEmpty()){
		//	return null;
		//}
		//return (Entry<IGridRobot, Direction>) steps.entrySet().toArray()[0];
		return null;
	}
	
	public Direction bfs (IGridRobot robot, GridCell dest, IWarehouse warehouse){
		IGrid grid = warehouse.getFloorPlan();
		ArrayList<GridCell> queue = new ArrayList<GridCell>();
		ArrayList<GridCell> visitedCells = new ArrayList<GridCell>();
		Map<GridCell, Direction> predecesor = new HashMap<GridCell, Direction>();
		boolean destFound = false;
		ArrayList<GridCell> robotLocations = new ArrayList<GridCell>();
		Iterator<IGridRobot>robots = warehouse.getRobots();
		while (robots.hasNext()){
			IGridRobot rob = robots.next();
			robotLocations.add(GridCell.at(rob.getLocation().x, rob.getLocation().y));
		}
		//If a robot is moving, other robots can not move to that robot's starting location OR destination, in case
		//there is an error and the robot needs to return to its starting location instead of its destination
		//NEW
		
		// Concurrent Modification Error
		//Entry<IGridRobot, Direction>[] robotsInMotion = warehouse.getRobotsInMotion().entrySet().toArray(new Map.Entry[warehouse.getRobotsInMotion().size()]);
		Map<IGridRobot, Direction> robotsInMotion = new HashMap<IGridRobot, Direction>();
		robotsInMotion.putAll(warehouse.getRobotsInMotion());
		for (Entry<IGridRobot, Direction> entry : robotsInMotion.entrySet()){
			robotLocations.add(GridRobot.oneCellOver(entry.getKey().getLocation(), entry.getValue()));
		}
		
		queue.add(robot.getLocation());
		visitedCells.add(robot.getLocation());
		while(queue.size() > 0){
			GridCell cell = queue.get(0);
			GridCell tempCell;
			if (cell.equals(dest)){
				destFound = true;
				break;
			}
			tempCell = GridCell.at(cell.x + 1, cell.y);
			if (grid.hasCell(tempCell) && !visitedCells.contains(tempCell) && (!robotLocations.contains(tempCell) || tempCell.equals(dest))){
				queue.add(tempCell);
				visitedCells.add(tempCell);
				predecesor.put(tempCell, Direction.EAST);
			}
			tempCell = GridCell.at(cell.x, cell.y + 1);
			if (grid.hasCell(tempCell) && !visitedCells.contains(tempCell) && (!robotLocations.contains(tempCell) || tempCell.equals(dest))){
				queue.add(tempCell);
				visitedCells.add(tempCell);
				predecesor.put(tempCell, Direction.NORTH);
			}
			tempCell = GridCell.at(cell.x - 1, cell.y);
			if (grid.hasCell(tempCell) && !visitedCells.contains(tempCell) && (!robotLocations.contains(tempCell) || tempCell.equals(dest))){
				queue.add(tempCell);
				visitedCells.add(tempCell);
				predecesor.put(tempCell, Direction.WEST);
			}
			tempCell = GridCell.at(cell.x, cell.y - 1);
			if (grid.hasCell(tempCell) && !visitedCells.contains(tempCell) && (!robotLocations.contains(tempCell) || tempCell.equals(dest))){
				queue.add(tempCell);
				visitedCells.add(tempCell);
				predecesor.put(tempCell, Direction.SOUTH);
			}
			queue.remove(0);
		}
		if (destFound){
			boolean atRoot = false;
			GridCell currentCell = queue.get(0);
			GridCell previousCell = null;
			while (!atRoot){
				if (predecesor.get(currentCell) == Direction.NORTH){
					previousCell = currentCell;
					currentCell = GridCell.at(currentCell.x, currentCell.y - 1);
				}
				if (predecesor.get(currentCell) == Direction.SOUTH){
					previousCell = currentCell;
					currentCell = GridCell.at(currentCell.x, currentCell.y + 1);
				}
				if (predecesor.get(currentCell) == Direction.EAST){
					previousCell = currentCell;
					currentCell = GridCell.at(currentCell.x - 1, currentCell.y);
				}
				if (predecesor.get(currentCell) == Direction.WEST){
					previousCell = currentCell;
					currentCell = GridCell.at(currentCell.x + 1, currentCell.y);
				}
				if (currentCell.equals(robot.getLocation())){
					atRoot = true;
				}
			}
			if (robotLocations.contains(previousCell)){
				return null;
			}
			return predecesor.get(previousCell);
		}
		return null;
	}
	
	// Why does nextStep even take a map if it only returns one move?
	// Cant change nextStep to return a map because robotsInMotion in the warehouse has to be updated for each movement
	// Also the type signature is in IPathPlanner
	//NEW
	// This is tested in SingleRobotTests.java but not MultipleRobotTests.java
	public Entry<IGridRobot, Direction> nextStepOneRobot(IWarehouse warehouse, Entry<IGridRobot, GridCell> entry) {
		if (entry.getKey() == null || entry.getValue() == null){
			throw new NullPointerException();
		}
		IGridRobot robot = entry.getKey();
		GridCell dest = entry.getValue();
		Direction dir = bfs(robot, dest, warehouse);
		if (dir != null){
			return new AbstractMap.SimpleEntry<IGridRobot, Direction>(robot, dir);
		}
		return null;
	}

}
