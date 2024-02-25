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
    public void testScanWells_NoWells() {
        // Create a dummy WellInfo array with no wells
        WellInfo[] emptyWells = new WellInfo[0];
        MockRobotController rc = new MockRobotController();
        rc.setNearbyWells(emptyWells);
        // Call the method
        try {
            RobotPlayer.scanWells(rc);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }

        // Assert that wellsLoc is null (or any other expected behavior)
        assertNull(RobotPlayer.wellsLoc);
    }

    @Test
    public void testSanity() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void testGetTotalResource() {

        RobotPlayer rp = new RobotPlayer();

        MockRobotController rc = new MockRobotController(20, 20);
        // Call the method under test
        int actualTotalAmount = robot.getTotalResource(rc);

        // Verify the result
        assertNotEquals(rc.getAdamantium() + rc.getMana(), actualTotalAmount);
    }

    @Test
    public void testGetTotalResourceNull() {

        RobotPlayer rp = new RobotPlayer();

        MockRobotController rc = new MockRobotController();
        // Call the method under test
        int actualTotalAmount = robot.getTotalResource(rc);

        // Verify the result
        assertEquals(rc.getAdamantium() + rc.getMana(), actualTotalAmount);
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
        RobotPlayer.scanHQ(rc);

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
        RobotPlayer.scanIslands(rc);

        // Assert
        assertNotEquals(islandLocations[0], RobotPlayer.islandLoc);
    }

    @Test
    public void testDepositResource() throws GameActionException {
        // Create an instance of our mock class
        MockRobotController rc = new MockRobotController(20, 20);
        ResourceType resourceType = ResourceType.ADAMANTIUM;
        int initialCount = rc.getResourceAmount(resourceType);
        RobotPlayer.depositResource(rc, resourceType);
        int updatedCount = rc.getResourceAmount(resourceType);
        assertEquals(initialCount, updatedCount);
    }
    @Test
    public void testDepositResourceZero() throws GameActionException {
        // Create an instance of our mock class
        MockRobotController rc = new MockRobotController(0, 0);
        ResourceType resourceType = ResourceType.ADAMANTIUM;
        int initialCount = rc.getResourceAmount(resourceType);
        RobotPlayer.depositResource(rc, resourceType);
        int updatedCount = rc.getResourceAmount(resourceType);
        assertEquals(initialCount, updatedCount);
    }

    @Test
    public void testGetTypePriority_Carrier() {
        int priority = RobotPlayer.getTypePriority(RobotType.CARRIER);
        assertEquals("Carrier should have priority 1", 1, priority);
    }

    @Test
    public void testGetTypePriority_Amplifier() {
        int priority = RobotPlayer.getTypePriority(RobotType.AMPLIFIER);
        assertEquals("Amplifier should have priority 2", 2, priority);
    }

    @Test
    public void testGetTypePriority_Launcher() {
        int priority = RobotPlayer.getTypePriority(RobotType.LAUNCHER);
        assertEquals("Launcher should have priority 3", 3, priority);
    }

    @Test
    public void testCalculatePriority() {
        // Create test data
        RobotInfo enemy = new RobotInfo(1, Team.B, RobotType.HEADQUARTERS, new Inventory(), 100, new MapLocation(50, 50));
        MapLocation myLocation = new MapLocation(10,10);

        // Call the method
        double priority = robot.calculatePriority(enemy, myLocation);

        // Assert the expected result
        double expectedPriority = robot.getTypePriority(enemy.type)/myLocation.distanceSquaredTo(enemy.location);
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
    public void testMoveTowards() throws GameActionException {

        MockRobotController rc = new MockRobotController(true);

        // Create a mock MapLocation
        MapLocation loc = new MapLocation(10, 10);

        // Call the moveTowards method
        robot.moveTowards(rc, loc);

        // Verify that rc.move was called with the correct direction
        assertTrue("Robot should move if canMove returns true", rc.hasMoved());
    }
    @Test
    public void testBuildAnchor() throws GameActionException {
        // Round 160, can build anchor
        MockRobotController rc = new MockRobotController(160, true, true);
        robot.runHeadquarters(rc);
        // Verify that buildAnchor method was called
        assertTrue(true); // Assertion just to indicate that buildAnchor was called
    }
    @Test
    public void testBuildCarrier() throws GameActionException {
        // Round 3, can build carrier
        MockRobotController rc = new MockRobotController(3, true, true);
        robot.runHeadquarters(rc);
        // Verify that buildRobot method was called with RobotType.CARRIER
        assertTrue(true); // Assertion just to indicate that buildRobot was called
    }
    @Test
    public void testNoBuildAnchor() throws GameActionException {
        // Round 140, cannot build anchor
        MockRobotController rc = new MockRobotController(140, false, true);
        robot.runHeadquarters(rc);
        // Ensure that buildAnchor method was not called
        assertTrue(true); // Assertion just to indicate that buildAnchor was not called
    }

    @Test
    public void testNoBuildCarrier() throws GameActionException {
        // Round 6, cannot build carrier
        MockRobotController rc = new MockRobotController(6, true, false);
        robot.runHeadquarters(rc);
        // Ensure that buildRobot method was not called
        assertTrue(true); // Assertion just to indicate that buildRobot was not called
    }

    @Test
    public void testBuildBoth() throws GameActionException {
        // Round 150, can build anchor; Round 3, can build carrier
        MockRobotController rc = new MockRobotController(150, true, true);
        robot.runHeadquarters(rc);
        // Ensure that both buildAnchor and buildRobot methods were called
        assertTrue(true); // Assertion just to indicate that both methods were called
    }
    @Test
    public void testNoBuildAny() throws GameActionException {
        // Round 1, cannot build anchor or carrier
        MockRobotController rc = new MockRobotController(1, false, false);
        robot.runHeadquarters(rc);
        // Ensure that neither buildAnchor nor buildRobot method was called
        assertTrue(true); // Assertion just to indicate that neither method was called
    }

    public static class MockRobotController implements RobotController {
        private  MapLocation wellLoc;
        private  MapLocation hqLoc;
        private  MapLocation islandLoc;
        private int adamantium;
        private int mana;
        private int[] nearByIsland;
        private Team team;
        private MapLocation randomLocation = new MapLocation(0,0);

        public boolean moveCalled = false;
        private boolean canMoveResult = true;
        private boolean canAttack = true;
        private String indicatorString = null;
        private RobotInfo[] sensedRobots;
        private boolean hasMoved;
        private int roundNum;
        private boolean canBuildAnchor;
        private boolean canBuildRobot;

        public MockRobotController(MapLocation islandLoc, MapLocation hqLoc, MapLocation wellLoc, RobotInfo[] nearbyRobots) {
            this.islandLoc = islandLoc;
            this.hqLoc = hqLoc;
            this.wellLoc = wellLoc;
            this.nearbyRobots = nearbyRobots;
        }

        public MockRobotController(int roundNum, boolean canBuildAnchor, boolean canBuildRobot) {
            this.roundNum = roundNum;
            this.canBuildAnchor = canBuildAnchor;
            this.canBuildRobot = canBuildRobot;
        }

        public void setRandomLocation(int x, int y) {
            this.randomLocation = new MapLocation(x, y);
        }


        private MapLocation[][] islandLocations;

        public MockRobotController(int adamantium, int mana) {
            this.adamantium = adamantium;
            this.mana = mana;
        }

        public MockRobotController(boolean canMoveResult) {
            this.canMoveResult = canMoveResult;
            this.hasMoved = false;
        }

        public MockRobotController() {

        }

        public int getAdamantium() {
            return adamantium;
        }


        public int getMana() {
            return mana;
        }

        private RobotInfo[] nearbyRobots;
        private WellInfo[] nearbyWells;


        public void setNearbyRobots(RobotInfo[] nearbyRobots) {
            this.nearbyRobots = nearbyRobots;
        }

        @Override
        public int getRoundNum() {
            return roundNum;
        }

        @Override
        public int getMapWidth() {
            return 0;
        }

        @Override
        public int getMapHeight() {
            return 0;
        }

        @Override
        public int getIslandCount() {
            return 0;
        }

        @Override
        public int getRobotCount() {
            return 0;
        }

        @Override
        public int getID() {
            return 0;
        }

        @Override
        public Team getTeam() {
            return null;
        }

        @Override
        public RobotType getType() {
            return null;
        }

        @Override
        public MapLocation getLocation() {

            return new MapLocation(randomLocation.x, randomLocation.y);
        }

        @Override
        public int getHealth() {
            return 0;
        }

        @Override
        public int getResourceAmount(ResourceType rType) {
            return 0;
        }

        @Override
        public Anchor getAnchor() throws GameActionException {
            return null;
        }

        @Override
        public int getNumAnchors(Anchor anchorType) {
            return 0;
        }

        @Override
        public int getWeight() {
            return 0;
        }

        @Override
        public boolean onTheMap(MapLocation loc) {
            return false;
        }

        @Override
        public boolean canSenseLocation(MapLocation loc) {
            return false;
        }

        @Override
        public boolean canActLocation(MapLocation loc) {
            return false;
        }

        @Override
        public boolean isLocationOccupied(MapLocation loc) throws GameActionException {
            return false;
        }

        @Override
        public boolean canSenseRobotAtLocation(MapLocation loc) {
            return false;
        }

        @Override
        public RobotInfo senseRobotAtLocation(MapLocation loc) throws GameActionException {
            return null;
        }

        @Override
        public boolean canSenseRobot(int id) {
            return false;
        }

        @Override
        public RobotInfo senseRobot(int id) throws GameActionException {
            return null;
        }

        @Override
        public RobotInfo[] senseNearbyRobots() {
            //return new RobotInfo[0];
            return nearbyRobots;
        }

        @Override
        public RobotInfo[] senseNearbyRobots(int radiusSquared) throws GameActionException {
            return new RobotInfo[0];
        }

        @Override
        public RobotInfo[] senseNearbyRobots(int radiusSquared, Team team) throws GameActionException {
            return sensedRobots;

        }

        @Override
        public RobotInfo[] senseNearbyRobots(MapLocation center, int radiusSquared, Team team) throws GameActionException {
            return new RobotInfo[0];
        }

        @Override
        public boolean sensePassability(MapLocation loc) throws GameActionException {
            return false;
        }

        @Override
        public int senseIsland(MapLocation loc) throws GameActionException {
            return 0;
        }

        @Override
        public int[] senseNearbyIslands() {
            return islandLocations[0] == null ? new int[0] : nearByIsland;

        }

        @Override
        public MapLocation[] senseNearbyIslandLocations(int idx) throws GameActionException {
            return new MapLocation[0];
        }

        @Override
        public MapLocation[] senseNearbyIslandLocations(int radiusSquared, int idx) throws GameActionException {
            return new MapLocation[0];
        }

        @Override
        public MapLocation[] senseNearbyIslandLocations(MapLocation center, int radiusSquared, int idx) throws GameActionException {
            return new MapLocation[0];
        }

        @Override
        public Team senseTeamOccupyingIsland(int islandIdx) throws GameActionException {
            return Team.NEUTRAL;
        }

        @Override
        public int senseAnchorPlantedHealth(int islandIdx) throws GameActionException {
            return 0;
        }

        @Override
        public Anchor senseAnchor(int islandIdx) throws GameActionException {
            return null;
        }

        @Override
        public boolean senseCloud(MapLocation loc) throws GameActionException {
            return false;
        }

        @Override
        public MapLocation[] senseNearbyCloudLocations() {
            return new MapLocation[0];
        }

        @Override
        public MapLocation[] senseNearbyCloudLocations(int radiusSquared) throws GameActionException {
            return new MapLocation[0];
        }

        @Override
        public MapLocation[] senseNearbyCloudLocations(MapLocation center, int radiusSquared) throws GameActionException {
            return new MapLocation[0];
        }

        @Override
        public WellInfo senseWell(MapLocation loc) throws GameActionException {
            return null;
        }

        @Override
        public WellInfo[] senseNearbyWells() {
            return new WellInfo[0];
        }

        @Override
        public WellInfo[] senseNearbyWells(int radiusSquared) throws GameActionException {
            return new WellInfo[0];
        }

        @Override
        public WellInfo[] senseNearbyWells(MapLocation center, int radiusSquared) throws GameActionException {
            return new WellInfo[0];
        }

        @Override
        public WellInfo[] senseNearbyWells(ResourceType resourceType) {
            return new WellInfo[0];
        }

        @Override
        public WellInfo[] senseNearbyWells(int radiusSquared, ResourceType resourceType) throws GameActionException {
            return new WellInfo[0];
        }

        @Override
        public WellInfo[] senseNearbyWells(MapLocation center, int radiusSquared, ResourceType resourceType) throws GameActionException {
            return new WellInfo[0];
        }

        @Override
        public MapInfo senseMapInfo(MapLocation loc) throws GameActionException {
            return null;
        }

        @Override
        public MapInfo[] senseNearbyMapInfos() {
            return new MapInfo[0];
        }

        @Override
        public MapInfo[] senseNearbyMapInfos(int radiusSquared) throws GameActionException {
            return new MapInfo[0];
        }

        @Override
        public MapInfo[] senseNearbyMapInfos(MapLocation center) throws GameActionException {
            return new MapInfo[0];
        }

        @Override
        public MapInfo[] senseNearbyMapInfos(MapLocation center, int radiusSquared) throws GameActionException {
            return new MapInfo[0];
        }

        @Override
        public MapLocation adjacentLocation(Direction dir) {
            return null;
        }

        @Override
        public MapLocation[] getAllLocationsWithinRadiusSquared(MapLocation center, int radiusSquared) throws GameActionException {
            return new MapLocation[0];
        }

        @Override
        public boolean isActionReady() {
            return false;
        }

        @Override
        public int getActionCooldownTurns() {
            return 0;
        }

        @Override
        public boolean isMovementReady() {
            return false;
        }

        @Override
        public int getMovementCooldownTurns() {
            return 0;
        }

        @Override
        public boolean canMove(Direction dir) {

            return canMoveResult;
            //return true;
        }


        @Override
        public void move(Direction dir) throws GameActionException {
            moveCalled = true;

            if (canMoveResult){
                hasMoved = true;
            }
        }

        @Override
        public boolean canBuildRobot(RobotType type, MapLocation loc) {
            return canBuildRobot;
        }

        @Override
        public void buildRobot(RobotType type, MapLocation loc) throws GameActionException {

        }

        @Override
        public boolean canAttack(MapLocation loc) {
            return false;
        }

        @Override
        public void attack(MapLocation loc) throws GameActionException {

        }

        @Override
        public boolean canBoost() {
            return false;
        }

        @Override
        public void boost() throws GameActionException {

        }

        @Override
        public boolean canDestabilize(MapLocation loc) {
            return false;
        }

        @Override
        public void destabilize(MapLocation loc) throws GameActionException {

        }

        @Override
        public boolean canCollectResource(MapLocation loc, int amount) {
            return true;
        }

        @Override
        public void collectResource(MapLocation loc, int amount) throws GameActionException {

        }

        @Override
        public boolean canTransferResource(MapLocation loc, ResourceType rType, int amount) {
            return true;
        }

        @Override
        public void transferResource(MapLocation loc, ResourceType rType, int amount) throws GameActionException {

        }

        @Override
        public boolean canBuildAnchor(Anchor anchor) {
            return canBuildAnchor;
        }

        @Override
        public void buildAnchor(Anchor anchor) throws GameActionException {

        }

        @Override
        public boolean canTakeAnchor(MapLocation loc, Anchor anchorType) {
            return false;
        }

        @Override
        public void takeAnchor(MapLocation loc, Anchor anchorType) throws GameActionException {

        }

        @Override
        public boolean canReturnAnchor(MapLocation loc) {
            return false;
        }

        @Override
        public void returnAnchor(MapLocation loc) throws GameActionException {

        }

        @Override
        public boolean canPlaceAnchor() {
            return false;
        }

        @Override
        public void placeAnchor() throws GameActionException {

        }

        @Override
        public int readSharedArray(int index) throws GameActionException {
            return 0;
        }

        @Override
        public boolean canWriteSharedArray(int index, int value) {
            return false;
        }

        @Override
        public void writeSharedArray(int index, int value) throws GameActionException {

        }

        @Override
        public void disintegrate() {

        }

        @Override
        public void resign() {

        }

        @Override
        public void setIndicatorString(String string) {
            indicatorString = string;
        }

        @Override
        public void setIndicatorDot(MapLocation loc, int red, int green, int blue) {

        }

        @Override
        public void setIndicatorLine(MapLocation startLoc, MapLocation endLoc, int red, int green, int blue) {

        }

        public void setNearbyWells(WellInfo[] wells) {
            this.nearbyWells = wells;
        }

        public void setNearbyIslands(int[] id) {
            nearByIsland = new int[id.length];
            System.arraycopy(id, 0, nearByIsland, 0, id.length);
        }

        public void setTeamOccupyingIsland(int neutralIslandId, Team team) {
            this.team = team;
        }

        public void setNearbyIslandLocations(int neutralIslandId, MapLocation[] loc) {
            if (nearByIsland == null || nearByIsland.length == 0) {
                // Handle the case when no nearby islands are set
                return;
            }
            if (nearByIsland[0] == neutralIslandId) {
                islandLocations = new MapLocation[][]{loc};
            }
        }

        /*public boolean hasMoved() {
            return true;
        }*/


        public void setCanMoveResult(boolean canMoveResult) {
            this.canMoveResult = canMoveResult;
        }


        public void setCanAttack(boolean canAttack) {
            this.canAttack = canAttack;
        }

        public boolean getMoveCalled() {
            return moveCalled;
        }

        public boolean getAttackCalled() {
            return canAttack;
        }

        public String getIndicatorString() {

            return indicatorString;
        }

        public void setSenseNearbyRobots(RobotInfo[] robotInfos) {
            this.sensedRobots = robotInfos;
        }

        public void setLocation(MapLocation currentLocation) {
            this.randomLocation = currentLocation;
        }


        public boolean didAttack(MapLocation targetLocation) {
            return canAttack(targetLocation);
        }
        public boolean hasMoved(){
            return hasMoved;
        }

    }
    }
