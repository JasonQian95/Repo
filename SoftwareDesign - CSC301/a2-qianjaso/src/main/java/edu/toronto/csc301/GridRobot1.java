package edu.toronto.csc301;

public class GridRobot1 implements IGridRobot{

	private int cellSize;
	private IBasicRobot rob = null;
	
	public GridRobot1(IBasicRobot rob, int cellSize){
		if (rob.equals(null) || rob.getRotation() % 90 != 0){
			throw new IllegalArgumentException();
		}
		this.rob = rob;
		if (rob.getXCoordinate() % cellSize != 0 || rob.getYCoordinate() % cellSize != 0 ||cellSize <= 0){
			throw new IllegalArgumentException();
		}
		this.cellSize = cellSize;
	}

	public GridCell getLocation() {
		return GridCell.at((int) Math.round(rob.getXCoordinate() / cellSize), (int) Math.round(rob.getYCoordinate() / cellSize));
	}

	public Direction getFacingDirection() {
		switch (rob.getRotation()) {
		case 0:
			return Direction.NORTH;
		case 90:
			return Direction.EAST;
		case 180:
			return Direction.SOUTH;
		case 270:
			return Direction.WEST;
		default:
			// error
			return Direction.NORTH;
		}
	}

	public void step(Direction direction) {
		face(direction);
		rob.moveForward(cellSize);
	}

	public void face(Direction direction) {
		int angle = 0;
		switch (direction) {
		case NORTH:
			angle = 0;
			break;
		case EAST:
			angle = 90;
			break;
		case SOUTH:
			angle = 180;
			break;
		case WEST:
			angle = 270;
			break;
		}
		while(rob.getRotation() != angle){
			rob.rotateRight(90);
		}
	}
	
}