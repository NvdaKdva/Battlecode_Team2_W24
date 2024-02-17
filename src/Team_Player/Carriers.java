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

    static MapLocation hqLoc;
    static MapLocation wellLoc;
    static MapLocation islandLoc;
    static boolean anchorMode = false;

    /**
     * Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runCarrier(RobotController rc) throws GameActionException {
        if(hqLoc == null) hqLoc = Movement.scanHQ(rc);
        if(wellLoc == null) wellLoc = Movement.scanWells(rc);
        if(islandLoc == null) islandLoc = Movement.scanIslands(rc);
        //if(wellsLoc == null) Movement.scanWells(rc);

        //Collect from well if close and inventory not full
        if (wellLoc != null && rc.canCollectResource(wellLoc, -1))
            rc.collectResource(wellLoc, -1);

        //Deposit resource to headquarter
        int total = Carriers.getTotalResource(rc);
        Carriers.depositResource(rc,ResourceType.ADAMANTIUM, hqLoc);
        Carriers.depositResource(rc,ResourceType.MANA, hqLoc);

        if(rc.canTakeAnchor(hqLoc, Anchor.STANDARD)){
            rc.takeAnchor(hqLoc,Anchor.STANDARD);
            anchorMode = true;
        }
        if(anchorMode){
            rc.setIndicatorString("Building anchor! " + rc.getAnchor());
            if(islandLoc == null) Movement.moveRandom(rc);
            else Movement.moveTowards(rc, islandLoc);
            if(rc.canPlaceAnchor()) rc.placeAnchor();
        } else {
            if (total == 0) {
                if (wellLoc != null) {
                    MapLocation me = rc.getLocation();
                    if (!me.isAdjacentTo(wellLoc)) Movement.moveTowards(rc, wellLoc);
                } else {
                    Movement.moveRandom(rc);
                }
            }
            if (total == GameConstants.CARRIER_CAPACITY) {
                Movement.moveTowards(rc, hqLoc);
            }
        }
    }

    /**
     * Support Functions
     */
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
