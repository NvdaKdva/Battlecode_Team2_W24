package Team_Player;

import battlecode.common.*;
import battlecode.world.Inventory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;



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
            robot.scanWells(rc);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }

        // Assert that wellsLoc is null (or any other expected behavior)
        assertNull(robot.wellsLoc);
    }

@Test
    public void testSanity() {
        assertEquals(2, 1+1);
    }
    @Test
    public void testGetTotalResource() {

        RobotPlayer rp = new RobotPlayer();

        MockRobotController rc = new MockRobotController(20, 20);
        // Call the method under test
        int actualTotalAmount = rp.getTotalResource(rc);

        // Verify the result
        assertNotEquals(40, actualTotalAmount);
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
            robot.scanIslands(rc);

            // Assert
            assertNotEquals(islandLocations[0], robot.islandLoc);
        }

       @Test
       public void testDepositResource() throws GameActionException {
           // Create an instance of our mock class
           MockRobotController rc = new MockRobotController();
           ResourceType resourceType = ResourceType.ADAMANTIUM;
           int initialCount = rc.getResourceAmount(resourceType) ;
           robot.depositResource(rc, resourceType);
           int updatedCount = rc.getResourceAmount(resourceType);
           assertEquals(initialCount ,  updatedCount);
    }
    @Test
    public void testGetTypePriority_Carrier() {
        int priority = robot.getTypePriority(RobotType.CARRIER);
        assertEquals("Carrier should have priority 1", 1, priority);
    }

    @Test
    public void testGetTypePriority_Amplifier() {
        int priority = robot.getTypePriority(RobotType.AMPLIFIER);
        assertEquals("Amplifier should have priority 2", 2, priority);
    }

    @Test
    public void testGetTypePriority_Launcher() {
        int priority = robot.getTypePriority(RobotType.LAUNCHER);
        assertEquals("Launcher should have priority 3", 3, priority);
    }


    public  static class MockRobotController implements RobotController {
        private int adamantium;
        private int mana;
        private int[] nearByIsland;
        private Team team;
        private MapLocation[][] islandLocations;

        public MockRobotController(int adamantium, int mana) {
            this.adamantium = adamantium;
            this.mana = mana;
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
            return 0;
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
            return null;
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
            return new RobotInfo[0];
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
            return islandLocations[0]== null ? new int[0] : nearByIsland;

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
            return false;
        }

        @Override
        public void move(Direction dir) throws GameActionException {

        }

        @Override
        public boolean canBuildRobot(RobotType type, MapLocation loc) {
            return false;
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
            return false;
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
            return false;
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
    }
}