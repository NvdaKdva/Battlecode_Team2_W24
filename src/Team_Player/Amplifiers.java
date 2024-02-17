package Team_Player;

import battlecode.common.*;

import java.util.Random;

public class Amplifiers {
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

    /**
     * Run a single turn for an Amplifier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runAmplifier(RobotController rc) throws GameActionException {
        // Scan for critical locations
        islandLoc = Movement.scanIslands(rc);
        hqLoc = Movement.scanHQ(rc);
        Movement.scanWells(rc);

        // Move towards island
        if (islandLoc != null) {
            Movement.moveTowards(rc, islandLoc);
        }
        // Scan for nearby amplifiers
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        for (RobotInfo robot : nearbyRobots) {
            if (robot.getType() == RobotType.AMPLIFIER) {
                // Move towards the well if found nearby
                if (wellLoc != null) {
                    Movement.moveTowards(rc, wellLoc);
                }
                // Move towards HQ if another amplifier is found near the well
                if (hqLoc != null) {
                    Movement.moveTowards(rc, hqLoc);
                }
            }
        }
    }

    /**
     * Support Functions
     */
}
