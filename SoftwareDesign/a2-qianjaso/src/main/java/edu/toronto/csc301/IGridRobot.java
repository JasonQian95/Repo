package edu.toronto.csc301;


public interface IGridRobot {

	public static enum Direction {NORTH, EAST, SOUTH, WEST};
	
	public GridCell getLocation();
	public Direction getFacingDirection();
	
	public void step(Direction direction);
	public void face(Direction direction);
	
}
