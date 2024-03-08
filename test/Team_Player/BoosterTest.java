package Team_Player;

import org.junit.Ignore;
import org.junit.Test;

import battlecode.common.*;
import static org.junit.Assert.*;

public class BoosterTest {
     @Test
        public void testRunBooster() throws GameActionException {
            // Initialize a mock RobotController
            RobotController rc = new MockRobotController();
            Map myMap = new Map(20,20);

            // Create a Booster instance
            Booster booster = new Booster();

            // Test case 1: Check if the booster moves towards the spot if not adjacent
            MapLocation mySpot = new MapLocation(5, 5); // Assuming mySpot is (5,5)
            MapLocation currentLocation = new MapLocation(4, 4); // Assume current location is (4,4)
            ((MockRobotController) rc).setLocation(currentLocation); // Set current location for the mock

            // Call the runBooster method
            booster.runBooster(rc, 1, myMap);

            // Check if the booster moved towards the spot
            assertNotEquals(mySpot, ((MockRobotController) rc).getLocation());

            // Test case 2: Check if the booster moves randomly if already adjacent to the spot
            currentLocation = new MapLocation(5, 5); // Assume current location is already at the spot
            ((MockRobotController) rc).setLocation(currentLocation); // Set current location for the mock

            // Call the runBooster method again
            booster.runBooster(rc, 1, myMap);

            // Check if the booster moved randomly
            assertEquals(mySpot, ((MockRobotController) rc).getLocation());
        }


}


