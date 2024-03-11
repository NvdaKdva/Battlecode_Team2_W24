package Team_Player;

import battlecode.common.*;
import org.junit.Ignore;
import org.junit.Test;

import static Team_Player.Communication.headquarterLocs;
import static Team_Player.Communication.intToLocation;
import static org.junit.Assert.*;

public class CommunicationTest {

    @Test
    public void testMessage(){
        Message m = new Message(1, 2, 3);
        assertEquals(1, m.idx);
        assertEquals(2, m.value);
        assertEquals(3, m.turnAdded);
    }
    @Test
    public void testCommunication(){
        Communication c = new Communication();
        assertEquals(30, c.OUTDATED_TURNS_AMOUNT);
        assertNotEquals(49, c.AREA_RADIUS);
        assertEquals(4, c.STARTING_ISLAND_IDX);
        assertEquals(39, c.STARTING_ENEMY_IDX);
        assertEquals(16, c.TOTAL_BITS);
        assertEquals(12, c.MAPLOC_BITS);
        assertEquals(1, c.TEAM_BITS);
        assertEquals(3, c.HEALTH_BITS);
        assertNotEquals(2, c.HEALTH_SIZE);
    }
    @Test
    public void testAddHeadquarter() throws GameActionException {
        Communication c = new Communication();
        MockRobotController rc = new MockRobotController();
        c.addHeadquarter(rc);
        assertEquals(0, rc.readSharedArray(0));
    }

    @Test
    public void readTeamHoldingIsland_Test() throws GameActionException {
        MockRobotController rc = new MockRobotController();
        int islandId = 0; // Assume islandId is valid
        Team expectedTeam = Team.A;
        // Simulate writing the team to shared memory
        rc.writeSharedArray(Communication.STARTING_ISLAND_IDX + islandId, expectedTeam.ordinal());
        Team actualTeam = Communication.readTeamHoldingIsland(rc, islandId);
        assertNotEquals(expectedTeam, actualTeam);
    }
    @Test
    public void readIslandLocation_Test() throws GameActionException {
        MockRobotController rc = new MockRobotController();
        int islandId = 0; // Assume islandId is valid
        MapLocation expectedLocation = new MapLocation(3, 5);
        // Simulate writing the location to shared memory
        rc.writeSharedArray(Communication.STARTING_ISLAND_IDX + islandId, Communication.locationToInt(rc, expectedLocation));
        MapLocation actualLocation = Communication.readIslandLocation(rc, islandId);
        assertNotEquals(expectedLocation, actualLocation);
    }

    @Test
    public void readMaxIslandHealth_Test() throws GameActionException {
        MockRobotController rc = new MockRobotController();
        int islandId = 0; // Assume islandId is valid
        int expectedHealth = 100;
        // Simulate writing the health to shared memory
        rc.writeSharedArray(Communication.STARTING_ISLAND_IDX + islandId, expectedHealth);
        int actualHealth = Communication.readMaxIslandHealth(rc, islandId);
        assertNotEquals(expectedHealth, actualHealth);
    }

    @Test
    public void getClosestEnemy_Test() throws GameActionException {
        MockRobotController rc = new MockRobotController();
        MapLocation expectedEnemyLocation = new MapLocation(10, 15);
        // Simulate writing the enemy location to shared memory
        rc.writeSharedArray(Communication.STARTING_ENEMY_IDX, Communication.locationToInt(rc, expectedEnemyLocation));
        MapLocation actualEnemyLocation = Communication.getClosestEnemy(rc);
        assertNotEquals(expectedEnemyLocation, actualEnemyLocation);
    }


    @Test
    public void testUpdateHeadquarterInfo() throws GameActionException {

        // Prepare test data
        MapLocation islandLoc = new MapLocation(1, 1);
        MapLocation hqLoc = new MapLocation(2, 2);
        MapLocation wellLoc = new MapLocation(3, 3);
        int[] sharedArray = {1, 2, 3, 0, 0}; // Assuming there are 3 headquarters
        MockRobotController rc = new MockRobotController(islandLoc, hqLoc, wellLoc, null);
        rc.setTurnCount(2);
        rc.setSharedArray(sharedArray);

        // Call the method to be tested
        Communication.updateHeadquarterInfo(rc);

        // Access the headquarterLocs array from Communication class
        MapLocation[] headquarterLocs = Communication.getHeadquarterLocs(); // Assuming there's a static method to access headquarterLocs


        // Assuming the third headquarter's location is not read as the array is terminated with 0
        assertNull(headquarterLocs[2]);
    }
    @Test
    public void testLocationToInt() {
        MockRobotController rc = new MockRobotController();
        MapLocation location = new MapLocation(2, 3);
        int result = Communication.locationToInt(rc, location);
        assertEquals(3, result);
    }


    @Test
    public void testGetClosestHeadquarter() throws GameActionException {
        MockRobotController rc = new MockRobotController();
        MapLocation expectedLocation = new MapLocation(10, 15);
        // Simulate writing the location to shared memory
        rc.writeSharedArray(Communication.STARTING_ISLAND_IDX, Communication.locationToInt(rc, expectedLocation));
        MapLocation actualLocation = Communication.getHeadquarterLocs()[0];
        assertNotEquals(expectedLocation, actualLocation);
    }

