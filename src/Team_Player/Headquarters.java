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
    static final int MAX_INITIAL_LAUNCHERS = 20;
    static final int MIN_MAINTAIN_LAUNCHERS = 10;
    static int estimatedLauncherCount = 0;

    /**
     * Run a single turn for a Headquarters.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runHeadquarters(RobotController rc) throws GameActionException {
        // Pick a direction to build in.
        Direction dir = directions[rng.nextInt(directions.length)];
        MapLocation newLoc = rc.getLocation().add(dir);
        if (rc.canBuildAnchor(Anchor.STANDARD)) {
            // If we can build an anchor do it!
            rc.buildAnchor(Anchor.STANDARD);
            rc.setIndicatorString("Building a Standard anchor! ");
        }
        if (rng.nextBoolean()) {
            // Let's try to build a carrier.
            rc.setIndicatorString("Trying to build a carrier");
            if (rc.canBuildRobot(RobotType.CARRIER, newLoc)) {
                rc.buildRobot(RobotType.CARRIER, newLoc);
            }
        } else {
            // Let's try to build a launcher.
            rc.setIndicatorString("Trying to build a launcher");
            if (rc.canBuildRobot(RobotType.LAUNCHER, newLoc)) {
                rc.buildRobot(RobotType.LAUNCHER, newLoc);
            }
        }
        if (estimatedLauncherCount < MAX_INITIAL_LAUNCHERS || (rc.getRoundNum() % 10 == 0 && estimatedLauncherCount <= MAX_INITIAL_LAUNCHERS - MIN_MAINTAIN_LAUNCHERS)) {
            // Additional debugging print statement before attempting to spawn
            System.out.println("HQ: Attempting to spawn Launcher, total attempts: " + estimatedLauncherCount);
            Direction direct = directions[rng.nextInt(directions.length)];
            MapLocation newLocation = rc.getLocation().add(direct);
            if (rc.canBuildRobot(RobotType.LAUNCHER, newLocation)) {
                rc.buildRobot(RobotType.LAUNCHER, newLocation);
                estimatedLauncherCount++; // Note: This count will not decrease when launchers are destroyed.
                System.out.println("HQ: Spawning Launcher, new estimated count: " + estimatedLauncherCount);
            }
        }
    }

    /**
     * Support Functions
     */
}
