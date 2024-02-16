package Team_Player;

import battlecode.common.*;

import java.util.Random;

public class Launchers {
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

    public static double calculatePriority(RobotInfo enemy, MapLocation myLocation) {
        double typePriority = getTypePriority(enemy.type);
        double healthFactor = 1.0 / (enemy.health + 1); // Lower health = higher priority
        double distanceFactor = 1.0 / myLocation.distanceSquaredTo(enemy.location); // Closer = higher priority

        return typePriority * healthFactor * distanceFactor;
    }

    public static RobotInfo findTargetPriority(RobotController rc) throws GameActionException {
        RobotInfo[] enemies = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam().opponent());
        if (enemies.length == 0) return null;

        RobotInfo prioritizedTarget = null;
        double highestPriority = Double.MAX_VALUE;
        for (RobotInfo enemy : enemies) {
            double priority = Launchers.calculatePriority(enemy, rc.getLocation());
            if (priority < highestPriority) {
                highestPriority = priority;
                prioritizedTarget = enemy;
            }
        }
        return prioritizedTarget;
    }

    public static int getTypePriority(RobotType type) {
        switch (type) {
            case CARRIER: return 1;
            case AMPLIFIER: return 2;
            case LAUNCHER: return 3;
            default: return 10;
        }
    }
}
