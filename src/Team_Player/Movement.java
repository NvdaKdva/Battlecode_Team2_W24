package Team_Player;

import battlecode.common.*;

import java.util.Random;

public class Movement {

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

    // SUPPORTING FUNCTIONS
    static void moveRandom(RobotController rc) throws GameActionException {
        Direction dir = directions[rng.nextInt(directions.length)];
        if (rc.canMove(dir)) rc.move(dir);
    }

    static void moveTowards(RobotController rc, MapLocation loc) throws GameActionException {
        Direction dir = rc.getLocation().directionTo(loc);
        if(rc.canMove(dir)) rc.move(dir);
        else moveRandom(rc);
    }

    static MapLocation scanHQ(RobotController rc) throws GameActionException{
        RobotInfo[] robots = rc.senseNearbyRobots();
        MapLocation hqloc = null;
        for(RobotInfo robot : robots){
            if(robot.getTeam() == rc.getTeam() && robot.getType() == RobotType.HEADQUARTERS){
                hqloc = robot.getLocation();
                break;
            }
        }
        return hqloc;
    }

    static MapLocation scanWells(RobotController rc) throws GameActionException{
        WellInfo[] wells = rc.senseNearbyWells();
        MapLocation wellloc = null;
        if (wells.length > 0) {
            wellloc = wells[0].getMapLocation();
        }
        return wellloc;
    }

    static MapLocation scanIslands(RobotController rc) throws GameActionException{
        int[] ids = rc.senseNearbyIslands();
        MapLocation islandloc = null;
        for(int id : ids){
            if(rc.senseTeamOccupyingIsland(id) == Team.NEUTRAL){
                MapLocation[] locs = rc.senseNearbyIslandLocations(id);
                if(locs.length > 0){
                    islandloc = locs[0];
                    break;
                }
            }
        }
        return islandloc;
    }
}
