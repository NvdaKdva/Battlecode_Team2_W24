package Team_Player;

import battlecode.common.*;

public class Destabilizer {

    public static void runDestabilizer(RobotController rc) {
        while (true) {
            try {
                attackOrMove(rc);
            } catch (Exception e) {
                System.out.println("Destabilizer Exception: " + e.getMessage());
            }
            Clock.yield();
        }
    }

    private static void attackOrMove(RobotController rc) throws GameActionException {
        MapLocation myLocation = rc.getLocation();
        RobotInfo[] enemyRobots = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam().opponent());
        if (enemyRobots.length > 0) {
            RobotInfo closestEnemy = enemyRobots[0]; // Assume the first enemy is the closest for this example
            Direction toEnemy = myLocation.directionTo(closestEnemy.location);
            if (rc.canAttack(closestEnemy.location) && rc.isActionReady()) {
                rc.attack(closestEnemy.location);
            } else if (rc.canMove(toEnemy)) {
                rc.move(toEnemy);
            }
        } else {
            Shared.moveRandom(rc);
        }
    }
}