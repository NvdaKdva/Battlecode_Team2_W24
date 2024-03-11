package Team_Player;

import battlecode.common.*;

public class Amplifier {
    static MapLocation hqLoc;
    static MapLocation wellLoc;
    static MapLocation islandLoc;

    /** Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn. */
    static void runAmplifier(RobotController rc, int turnCount, Map myMap) throws GameActionException {
        myMap.updateMap(rc, turnCount);

        // Scan for critical locations
        Shared.scanIslands(rc);
        Shared.scanHQ(rc);
        Shared.scanWells(rc);
        //Why are we sending them to clog up islands, wells and the HQ.
        //Why do we never use them to communicate
        //Why are we building more than ~20?

        // Move towards island
        if (islandLoc != null) {
            if(rc.getLocation().directionTo(islandLoc) == Direction.CENTER) {
                rc.setIndicatorString("Trying to move to where I am, I'm a bad robot. "); Shared.moveRandom(rc);}
            else
                Shared.moveTowards(rc, islandLoc);
        }
        // Scan for nearby amplifiers
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        for (RobotInfo robot : nearbyRobots) {
            if (robot.getType() == RobotType.AMPLIFIER) {
                // Move towards the well if found nearby
                if (wellLoc != null) {
                    if(rc.getLocation().directionTo(wellLoc) == Direction.CENTER) {
                        rc.setIndicatorString("Trying to move to where I am, I'm a bad robot. ");
                        Shared.moveRandom(rc);
                    } else { Shared.moveTowards(rc, wellLoc);}
                }
                // Move towards HQ if another amplifier is found near the well
                if (hqLoc != null) {
                    if(rc.getLocation().directionTo(hqLoc) == Direction.CENTER) {
                        rc.setIndicatorString("Trying to move to where I am, I'm a bad robot. ");
                        Shared.moveRandom(rc);
                    } else { Shared.moveTowards(rc, hqLoc);}
                }
            }
        }
    }

    ///** Support Functions */
}