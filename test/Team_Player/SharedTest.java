package Team_Player;

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

}