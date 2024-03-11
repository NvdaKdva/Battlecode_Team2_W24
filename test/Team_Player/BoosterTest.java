package Team_Player;

import org.junit.Ignore;
import org.junit.Test;

import battlecode.common.*;
import static org.junit.Assert.*;

public class BoosterTest {

    @Test
    public void testGetMySpot() throws GameActionException {
         MapLocation testResult = null;
         Booster booster = new Booster();
         
         MapLocation expectedResult_1 = new MapLocation(5,5);
         testResult = Booster.getMySpot(0);
         assertEquals(testResult,expectedResult_1);

         MapLocation expectedResult_2 = new MapLocation(5,10);
         testResult = Booster.getMySpot(1);
         assertEquals(testResult,expectedResult_2);

         MapLocation expectedResult_3 = new MapLocation(5,15);
         testResult = Booster.getMySpot(2);
         assertEquals(testResult,expectedResult_3);

         MapLocation expectedResult_4 = new MapLocation(10,5);
         testResult = Booster.getMySpot(3);
         assertEquals(testResult,expectedResult_4);

         MapLocation expectedResult_5 = new MapLocation(10,10);
         testResult = Booster.getMySpot(4);
         assertEquals(testResult,expectedResult_5);

         MapLocation expectedResult_6 = new MapLocation(10,15);
         testResult = Booster.getMySpot(5);
         assertEquals(testResult,expectedResult_6);

         MapLocation expectedResult_7 = new MapLocation(15,5);
         testResult = Booster.getMySpot(6);
         assertEquals(testResult,expectedResult_7);

         MapLocation expectedResult_8 = new MapLocation(15,10);
         testResult = Booster.getMySpot(7);
         assertEquals(testResult,expectedResult_8);

         MapLocation expectedResult_9 = new MapLocation(15,15);
         testResult = Booster.getMySpot(8);
         assertEquals(testResult,expectedResult_9);
    }

    @Test
    public void testUpDateArray() throws GameActionException {
        // Initialize a mock RobotController
        MockRobotController rc = new MockRobotController(RobotType.BOOSTER,4,4);
        Booster booster_1 = new Booster();
        Booster booster_2 = new Booster();
        
        int[] expected_result_1 = {5,5,5,10,5,15,10,5,10,10,10,15,15,5,15,10,15,15};
        int[] result_1 = booster_1.upDateArray(rc,20,20);
        assertArrayEquals(expected_result_1, result_1);

        int[] expected_result_2 = {10,10,10,20,10,30,20,10,20,20,20,30,30,10,30,20,30,30};
        int[] result_2 = booster_2.upDateArray(rc,40,40);
        assertArrayEquals(expected_result_2, result_2);
    }

    @Test
    public void runBoosterTest() throws GameActionException {
        MockRobotController mrc = new MockRobotController(RobotType.BOOSTER, 4,10);
        Booster booster_3 = new Booster();
        Map myMap = new Map(20,20);
        booster_3.mySpot = booster_3.getMySpot(1);


        booster_3.runBooster(mrc,2,myMap);
        assert mrc.moveCalled;

        mrc.moveCalled = false;
        MapLocation non_adj_loc = new MapLocation(4,4);
        mrc.setLocation(non_adj_loc);

        booster_3.runBooster(mrc, 2, myMap);
        assert mrc.isMoveRandomCalled() == true;
    }
}


