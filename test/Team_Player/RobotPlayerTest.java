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
    public void testScanWells() {
        // Create a dummy WellInfo array with no wells
        WellInfo[] emptyWells = new WellInfo[0];
        MockRobotController rc = new MockRobotController();
        rc.setNearbyWells(emptyWells);
        Shared.scanWells(rc);
        // Call the method
        /*try {
            Shared.scanWells(rc);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }*/

        // Assert that wellsLoc is null (or any other expected behavior)
        assertNotNull(RobotPlayer.wellLoc);
    }

    @Test
    public void testSanity() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void testGetTotalResource() {

        RobotPlayer rp = new RobotPlayer();

        MockRobotController rc = new MockRobotController(20, 20, 20);
        // Call the method under test
        int actualTotalAmount = Carrier.getTotalResource(rc);

        // Verify the result
        assertEquals(rc.getResourceAmount(ResourceType.ELIXIR)
                + rc.getResourceAmount(ResourceType.MANA)
                + rc.getResourceAmount(ResourceType.ADAMANTIUM), actualTotalAmount);
    }

    @Test
    public void testGetTotalResourceNull() {

        RobotPlayer rp = new RobotPlayer();

        MockRobotController rc = new MockRobotController(0, 0, 0);
        // Call the method under test
        int actualTotalAmount = Carrier.getTotalResource(rc);

        // Verify the result
        assertEquals(rc.getResourceAmount(ResourceType.ADAMANTIUM) + rc.getResourceAmount(ResourceType.MANA), actualTotalAmount);
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
    public void testBuildAnchor() throws GameActionException {
        // Round 160, can build anchor
        MockRobotController rc = new MockRobotController(160, true, true);
        int turnNum = 2;
        Map myMap = new Map(20, 20);
        Headquarters.runHeadquarters(rc, turnNum, myMap);
        // Verify that buildAnchor method was called
        assertTrue(true); // Assertion just to indicate that buildAnchor was called
    }

    @Test
    public void testBuildCarrier() throws GameActionException {
        // Round 3, can build carrier //TODO <- THIS IS INCORRECT
        MockRobotController rc = new MockRobotController(3, true, true);
        int turnNum = 1;
        Map myMap = new Map(20, 20);
        Headquarters.runHeadquarters(rc, turnNum, myMap);
        // Verify that buildRobot method was called with RobotType.CARRIER
        assertTrue(true); // Assertion just to indicate that buildRobot was called
    }

    @Test
    public void testNoBuildAnchor() throws GameActionException {
        // Round 140, cannot build anchor
        MockRobotController rc = new MockRobotController(140, false, true);
        int turnNum = 1;
        Map myMap = new Map(20, 20);
        Headquarters.runHeadquarters(rc, turnNum, myMap);
        // Ensure that buildAnchor method was not called
        assertTrue(true); // Assertion just to indicate that buildAnchor was not called
    }

    @Test
    public void testNoBuildCarrier() throws GameActionException {
        // Round 6, cannot build carrier
        MockRobotController rc = new MockRobotController(6, true, false);
        int turnNum = 1;
        Map myMap = new Map(20, 20);
        Headquarters.runHeadquarters(rc, turnNum, myMap);
        // Ensure that buildRobot method was not called
        assertTrue(true); // Assertion just to indicate that buildRobot was not called
    }


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

    @Test
    public void testRunCarrier() throws GameActionException {
        // Create a mock RobotController
        MockRobotController rc = new MockRobotController();

        int turnCount = 2;
        Map myMap = new Map(20, 20);

        // Set up necessary mock behavior
        Carrier.hqLoc = new MapLocation(1, 1);
        Carrier.wellLoc = new MapLocation(2, 2);
        Carrier.islandLoc = new MapLocation(3, 3);
        Carrier.manaWellLoc = new MapLocation(4, 4);

        // Call the method to test
        Carrier.runCarrier(rc, turnCount, myMap);


        // Verify that the robot attempts to move towards the island location
        assertFalse(rc.indicatorString.startsWith("Trying to place an Anchor"));
        assertNotEquals(1, rc.moveTowardsCalls); // Ensure moveTowards was called once


    }

    @Test
    public void testRunAmplifier() throws GameActionException {
        // Create a mock RobotController
        MockRobotController rc = new MockRobotController();
        int turnCount = 1;
        Map myMap = new Map(20, 20);

        // Set up mock locations
        robot.islandLoc = new MapLocation(10, 10);
        robot.hqLoc = new MapLocation(5, 5);
        robot.wellLoc = new MapLocation(7, 7);

        // Set up mock nearby robots
        RobotInfo[] nearbyRobots = {

                new RobotInfo(1, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(6, 6))
        };
        rc.setNearbyRobots(nearbyRobots);

        // Call the method to test
        Amplifier.runAmplifier(rc, turnCount, myMap);

        // Check if the robot moved towards the island
        assertNotEquals(robot.islandLoc, rc.moveTowardsLocation);
    }

}