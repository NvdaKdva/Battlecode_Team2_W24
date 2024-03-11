package Team_Player;

import battlecode.common.*;
import battlecode.world.Inventory;
import org.junit.Test;

import static org.junit.Assert.*;

public class AmplifierTest {



/*
    @Test
    public void testRunAmplifier() throws GameActionException {
        // Create a mock RobotController
        MockRobotController rc = new MockRobotController();
        RobotPlayer robot = new RobotPlayer();
        int turnCount = 2;
        Map myMap = new Map(20,20);

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
        Amplifier.runAmplifier(rc, turnCount, myMap);

        // Check if the robot moved towards the island
        assertNotEquals(robot.islandLoc, rc.moveTowardsLocation);
    }
    @Test
    public void testRunAmplifierNoIsland() throws GameActionException {
        // Create a mock RobotController
        MockRobotController rc = new MockRobotController();
        RobotPlayer robot = new RobotPlayer();
        int turnCount = 2;
        Map myMap = new Map(20, 20);

        // Set up mock locations with no island location
        robot.islandLoc = null;
        robot.hqLoc = new MapLocation(5, 5);
        robot.wellLoc = new MapLocation(7, 7);

        // Set up mock nearby robots
        RobotInfo[] nearbyRobots = {
                new RobotInfo(1, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(6, 6))
        };
        rc.setNearbyRobots(nearbyRobots);

        // Call the method to test
        Amplifier.runAmplifier(rc, turnCount, myMap);

        // Check if the robot did not move towards the island
        assertNull(rc.moveTowardsLocation);
    }
    @Test
    public void testRunAmplifierNearbyAmplifier() throws GameActionException {
        // Create a mock RobotController
        MockRobotController rc = new MockRobotController();
        RobotPlayer robot = new RobotPlayer();
        int turnCount = 2;
        Map myMap = new Map(20, 20);

        // Set up mock locations with no island location
        robot.islandLoc = new MapLocation(10, 10);
        robot.hqLoc = new MapLocation(5, 5);
        robot.wellLoc = new MapLocation(7, 7);

        // Set up mock nearby robots with an amplifier nearby
        RobotInfo[] nearbyRobots = {
                new RobotInfo(1, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(9, 9))
        };
        rc.setNearbyRobots(nearbyRobots);

        // Call the method to test
        Amplifier.runAmplifier(rc, turnCount, myMap);

        // Check if the robot did not move towards the well or HQ
        assertNull(rc.moveTowardsLocation);
    }



        @Test
    public void testRunAmplifierNearbyWell() throws GameActionException {
            // Create a mock RobotController
            MockRobotController rc = new MockRobotController();
            RobotPlayer robot = new RobotPlayer();
            int turnCount = 2;
            Map myMap = new Map(20, 20);

            // Set up mock locations with no island location
            robot.islandLoc = new MapLocation(10, 10);
            robot.hqLoc = new MapLocation(5, 5);
            robot.wellLoc = new MapLocation(7, 7);

            // Set up mock nearby robots with a well nearby
            RobotInfo[] nearbyRobots = {
                    new RobotInfo(1, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(6, 6))
            };
            rc.setNearbyRobots(nearbyRobots);

            // Call the method to test
            Amplifier.runAmplifier(rc, turnCount, myMap);

            // Check if the robot moved towards the well
            assertNotEquals(robot.wellLoc, rc.moveTowardsLocation);
        }


        @Test
        public void testRunAmplifierNearbyHQ() throws GameActionException {
            // Create a mock RobotController
            MockRobotController rc = new MockRobotController();
            RobotPlayer robot = new RobotPlayer();
            int turnCount = 2;
            Map myMap = new Map(20, 20);

            // Set up mock locations with no island location
            robot.islandLoc = new MapLocation(10, 10);
            robot.hqLoc = new MapLocation(5, 5);
            robot.wellLoc = new MapLocation(7, 7);

            // Set up mock nearby robots with an amplifier nearby
            RobotInfo[] nearbyRobots = {
                    new RobotInfo(1, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(6, 6))
            };
            rc.setNearbyRobots(nearbyRobots);

            // Call the method to test
            Amplifier.runAmplifier(rc, turnCount, myMap);

            // Check if the robot moved towards the HQ
            assertNotEquals(robot.hqLoc, rc.moveTowardsLocation);
        }
        @Test
        public void testRunAmplifierNearbyHQNoWell() throws GameActionException {
            // Create a mock RobotController
            MockRobotController rc = new MockRobotController();
            RobotPlayer robot = new RobotPlayer();
            int turnCount = 2;
            Map myMap = new Map(20, 20);

            // Set up mock locations with no island location
            robot.islandLoc = new MapLocation(10, 10);
            robot.hqLoc = new MapLocation(5, 5);
            robot.wellLoc = null;

            // Set up mock nearby robots with an amplifier nearby
            RobotInfo[] nearbyRobots = {
                    new RobotInfo(1, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(6, 6))
            };
            rc.setNearbyRobots(nearbyRobots);

            // Call the method to test
            Amplifier.runAmplifier(rc, turnCount, myMap);

            // Check if the robot moved towards the HQ
            assertNull(rc.moveTowardsLocation);
        }
        @Test
        public void testRunAmplifierNearbyWellNoHQ() throws GameActionException {
            // Create a mock RobotController
            MockRobotController rc = new MockRobotController();
            RobotPlayer robot = new RobotPlayer();
            int turnCount = 2;
            Map myMap = new Map(20, 20);

            // Set up mock locations with no island location
            robot.islandLoc = new MapLocation(10, 10);
            robot.hqLoc = null;
            robot.wellLoc = new MapLocation(7, 7);

            // Set up mock nearby robots with an amplifier nearby
            RobotInfo[] nearbyRobots = {
                    new RobotInfo(1, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(6, 6))
            };
            rc.setNearbyRobots(nearbyRobots);

            // Call the method to test
            Amplifier.runAmplifier(rc, turnCount, myMap);

            // Check if the robot moved towards the well
            assertNull(rc.moveTowardsLocation);
        }
        @Test
        public void testRunAmplifierNearbyHQWell() throws GameActionException {
            // Create a mock RobotController
            MockRobotController rc = new MockRobotController();
            RobotPlayer robot = new RobotPlayer();
            int turnCount = 2;
            Map myMap = new Map(20, 20);

            // Set up mock locations with no island location
            robot.islandLoc = new MapLocation(10, 10);
            robot.hqLoc = new MapLocation(5, 5);
            robot.wellLoc = new MapLocation(7, 7);

            // Set up mock nearby robots with an amplifier nearby
            RobotInfo[] nearbyRobots = {
                    new RobotInfo(1, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(6, 6))
            };
            rc.setNearbyRobots(nearbyRobots);

            // Call the method to test
            Amplifier.runAmplifier(rc, turnCount, myMap);

            // Check if the robot moved towards the well
            assertNotEquals(robot.wellLoc, rc.moveTowardsLocation);
        }

        @Test
        public void whybother() throws GameActionException {
            MockRobotController mrc = new MockRobotController(RobotType.AMPLIFIER,0,0);
            Map myMap = new Map(20,20);
            Amplifier.runAmplifier(mrc,2,myMap);
        }
        */
}