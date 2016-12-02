package edu.toronto.csc301;

public class BasicRobot implements IBasicRobot {
	
	protected double xCoord;
	protected double yCoord;
	protected int angle; //the angle the robot is facing, clockwise from 12 o'clock
	
	public BasicRobot(){
		xCoord = 0;
		yCoord = 0;
		angle = 0;
	}
	
	public BasicRobot(double x, double y, int rotation){
		xCoord = x;
		yCoord = y;
		if (rotation < 0 || rotation >= 360){
			throw new IllegalArgumentException();
		}
		angle = rotation;
	}
	
	public double getXCoordinate() {
		return xCoord;
	}

	public double getYCoordinate() {
		return yCoord;
	}

	public int getRotation() {
		return angle;
	}

	public void rotateRight(int degrees) {
		angle += degrees;
		fixAngleOverflow();
	}

	public void rotateLeft(int degrees) {
		angle -= degrees;
		fixAngleOverflow();
	}
	
	public void fixAngleOverflow(){
		while (angle >= 360){
			angle -= 360;
		}
		while (angle < 0){
			angle += 360;
		}
	}

	public void moveForward(int millimeters) {
		double radians = Math.toRadians(angle);
		xCoord += Math.sin(radians) * millimeters;
		yCoord += Math.cos(radians) * millimeters;
	}

}
