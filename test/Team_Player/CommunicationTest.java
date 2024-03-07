package Team_Player;

import battlecode.common.*;
import org.junit.Ignore;
import org.junit.Test;

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
    }
    @Ignore
    public void locationToInt_and_intToLocation_Test() throws GameActionException {
        MockRobotController rc = new MockRobotController();
        MapLocation location = new MapLocation(3, 5);
        int encoded = Communication.locationToInt(rc, location);
        MapLocation decoded = Communication.intToLocation(rc, encoded);
        assertEquals(location, decoded);

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

}