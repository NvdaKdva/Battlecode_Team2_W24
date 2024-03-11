package Team_Player;

import battlecode.common.*;

public class Amplifier {
    static int[] amp_arr;
    static MapLocation myPlace;

    static void runAmplifier(RobotController rc, int turnCount, Map myMap) throws GameActionException {
        myMap.updateMap(rc, turnCount);

        if(turnCount == 1) {
            amp_arr = setAmpArr(rc.getMapWidth(),rc.getMapHeight());

            myPlace = Amplifier.getMyPlace(rc.readSharedArray(62), amp_arr.length);
        }

        //move to my spot then move randomly near it
        if(!rc.getLocation().isAdjacentTo(myPlace)) {
            Shared.moveTowards(rc,myPlace); }
        else { Shared.moveRandom(rc); }

    }

    static int[] setAmpArr(int width, int height) {
        int wNum, hNum, numPlaces, offset = 0;
        if(width%5 == 0) {wNum = (width / 5) -1;} else {wNum = (width/5);}
        if(height%5 == 0) {hNum = (height / 5) -1;} else {hNum = (height/5);}
        numPlaces = wNum*hNum;
        int[] place_arr = new int[numPlaces*2];
        for(int i = 1; i <= hNum; i++) {
            for(int j = 1; j <= wNum; j++) {
                place_arr[offset] = j * 5;
                place_arr[offset+1] = i * 5;
                offset += 2;
            }
        }
        return place_arr;
    }

    public static MapLocation getMyPlace(int num_amps, int ampArrLen) {
        int offset = num_amps % (ampArrLen/2);
        MapLocation place = new MapLocation(amp_arr[offset*2],amp_arr[offset*2+1]);
        return place;
    }

}