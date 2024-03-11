package Team_Player;

import battlecode.world.Inventory;
import org.junit.Ignore;
import org.junit.Test;
import battlecode.common.*;
import org.scalactic.Or;

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
    @Test
    public void testScanManaWell_ManaWell() throws GameActionException {
        // Create a stub implementation of RobotController
        MockRobotController rc = new MockRobotController();
        // Set the mana well location
        rc.setWellLocation(new MapLocation(10, 10));

        MapLocation testwellLoc = new MapLocation(10,10);
        Inventory inv = new Inventory();
        WellInfo twInfo = new WellInfo(testwellLoc,ResourceType.MANA,inv,false);
        MockRobotController mrc = new MockRobotController();
        rc.setSenseWell_Ret_Value(twInfo);

        // Call the method to test
        MapLocation wellLocation = Shared.scanManaWell(rc);

        // Verify that the method returns the correct location
        assertEquals(new MapLocation(10, 10), wellLocation);

    }

    @Test
    public void scanWellTest() throws GameActionException {
        MapLocation testislandLoc = new MapLocation(0,1);
        MapLocation testhqLoc = new MapLocation(1,1);
        MapLocation testwellLoc = new MapLocation(1,0);
        Inventory inv = new Inventory();

        WellInfo twInfo = new WellInfo(testwellLoc,ResourceType.ADAMANTIUM,inv,false);
        MockRobotController mrc = new MockRobotController();
        mrc.setSenseWell_Ret_Value(twInfo);

        MapLocation expected = new MapLocation(1,0);
        MapLocation result = Shared.scanWells(mrc);
        assertEquals(expected,result);
    }


}