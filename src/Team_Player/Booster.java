package Team_Player;

import battlecode.common.*;

public class Booster {
    /** Run a single turn for a Booster.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn. */
    static MapLocation mySpot;
    static int[] booster_arr = {5,5,5,10,5,15,10,5,10,10,10,15,15,5,15,10,15,15};

    static void runBooster(RobotController rc, int turnCount, Map myMap) throws GameActionException {

        myMap.updateMap(rc, turnCount);

        //change locations if not 20x20
        if(turnCount == 1) {
            int w4th = rc.getMapWidth(), h4th = rc.getMapHeight();
            if(w4th != 20 && h4th != 20) {
                w4th = w4th/4;
                h4th = h4th/4;
                booster_arr[0]  = w4th;
                booster_arr[1]  = h4th;
                booster_arr[2]  = w4th;
                booster_arr[3]  = h4th * 2;
                booster_arr[4]  = w4th;
                booster_arr[5]  = h4th * 3;
                booster_arr[6]  = w4th * 2;
                booster_arr[7]  = h4th;
                booster_arr[8]  = w4th * 2;
                booster_arr[9]  = h4th * 2;
                booster_arr[10] = w4th * 2;
                booster_arr[11] = h4th * 3;
                booster_arr[12] = w4th * 3;
                booster_arr[13] = h4th;
                booster_arr[14] = w4th * 3;
                booster_arr[15] = h4th * 2;
                booster_arr[16] = w4th * 3;
                booster_arr[17] = h4th * 3;
            }

            //find my spot if I don't have one
            int spot = rc.readSharedArray(61);
            switch(spot % 9) {
                case 0:
                    mySpot = new MapLocation(booster_arr[0],booster_arr[1]);
                    break;
                case 1:
                    mySpot = new MapLocation(booster_arr[2],booster_arr[3]);
                    break;
                case 2:
                    mySpot = new MapLocation(booster_arr[4],booster_arr[5]);
                    break;
                case 3:
                    mySpot = new MapLocation(booster_arr[6],booster_arr[7]);
                    break;
                case 4:
                    mySpot = new MapLocation(booster_arr[8],booster_arr[9]);
                    break;
                case 5:
                    mySpot = new MapLocation(booster_arr[10],booster_arr[11]);
                    break;
                case 6:
                    mySpot = new MapLocation(booster_arr[12],booster_arr[13]);
                    break;
                case 7:
                    mySpot = new MapLocation(booster_arr[14],booster_arr[15]);
                    break;
                case 8:
                    mySpot = new MapLocation(booster_arr[16],booster_arr[17]);
                    break;
            }
        }

        //move to my spot then move randomly near it
        if(!rc.getLocation().isAdjacentTo(mySpot)) { Shared.moveTowards(rc,mySpot); }
        else { Shared.moveRandom(rc); }
    }
}
