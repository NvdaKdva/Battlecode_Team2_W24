package Team_Player;

import battlecode.common.*;

import java.util.Arrays;
import java.util.Random;

    public class Shared {

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
            else moveNextBest(rc, dir);
        }

        /** If robot can not move in the best direction tries the next in the direction array,
         *  then the one before in direction array before moving in random direction. */
        static void moveNextBest(RobotController rc, Direction dir1) throws GameActionException {
            Direction dir2 = directions[(Arrays.binarySearch(directions,dir1) + 1) % directions.length];
            Direction dir3 = directions[(Arrays.binarySearch(directions,dir1) + directions.length - 1) % directions.length];
            if(rc.canMove(dir2)) rc.move(dir2);
            else if(rc.canMove(dir3)) rc.move(dir3);
            else moveRandom(rc);
        }

        static MapLocation scanHQ(RobotController rc) throws GameActionException {
            RobotInfo[] robots = rc.senseNearbyRobots(3);
            MapLocation hqloc = null;
            for(RobotInfo robot : robots){
                if(robot.getTeam() == rc.getTeam() && robot.getType() == RobotType.HEADQUARTERS){
                    hqloc = robot.getLocation();
                    break;
                }
            }
            return hqloc;
        }

        static MapLocation scanWells(RobotController rc) {
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

        static MapLocation scanManaWell(RobotController rc) {
            WellInfo[] wells = rc.senseNearbyWells();
            MapLocation maWeLo = null;
            for (WellInfo well : wells) {
                if (well.getResourceType() == ResourceType.MANA) {
                    maWeLo = well.getMapLocation();
                    break;
                }
            }
            return maWeLo;
        }
    }
