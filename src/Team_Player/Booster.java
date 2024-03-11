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
            if(rc.getMapWidth() != 20 && rc.getMapHeight() != 20) { booster_arr = upDateArray(rc, rc.getMapWidth(), rc.getMapHeight()); }

            //find my spot if I don't have one
            mySpot = Booster.getMySpot(rc.readSharedArray(61));
        }

        //move to my spot then move randomly near it
        if(!rc.getLocation().isAdjacentTo(mySpot)) {
            Shared.moveTowards(rc,mySpot); }
        else { Shared.moveRandom(rc); }
    }

    public static int[] upDateArray(RobotController rc, int mapWidth, int mapHeight) {
        int w4th = mapWidth/4;
        int h4th = mapHeight/4;
        int[] placement_arr = {5,5,5,10,5,15,10,5,10,10,10,15,15,5,15,10,15,15};
        placement_arr[0]  = w4th;
        placement_arr[1]  = h4th;
        placement_arr[2]  = w4th;
        placement_arr[3]  = h4th * 2;
        placement_arr[4]  = w4th;
        placement_arr[5]  = h4th * 3;
        placement_arr[6]  = w4th * 2;
        placement_arr[7]  = h4th;
        placement_arr[8]  = w4th * 2;
        placement_arr[9]  = h4th * 2;
        placement_arr[10] = w4th * 2;
        placement_arr[11] = h4th * 3;
        placement_arr[12] = w4th * 3;
        placement_arr[13] = h4th;
        placement_arr[14] = w4th * 3;
        placement_arr[15] = h4th * 2;
        placement_arr[16] = w4th * 3;
        placement_arr[17] = h4th * 3;

        return placement_arr;
    }

    public static MapLocation getMySpot(int num_boosters) {
        MapLocation spot = null;

        switch(num_boosters % 9) {
            case 0:
                spot = new MapLocation(booster_arr[0],booster_arr[1]);
                break;
            case 1:
                spot = new MapLocation(booster_arr[2],booster_arr[3]);
                break;
            case 2:
                spot = new MapLocation(booster_arr[4],booster_arr[5]);
                break;
            case 3:
                spot = new MapLocation(booster_arr[6],booster_arr[7]);
                break;
            case 4:
                spot = new MapLocation(booster_arr[8],booster_arr[9]);
                break;
            case 5:
                spot = new MapLocation(booster_arr[10],booster_arr[11]);
                break;
            case 6:
                spot = new MapLocation(booster_arr[12],booster_arr[13]);
                break;
            case 7:
                spot = new MapLocation(booster_arr[14],booster_arr[15]);
                break;
            case 8:
                spot = new MapLocation(booster_arr[16],booster_arr[17]);
                break;
        }
        return spot;
    }

}