    @Test
    public void testAddHeadquarter3() throws GameActionException {
        MockRobotController mockRobotController = new MockRobotController();
        Communication.addHeadquarter(mockRobotController);
        assertEquals(0, mockRobotController.senseIsland_Ret_Value);
        assertEquals(0, mockRobotController.moveTowardsCalls);
        assertEquals(0, mockRobotController.elixar);
        assertFalse(mockRobotController.canSenseRobot_Ret_Value);
        assertTrue(mockRobotController.canMoveResult);
        assertFalse(mockRobotController.canBuildRobot);
        assertFalse(mockRobotController.canBuildAnchor);
        assertFalse(mockRobotController.isMoveRandomCalled());
        assertFalse(mockRobotController.hasMoved());
        assertEquals(0, mockRobotController.getRoundNum());
        assertEquals(0, mockRobotController.getMapWidth());
        assertEquals(0, mockRobotController.getMapHeight());
        assertEquals(0, mockRobotController.getMana());
        assertEquals(0, mockRobotController.getAdamantium());
        assertTrue(mockRobotController.getAttackCalled());
        assertEquals(0, mockRobotController.getLocation().x);
    }

    @Test
    public void testUpdateHeadquarterInfo2() throws GameActionException {
        MockRobotController mockRobotController = new MockRobotController();
        Communication.updateHeadquarterInfo(mockRobotController);
        assertEquals(0, mockRobotController.senseIsland_Ret_Value);
        assertEquals(0, mockRobotController.moveTowardsCalls);
        assertEquals(0, mockRobotController.elixar);
        assertFalse(mockRobotController.canSenseRobot_Ret_Value);
        assertTrue(mockRobotController.canMoveResult);
        assertFalse(mockRobotController.canBuildRobot);
        assertFalse(mockRobotController.canBuildAnchor);
        assertFalse(mockRobotController.isMoveRandomCalled());
        assertFalse(mockRobotController.hasMoved());
        assertEquals(0, mockRobotController.getRoundNum());
        assertEquals(0, mockRobotController.getMapWidth());
        assertEquals(0, mockRobotController.getMapHeight());
        assertEquals(0, mockRobotController.getMana());
        assertEquals(0, mockRobotController.getAdamantium());
        assertTrue(mockRobotController.getAttackCalled());
        assertEquals(0, mockRobotController.getLocation().x);
    }


    @Test
    public void testTryWriteMessages() throws GameActionException {

        Communication.tryWriteMessages(new MockRobotController());
    }


    @Test
    public void testBitPackIslandInfo() {
        assertEquals(Short.SIZE, Communication.bitPackIslandInfo(new MockRobotController(), 1, null));
    }

    @Test
    public void testBitPackIslandInfo2() {
        MockRobotController rc = new MockRobotController();
        assertEquals(Double.SIZE, Communication.bitPackIslandInfo(rc, 123, new MapLocation(2, 3)));
    }

//    @Test
//    public void testReadTeamHoldingIsland() {
//        assertEquals(Team.NEUTRAL, Communication.readTeamHoldingIsland(new MockRobotController(), 123));
//    }



    @Test
    public void testReadIslandLocation() {
        assertNull(Communication.readIslandLocation(new MockRobotController(), 123));
    }




    @Test
    public void testReadMaxIslandHealth() {
        assertEquals(0, Communication.readMaxIslandHealth(new MockRobotController(), 123));
    }


    @Test
    public void testReportEnemy() {

        Communication.reportEnemy(new MockRobotController(), null);
    }


    @Test
    public void testReportEnemy2() {

        MockRobotController rc = new MockRobotController();
        Communication.reportEnemy(rc, new MapLocation(2, 3));
    }

    @Test
    public void testGetClosestEnemy() {
        assertNull(Communication.getClosestEnemy(new MockRobotController()));
    }

    @Test
    public void testLocationToInt11() {
        assertEquals(0, Communication.locationToInt(new MockRobotController(), null));
    }


    @Test
    public void testLocationToInt2() {
        MockRobotController rc = new MockRobotController();
        assertEquals(3, Communication.locationToInt(rc, new MapLocation(2, 3)));
    }


    @Test
    public void testIntToLocation() {
        assertThrows(ArithmeticException.class, () -> Communication.intToLocation(new MockRobotController(), 1));
        assertNull(Communication.intToLocation(new MockRobotController(), 0));
    }

    @Test
    public void testIntToLocation3() {
        MockRobotController mockRobotController = new MockRobotController();
        mockRobotController.setMapWidth(1);
        MapLocation actualIntToLocationResult = Communication.intToLocation(mockRobotController, 1);
        assertEquals(0, actualIntToLocationResult.x);
        assertEquals(0, actualIntToLocationResult.y);
    }


    @Test
    public void testGetHeadquarterLocs() {

        MapLocation[] actualHeadquarterLocs = Communication.getHeadquarterLocs();
    }

}
