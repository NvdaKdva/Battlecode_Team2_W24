package Team_Player;

import battlecode.common.*;

public class Carrier {
    static MapLocation hqLoc;
    static MapLocation wellLoc;
    static MapLocation islandLoc;
    static MapLocation manaWellLoc;
    static boolean anchorMode = false;
    static boolean elixirWellMade = false;

    /** Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn. */
    static void runCarrier(RobotController rc) throws GameActionException {
        if(hqLoc == null)       hqLoc = Shared.scanHQ(rc);
        if(wellLoc == null)     wellLoc = Shared.scanWells(rc);
        if(islandLoc == null)   islandLoc = Shared.scanIslands(rc);
        if(manaWellLoc == null) manaWellLoc = Shared.scanManaWell(rc);

//Collect from well if close and inventory not full
        if (wellLoc != null && rc.canCollectResource(wellLoc, -1)) { rc.collectResource(wellLoc, -1); }

//After round X start making an Elixir well from a mana well
        if(manaWellLoc != null) {
            if(rc.canSenseLocation(manaWellLoc)) {
                if (rc.getRoundNum() > 0 &&
                        !elixirWellMade &&
                        rc.getResourceAmount(ResourceType.ADAMANTIUM) >= 30 &&
                        rc.readSharedArray(0) != 1 &&
                        rc.senseWell(manaWellLoc).getResourceType() != ResourceType.ELIXIR
                ) {
                    rc.setIndicatorString("Attempting to deposit Ad in Mn well");
                    while (!manaWellLoc.isAdjacentTo(rc.getLocation())) {
                        Shared.moveTowards(rc, manaWellLoc);
                    }
                    if(!(rc.getActionCooldownTurns() > 0)) {
                        rc.transferResource(manaWellLoc, ResourceType.ADAMANTIUM, rc.getResourceAmount(ResourceType.ADAMANTIUM));
                        if (rc.senseWell(manaWellLoc).getResourceType() == ResourceType.ELIXIR) {
                            elixirWellMade = true;
                            if (rc.canWriteSharedArray(0, 1)) rc.writeSharedArray(0, 1);
                        }
                    }
                }
            }
        }

        //Deposit resource to headquarter
        int total = getTotalResource(rc);
        depositResource(rc,ResourceType.ADAMANTIUM);
        depositResource(rc,ResourceType.MANA);
        depositResource(rc,ResourceType.ELIXIR);

        if(rc.canTakeAnchor(hqLoc, Anchor.STANDARD)){
            rc.takeAnchor(hqLoc, Anchor.STANDARD);
            anchorMode = true;
        }
        if(anchorMode) {
            rc.setIndicatorString("Trying to place an Anchor");
            if(islandLoc == null) Shared.moveRandom(rc);
            else Shared.moveTowards(rc, islandLoc);
            if(rc.canPlaceAnchor()) {
                rc.placeAnchor();
                rc.setIndicatorString("Anchor placed!");
                anchorMode = false; //they will leave islands after placing.
            }
        } else {
            if (total == 0) {
                if (wellLoc != null) {
                    MapLocation me = rc.getLocation();
                    if (!me.isAdjacentTo(wellLoc)) Shared.moveTowards(rc, wellLoc);
                } else {
                    Shared.moveRandom(rc);
                }
            }
            if (total == GameConstants.CARRIER_CAPACITY) {
                Shared.moveTowards(rc, hqLoc);
            }
        }
    }

    /** Support Functions */
    static void depositResource(RobotController rc, ResourceType type) throws GameActionException {
        int amount = rc.getResourceAmount(type);
        if (amount > 0){
            if(rc.canTransferResource(hqLoc, type, amount)){
                rc.transferResource(hqLoc, type, amount);
            }
        }
    }

    static int getTotalResource(RobotController rc){
        return rc.getResourceAmount(ResourceType.ADAMANTIUM)
                + rc.getResourceAmount(ResourceType.MANA)
                + rc.getResourceAmount(ResourceType.ELIXIR);
    }
}