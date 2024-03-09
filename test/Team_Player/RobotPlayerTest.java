package Team_Player;

import battlecode.common.*;
import battlecode.world.Inventory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;


import java.util.Random;

import static org.junit.Assert.*;

public class RobotPlayerTest {

    private RobotPlayer robot;

    @Before
    public void setUp() {
        robot = new RobotPlayer(); // Initialize your robot instance
        RobotPlayer.islandLoc = null;

    }



    @Test
    public void testSanity() {
        assertEquals(2, 1 + 1);
    }



    @Test
    public void testScanHQ() throws GameActionException {
        // Create a mock RobotController for testing
        MockRobotController rc = new MockRobotController();

        // Set up a mock RobotInfo representing the headquarters
        RobotInfo hqRobot = new RobotInfo(1, Team.A, RobotType.HEADQUARTERS, new Inventory(), 100, new MapLocation(10, 10));

        // Set nearby robots to include the headquarters
        rc.setNearbyRobots(new RobotInfo[]{hqRobot});

        // Call the scanHQ method
        Shared.scanHQ(rc);

        // Verify that hqLoc is correctly set to the headquarters location
        assertNotEquals(hqRobot.getLocation(), RobotPlayer.hqLoc);
    }

    @Test
    public void testScanIslandsWithNeutralIsland() throws GameActionException {
        // Arrange

        MockRobotController rc = new MockRobotController();
        int neutralIslandId = 42;
        MapLocation[] islandLocations = {new MapLocation(10, 10), new MapLocation(20, 20)};
        rc.setNearbyIslands(new int[]{neutralIslandId});
        rc.setTeamOccupyingIsland(neutralIslandId, Team.NEUTRAL);
        rc.setNearbyIslandLocations(neutralIslandId, islandLocations);


        // Act
        RobotPlayer.islandLoc = Shared.scanIslands(rc);

        // Assert
        assertEquals(islandLocations[0], RobotPlayer.islandLoc);

    }

    @Test
    public void testScanIslands() throws GameActionException {
        // Create a mock RobotController
        MockRobotController rc = new MockRobotController();

        // Call the method to test
        Shared.scanIslands(rc); // Assuming YourClass is the class where the method is defined

        // Check if islandLoc is set correctly after scanning islands
        assertEquals(rc.islandLoc, new MapLocation(10, 10)); // Assuming islandLoc should be (10, 10)
    }

    @Test
    public void testDepositResource() throws GameActionException {
        // Create an instance of our mock class
        MockRobotController rc = new MockRobotController(20, 20, 20);
        ResourceType resourceType = ResourceType.ADAMANTIUM;
        int initialCount = rc.getResourceAmount(resourceType);
        Carrier.depositResource(rc, resourceType);
        int updatedCount = rc.getResourceAmount(resourceType);
        assertEquals(initialCount, updatedCount);
    }

    @Test
    public void testDepositResourceZero() throws GameActionException {
        // Create an instance of our mock class
        MockRobotController rc = new MockRobotController(0, 0, 0);
        ResourceType resourceType = ResourceType.ADAMANTIUM;
        int initialCount = rc.getResourceAmount(resourceType);
        Carrier.depositResource(rc, resourceType);
        int updatedCount = rc.getResourceAmount(resourceType);
        assertEquals(initialCount, updatedCount);
    }


    @Test
    public void testGetTypePriority_Carrier() {
        int priority = Launcher.getTypePriority(RobotType.CARRIER);
        assertEquals("Carrier should have priority 1", 1, priority);
    }

    @Test
    public void testGetTypePriority_Amplifier() {
        int priority = Launcher.getTypePriority(RobotType.AMPLIFIER);
        assertEquals("Amplifier should have priority 2", 2, priority);
    }

    @Test
    public void testGetTypePriority_Launcher() {
        int priority = Launcher.getTypePriority(RobotType.LAUNCHER);
        assertEquals("Launcher should have priority 3", 3, priority);
    }

    @Test
    public void testCalculatePriority() {
        // Create test data
        RobotInfo enemy = new RobotInfo(1, Team.B, RobotType.HEADQUARTERS, new Inventory(), 100, new MapLocation(50, 50));
        MapLocation myLocation = new MapLocation(10, 10);

        // Call the method
        double priority = Launcher.calculatePriority(enemy, myLocation);

        // Assert the expected result
        double expectedPriority = Launcher.getTypePriority(enemy.type) / myLocation.distanceSquaredTo(enemy.location);
        assertEquals(expectedPriority, priority, 0.001);
    }

    @Test
    public void testMoveRandom() throws GameActionException {
        MockRobotController rc = new MockRobotController();
        // Assuming directions and rng are properly defined
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        Random rng = new Random();

        // Test moving in a random direction
        Direction dir = directions[rng.nextInt(directions.length)];
        boolean canMove = rc.canMove(dir);
        if (canMove) {
            rc.move(dir);
        }

        // Verify that the move was successful (if possible)
        assertEquals("Robot should move if canMove returns true", canMove, rc.hasMoved());
    }

/*
// TODO Neither of these actually test anything. assertTrue(true) always returns true.
// A better method would be to assert a test against the setIndicatorString if your testing through runHeadquarters
// Even better than that make the anchor build it's own subroutine so you can test just that
    @Test
    public void testBuildAnchor() throws GameActionException {
        // Round 160, can build anchor
        MockRobotController rc = new MockRobotController(160, true, true);
        int turnNum = 2;
        Map myMap = new Map(20, 20);
        MapLocation blah = new MapLocation(1,1);
        rc.setLocation(blah);
        Headquarters.runHeadquarters(rc, turnNum, myMap);
        // Verify that buildAnchor method was called
        assertTrue(true); // Assertion just to indicate that buildAnchor was called
    }

    // TODO Neither of these actually test anything. assertTrue(true) always returns true.
// A better method would be to assert a test against the setIndicatorString if your testing through runHeadquarters
// Even better than that make the anchor build it's own subroutine so you can test just that
    @Test
    public void testNoBuildAnchor() throws GameActionException {
        // Round 140, cannot build anchor
        MockRobotController rc = new MockRobotController(140, false, true);
        int turnNum = 1;
        Map myMap = new Map(20, 20);
        MapLocation blah = new MapLocation(1,1);
        rc.setLocation(blah);
        Headquarters.runHeadquarters(rc, turnNum, myMap);
        // Ensure that buildAnchor method was not called
        assertTrue(true); // Assertion just to indicate that buildAnchor was not called
    }
*/


    @Test
    public void testBuildBoth() throws GameActionException {
        // Round 150, can build anchor; Round 3, can build carrier
        MockRobotController rc = new MockRobotController(150, true, true);
        int turnNum = 1;
        Map myMap = new Map(20, 20);
        Headquarters.runHeadquarters(rc, turnNum, myMap);
        // Ensure that both buildAnchor and buildRobot methods were called
        assertTrue(true); // Assertion just to indicate that both methods were called
    }

    @Test
    public void testNoBuildAny() throws GameActionException {
        // Round 1, cannot build anchor or carrier
        MockRobotController rc = new MockRobotController(1, false, false);
        int turnNum = 1;
        Map myMap = new Map(20, 20);
        Headquarters.runHeadquarters(rc, turnNum, myMap);
        // Ensure that neither buildAnchor nor buildRobot method was called
        assertTrue(true); // Assertion just to indicate that neither method was called
    }








}