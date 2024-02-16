package Team_Player;

import battlecode.common.*;

import java.util.Random;

public class Carriers {

    static final Random rng = new Random(6147);

    /** Array containing all the possible movement directions. */
    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };


    static void depositResource(RobotController rc, ResourceType type, MapLocation hqLoc) throws GameActionException {
        int amount = rc.getResourceAmount(type);
        if (amount > 0){
            if(rc.canTransferResource(hqLoc, type, amount)){
                rc.transferResource(hqLoc, type, amount);
            }
        }
    }

    static int getTotalResource(RobotController rc){
        return rc.getResourceAmount(ResourceType.ADAMANTIUM) + rc.getResourceAmount(ResourceType.MANA);
    }
}
