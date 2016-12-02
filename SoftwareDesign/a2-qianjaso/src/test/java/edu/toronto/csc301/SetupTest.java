package edu.toronto.csc301;

import static edu.toronto.csc301.util.Asserts.*;

import org.junit.Test;


public class SetupTest {

	public static final String  BASIC_ROBOT = "edu.toronto.csc301.BasicRobot";
	public static final String  GRID_ROBOT1 = "edu.toronto.csc301.GridRobot1";
	public static final String  GRID_ROBOT2 = "edu.toronto.csc301.GridRobot2";
	
	@Test
	public void checkExistence_BasicRobot() {
		assertClassExists(BASIC_ROBOT);
	}
	
	@Test
	public void checkExistence_GridRobot1() {
		assertClassExists(GRID_ROBOT1);
	}
	
	@Test
	public void checkExistence_GridRobot2() {
		assertClassExists(GRID_ROBOT2);
	}
	
	
	
	@Test
	public void checkInterface_BasicRobot() throws ClassNotFoundException {
		assertClassImplementsInterface(BASIC_ROBOT, IBasicRobot.class);
	}
	
	@Test
	public void checkInterface_GridRobot1() throws ClassNotFoundException {
		assertClassImplementsInterface(GRID_ROBOT1, IGridRobot.class);
	}
	
	@Test
	public void checkInterface_GridRobot2() throws ClassNotFoundException {
		assertClassImplementsInterface(GRID_ROBOT2, IGridRobot.class);
	}
	
	
	
	@Test
	public void checkConstructor_BasicRobot() throws ClassNotFoundException {
		assertClassHasConstructor(BASIC_ROBOT, Double.TYPE, Double.TYPE, Integer.TYPE);
	}
	
	@Test
	public void checkConstructor_GridRobot1() throws ClassNotFoundException {
		assertClassHasConstructor(GRID_ROBOT1, IBasicRobot.class, Integer.TYPE);
	}
	
	@Test
	public void checkConstructor_GridRobot2() throws ClassNotFoundException {
		assertClassHasConstructor(GRID_ROBOT2, GridCell.class, IGridRobot.Direction.class, Integer.TYPE);
	}
	
	
	@Test
	public void checkParent_GridRobot2() throws ClassNotFoundException {
		assertClassHasParent(GRID_ROBOT2, BASIC_ROBOT);
	}
	
}
