package mahathi_player_sandbox;

import battlecode.common.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer {

    /**
     * We will use this variable to count the number of turns this robot has been alive.
     * You can use static variables like this to save any information you want. Keep in mind that even though
     * these variables are static, in Battlecode they aren't actually shared between your robots.
     */
    static int turnCount = 0;

    /**
     * A random number generator.
     * We will use this RNG to make some random moves. The Random class is provided by the java.util.Random
     * import at the top of this file. Here, we *seed* the RNG with a constant number (6147); this makes sure
     * we get the same sequence of numbers every time this code is run. This is very useful for debugging!
     */
    static final Random rng = new Random(6147);
    static MapLocation hqLoc;
    static MapLocation wellLoc;
    static MapLocation wellsLoc;
    static MapLocation islandLoc;
    static boolean anchorMode = false;

    /**
     * Array containing all the possible movement directions.
     */

    static RobotController rc;
    static final int EARLY_GAME_END = 500;
    static final int MID_GAME_END = 1500;
    static final double LAUNCHER_CRITICAL_HEALTH_THRESHOLD = 50.0; // Define the critical health threshold
    static MapLocation hqLocation = null;
    static int estLauncherCount = 0;
    static int estAmplifierCount = 0;
    static int maxAmpLim = 0;

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
    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc The RobotController object. You use it to perform actions from this robot, and to get
     *           information on its current status. Essentially your portal to interacting with the world.
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // Hello world! Standard output is very useful for debugging.
        // Everything you say here will be directly viewable in your terminal when you run a match!
        System.out.println("I'm a " + rc.getType() + " and I just got created! I have health " + rc.getHealth());
        //System.out.println("Launcher health is critical (" + rc.getHealth() + "). Retreating to HQ at " + hqLocation);
        // You can also use indicators to save debug notes in replays.
        rc.setIndicatorString("Hello world!");

        while (true) {
            // This code runs during the entire lifespan of the robot, which is why it is in an infinite
            // loop. If we ever leave this loop and return from run(), the robot dies! At the end of the
            // loop, we call Clock.yield(), signifying that we've done everything we want to do.
            turnCount += 1;  // We have now been alive for one more turn!

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
            try {
                // The same run() function is called for every robot on your team, even if they are
                // different types. Here, we separate the control depending on the RobotType, so we can
                // use different strategies on different robots. If you wish, you are free to rewrite
                // this into a different control structure!
                switch (rc.getType()) {
                    case HEADQUARTERS:
                        runHeadquarters(rc);
                        break;
                    case CARRIER:
                        runCarrier(rc);
                        break;
                    case LAUNCHER:
                        runLauncher(rc);
                        break;
                    case BOOSTER: // Examplefuncsplayer doesn't use any of these robot types below.
                    case DESTABILIZER: // You might want to give them a try!
                    case AMPLIFIER:
                        break;
                }

            } catch (GameActionException e) {
                // Oh no! It looks like we did something illegal in the Battlecode world. You should
                // handle GameActionExceptions judiciously, in case unexpected events occur in the game
                // world. Remember, uncaught exceptions cause your robot to explode!
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } catch (Exception e) {
                // Oh no! It looks like our code tried to do something bad. This isn't a
                // GameActionException, so it's more likely to be a bug in our code.
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } finally {
                // Signify we've done everything we want to do, thereby ending our turn.
                // This will make our code wait until the next turn, and then perform this loop again.
                Clock.yield();
            }
            // End of loop: go back to the top. Clock.yield() has ended, so it's time for another turn!
        }

        // Your code should never reach here (unless it's intentional)! Self-destruction imminent...
    }

    /**
     * Run a single turn for a Headquarters.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runHeadquarters(RobotController rc) throws GameActionException {
        // Pick a direction to build in.
        Direction dir = directions[rng.nextInt(directions.length)];
        MapLocation newLoc = rc.getLocation().add(dir);
        hqLocation = rc.getLocation();
        if (rc.getRoundNum() > 150 && rc.canBuildAnchor(Anchor.STANDARD)) {
            // If we can build an anchor do it!
            rc.buildAnchor(Anchor.STANDARD);
            rc.setIndicatorString("Building anchor! " + rc.getAnchor());
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
    }

    /**
     * Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runCarrier(RobotController rc) throws GameActionException {
        if (rc.getAnchor() != null) {
            // If I have an anchor singularly focus on getting it to the first island I see
            int[] islands = rc.senseNearbyIslands();
            Set<MapLocation> islandLocs = new HashSet<>();
            for (int id : islands) {
                MapLocation[] thisIslandLocs = rc.senseNearbyIslandLocations(id);
                islandLocs.addAll(Arrays.asList(thisIslandLocs));
            }
            if (islandLocs.size() > 0) {
                MapLocation islandLocation = islandLocs.iterator().next();
                rc.setIndicatorString("Moving my anchor towards " + islandLocation);
                while (!rc.getLocation().equals(islandLocation)) {
                    Direction dir = rc.getLocation().directionTo(islandLocation);
                    if (rc.canMove(dir)) {
                        rc.move(dir);
                    }
                }
                if (rc.canPlaceAnchor()) {
                    rc.setIndicatorString("Huzzah, placed anchor!");
                    rc.placeAnchor();
                }
            }
        }
        // Try to gather from squares around us.
        MapLocation me = rc.getLocation();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                MapLocation wellLocation = new MapLocation(me.x + dx, me.y + dy);
                if (rc.canCollectResource(wellLocation, -1)) {
                    if (rng.nextBoolean()) {
                        rc.collectResource(wellLocation, -1);
                        rc.setIndicatorString("Collecting, now have, AD:" +
                                rc.getResourceAmount(ResourceType.ADAMANTIUM) +
                                " MN: " + rc.getResourceAmount(ResourceType.MANA) +
                                " EX: " + rc.getResourceAmount(ResourceType.ELIXIR));
                    }
                }
            }
        }
        // Occasionally try out the carriers attack
        if (rng.nextInt(20) == 1) {
            RobotInfo[] enemyRobots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
            if (enemyRobots.length > 0) {
                if (rc.canAttack(enemyRobots[0].location)) {
                    rc.attack(enemyRobots[0].location);
                }
            }
        }
        // If we can see a well, move towards it
        WellInfo[] wells = rc.senseNearbyWells();
        if (wells.length > 1 && rng.nextInt(3) == 1) {
            WellInfo well_one = wells[1];
            Direction dir = me.directionTo(well_one.getMapLocation());
            if (rc.canMove(dir))
                rc.move(dir);
        }
        // Also try to move randomly.
        Direction dir = directions[rng.nextInt(directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }

    /**
     * Run a single turn for a Launcher.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    //Mahathi's part for launchers:
    // Find Enemy: Locate nearby enemies.
    //Get Enemy Data: Obtain information about these enemies.
    //Prioritize by Type: Prioritize the enemies based on their type.
    //Attack Enemy: Attack the prioritized enemy.
    static void runLauncher(RobotController rc) throws GameActionException {
        RobotInfo target = findTargetPriority(rc);
        if (rc.getHealth() < LAUNCHER_CRITICAL_HEALTH_THRESHOLD && hqLocation != null) {
            System.out.println("Launcher health is critical (" + rc.getHealth() + "). Retreating to HQ at " + hqLocation);
            retreatToHQ(rc);
        }
        if (target != null && rc.canAttack(target.location)) {
            rc.attack(target.location);
            rc.setIndicatorString("Attacking " + target.location);
        } else {
            Direction dir = directions[rng.nextInt(directions.length)];
            if (rc.canMove(dir)) {
                rc.move(dir);
            }
        }
    }

    private static RobotInfo findTargetPriority(RobotController rc) throws GameActionException {
        RobotInfo[] enemies = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam().opponent());
        if (enemies.length == 0) return null;

        RobotInfo prioritizedTarget = null;
        double highestPriority = Double.MAX_VALUE;
        for (RobotInfo enemy : enemies) {
            double priority = calculatePriority(enemy, rc.getLocation(), rc.getRoundNum());
            if (priority < highestPriority) {
                highestPriority = priority;
                prioritizedTarget = enemy;
            }
        }
        return prioritizedTarget;
    }

    private static double calculatePriority(RobotInfo enemy, MapLocation myLocation, int currentRound) {
        double typePriority = getTypePriority(enemy.type, currentRound);
        double healthFactor = 1.0 / (enemy.health + 1);
        double distanceFactor = 1.0 / myLocation.distanceSquaredTo(enemy.location);

        return typePriority * healthFactor * distanceFactor;
    }

    private static double getTypePriority(RobotType type, int currentRound) {
        // Adjust priorities based on the game phase
        if (currentRound <= EARLY_GAME_END) {
            switch (type) {
                case CARRIER:
                    return 3;
                case AMPLIFIER:
                    return 2;
                default:
                    return 10;
            }
        } else if (currentRound <= MID_GAME_END) {
            switch (type) {
                case LAUNCHER:
                    return 1;
                case CARRIER:
                    return 2;
                default:
                    return 10;
            }
        } else {
            switch (type) {
                case LAUNCHER:
                    return 1;
                case AMPLIFIER:
                    return 2;
                default:
                    return 10;
            }
        }
    }
    static void retreatToHQ(RobotController rc) throws GameActionException {
        // Logic to move towards the HQ location for repairs or safety
        Direction toHQ = rc.getLocation().directionTo(hqLocation);
        if (rc.canMove(toHQ)) {
            rc.move(toHQ);
            rc.setIndicatorString("Retreating to HQ");
            rc.setIndicatorDot(hqLocation, 255, 0, 0);
        }
    }
}


/*
    static void runLauncher(RobotController rc) throws GameActionException {
        // Try to attack someone
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        if (enemies.length >= 0) {
            // MapLocation toAttack = enemies[0].location;
            MapLocation toAttack = rc.getLocation().add(Direction.EAST);

            if (rc.canAttack(toAttack)) {
                rc.setIndicatorString("Attacking");
                rc.attack(toAttack);
            }
        }

        // Also try to move randomly.
        Direction dir = directions[rng.nextInt(directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }
*/



