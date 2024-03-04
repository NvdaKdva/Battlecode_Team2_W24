package Team_Player;

import battlecode.common.*;
import battlecode.world.Inventory;
import org.junit.Test;

import static org.junit.Assert.*;

public class AmplifierTest {

    @Test
    public void testRunAmplifier() throws GameActionException {
        // Create a mock RobotController
        MockRobotController rc = new MockRobotController();
        RobotPlayer robot = new RobotPlayer();

        // Set up mock locations
        robot.islandLoc = new MapLocation(10, 10);
        robot.hqLoc = new MapLocation(5, 5);
        robot.wellLoc = new MapLocation(7, 7);

        // Set up mock nearby robots
        RobotInfo[] nearbyRobots = {

                new RobotInfo(1, Team.A, RobotType.AMPLIFIER, new Inventory(), 50,  new MapLocation(6, 6))
        };
        rc.setNearbyRobots(nearbyRobots);

        // Call the method to test
        Amplifier.runAmplifier(rc);

        // Check if the robot moved towards the island
        assertNotEquals(robot.islandLoc, rc.moveTowardsLocation);
    }
}