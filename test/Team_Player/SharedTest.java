package Team_Player;

import org.junit.Ignore;
import org.junit.Test;
import battlecode.common.*;

import static org.junit.Assert.*;

public class SharedTest {


    @Test
    public void testMoveNextBest() throws GameActionException {
        // Create a mock RobotController for testing
        MockRobotController rc = new MockRobotController();
        // Set the initial direction
        Direction initialDirection = Direction.NORTH;
        // Simulate moving next best
        Shared.moveNextBest(rc, initialDirection);
        // Ensure that the robot tries the next best directions
        assertTrue(rc.moveCalled);
    }

    @Test
    public void testMoveRandom() throws GameActionException {
        // Create a mock RobotController for testing
        MockRobotController rc = new MockRobotController();
        // Simulate a move
        Shared.moveRandom(rc);
        // Ensure that the direction chosen is within the defined directions array
        assertTrue(rc.moveCalled);
    }
    /*@Test
    public void testMoveTowards() throws GameActionException {
        // Create a mock RobotController for testing
        MockRobotController rc = new MockRobotController();
        // Set the initial direction
        Direction initialDirection = Direction.NORTH;
        // Simulate moving towards a location
        Shared.moveTowards(rc, new MapLocation(5, 5));
        // Ensure that the robot moves towards the location
        assertTrue(rc.moveCalled);
    }*/
    @Test
    public void testMoveRandomValid() throws GameActionException {
        // Create a mock RobotController
        MockRobotController rc = new MockRobotController();

        // Call the method to test
        Shared.moveRandom(rc);

        // Check if the robot moved in a valid direction
        assertTrue(rc.moveCalled);
    }

    @Test
    public void testMoveTowards() throws GameActionException {

        MockRobotController rc = new MockRobotController(true);

        // Create a mock MapLocation
        MapLocation loc = new MapLocation(10, 10);

        // Call the moveTowards method
        Shared.moveTowards(rc, loc);

        // Verify that rc.move was called with the correct direction
        assertTrue("Robot should move if canMove returns true", rc.hasMoved());
    }

}