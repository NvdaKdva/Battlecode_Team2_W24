package Team_Player;

import battlecode.common.*;

import java.util.Random;

public class Headquarters {
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
    static int estLauncherCount = 0;
    static int estAmplifierCount = 0;
    static int maxAmpLim = 0;

    /** Run a single turn for a Headquarters.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn. */
    static void runHeadquarters(RobotController rc, int turnCount, Map myMap) throws GameActionException {
        if(turnCount <= 3) myMap.updateMap(rc, turnCount);

        // Pick a direction to build in.
        Direction dir = directions[rng.nextInt(directions.length)];
        MapLocation newLoc = rc.getLocation().add(dir);

        //Only makes Standard Anchors and if after round 150
        if (rc.getRoundNum() > 150 && rc.canBuildAnchor(Anchor.STANDARD)) {
            rc.buildAnchor(Anchor.STANDARD);
            rc.setIndicatorString("Building Standard anchor!");
        }

        if(rc.getRoundNum() % 10 == 0 && rc.readSharedArray(1) < 18) {
            rc.setIndicatorString("Trying to build a booster");
            if (rc.canBuildRobot(RobotType.BOOSTER, newLoc)) {
                rc.buildRobot(RobotType.BOOSTER, newLoc);
                rc.writeSharedArray(1,rc.readSharedArray(1)+1);
            }
        }

        //Determines ideal number of launchers
        if(turnCount == 0) { maxAmpLim = rc.getMapHeight()/5 * rc.getMapWidth()/5 * 2; }
        if (rc.getRoundNum() % 75 == 0) {
            //if (rc.getRoundNum() > 50 && rc.getRoundNum() % 5 == 0 && estAmplifierCount < maxAmpLim) {
            rc.setIndicatorString("Trying to build an amplifier");
            if (rc.canBuildRobot(RobotType.AMPLIFIER, newLoc)) {
                rc.buildRobot(RobotType.AMPLIFIER, newLoc);
                estAmplifierCount++;// Note: This count will not decrease when amplifiers are destroyed.
            }
        }
        if (rc.getRoundNum() % 20 == 0) {
            //if (rc.getRoundNum() == 0 || rc.getRoundNum() % 5 != 0 && rc.getRoundNum() % 3 == 0) {
            rc.setIndicatorString("Trying to build a carrier");
            if (rc.canBuildRobot(RobotType.CARRIER, newLoc)) {
                rc.buildRobot(RobotType.CARRIER, newLoc);
            }
        }
        if (rc.getRoundNum() % 7 == 0) {
            // Additional debugging print statement before attempting to spawn
            rc.setIndicatorString("Trying to build a launcher, " + estLauncherCount + " build so far.");
            if (rc.canBuildRobot(RobotType.LAUNCHER, newLoc)) {
                rc.buildRobot(RobotType.LAUNCHER, newLoc);
                estLauncherCount++; // Note: This count will not decrease when launchers are destroyed.
            }
        }
    }

    ///** Support Functions */
}
