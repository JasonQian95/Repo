package edu.toronto.csc301;

public class GridRobot2 extends BasicRobot implements IGridRobot {

	private Direction dir = null;
	private int cellSize;

	public GridRobot2(GridCell pos, Direction dir, int cellSize) {
		super();
		this.xCoord = pos.x * cellSize;
		this.yCoord = pos.y * cellSize;
		face(dir);
		if (cellSize <= 0){
			throw new IllegalArgumentException();
		}
		this.cellSize = cellSize;
	}

	public GridCell getLocation() {
		return GridCell.at((int)(this.xCoord / cellSize), (int) (this.yCoord / cellSize));
	}

	public Direction getFacingDirection() {
		return dir;
	}

	public void step(Direction direction) {
		face(direction);
		moveForward(cellSize);
	}

	public void face(Direction direction) {
		this.dir = direction;
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
	}

	public void rotateRight(int degrees) {
		if (degrees % 90 != 0) {
			throw new IllegalArgumentException();
		}
		super.rotateRight(degrees);
		setDirBasedOnAngle();
	}

	public void rotateLeft(int degrees) {
		if (degrees % 90 != 0) {
			throw new IllegalArgumentException();
		}
		super.rotateLeft(degrees);
		setDirBasedOnAngle();
	}

	public void moveForward(int millimeters) {
		if (millimeters % cellSize != 0){
			throw new IllegalArgumentException();
		}
		super.moveForward(millimeters);
	}
	
	public void setDirBasedOnAngle() {
		switch (this.angle) {
		case 0:
			this.dir = Direction.NORTH;
			break;
		case 90:
			this.dir = Direction.EAST;
			break;
		case 180:
			this.dir = Direction.SOUTH;
			break;
		case 270:
			this.dir = Direction.WEST;
			break;
		}
	}
}
