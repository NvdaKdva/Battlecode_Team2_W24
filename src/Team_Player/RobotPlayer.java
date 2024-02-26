package Team_Player;

import battlecode.common.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static battlecode.common.RobotType.HEADQUARTERS;

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
    static final int MAX_INITIAL_LAUNCHERS = 20; //todo consider removal
    static final int MIN_MAINTAIN_LAUNCHERS = 10; //todo consider removal
    static int atkEnemyHQ = 0;
    static int maxAtkEnemyHQ = 100;
    static int estLauncherCount = 0;
    static int estAmplifierCount = 0;
    static int maxAmpLim = 0;


    /**
     * A random number generator.
     * We will use this RNG to make some random moves. The Random class is provided by the java.util.Random
     * import at the top of this file. Here, we *seed* the RNG with a constant number (6147); this makes sure
     * we get the same sequence of numbers every time this code is run. This is very useful for debugging!
     */

    /**
     *  Global Variables
     *  largely used for storing locations can be used across robots
     **/
    static final Random rng = new Random(6147);
    static MapLocation hqLoc;
    static MapLocation wellLoc;
    static MapLocation wellsLoc;
    static MapLocation islandLoc;
    static boolean anchorMode = false;

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

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc  The RobotController object. You use it to perform actions from this robot, and to get
     *            information on its current status. Essentially your portal to interacting with the world.
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // Hello world! Standard output is very useful for debugging.
        // Everything you say here will be directly viewable in your terminal when you run a match!
        System.out.println("I'm a " + rc.getType() + " and I just got created! I have health " + rc.getHealth());

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
                    case HEADQUARTERS:     runHeadquarters(rc);  break;
                    case CARRIER:      runCarrier(rc);   break;
                    case LAUNCHER: runLauncher(rc); break;
                    case BOOSTER: // Examplefuncsplayer doesn't use any of these robot types below.
                    case DESTABILIZER: // You might want to give them a try!
                    case AMPLIFIER:    runAmplifier(rc);   break;
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

        //Only makes Standard Anchors and if after round 150
        if (rc.getRoundNum() > 150 && rc.canBuildAnchor(Anchor.STANDARD)) {
            rc.buildAnchor(Anchor.STANDARD);
            rc.setIndicatorString("Building Standard anchor!");
        }
        //Makes Carrier right away and every x rounds (x=3)
        // provided not making a launcher every y rounds (y=5)
        if (rc.getRoundNum() % 20 == 0) {
        //if (rc.getRoundNum() == 0 || rc.getRoundNum() % 5 != 0 && rc.getRoundNum() % 3 == 0) {
            rc.setIndicatorString("Trying to build a carrier");
            if (rc.canBuildRobot(RobotType.CARRIER, newLoc)) {
                rc.buildRobot(RobotType.CARRIER, newLoc);
            }
        }
/* old generation line, kept for reference
 * if (estimatedLauncherCount < MAX_INITIAL_LAUNCHERS || (rc.getRoundNum() % 10 == 0 && estimatedLauncherCount <= MAX_INITIAL_LAUNCHERS - MIN_MAINTAIN_LAUNCHERS)) {
 *
 * atkEnemyHQ < maxAtkEnemyHQ &&
 */
        //Makes Launcher every x rounds (x=5)
        if (rc.getRoundNum() % 7 == 0) {
            // Additional debugging print statement before attempting to spawn
            rc.setIndicatorString("Trying to build a launcher, " + estLauncherCount + " build so far.");
            if (rc.canBuildRobot(RobotType.LAUNCHER, newLoc)) {
                rc.buildRobot(RobotType.LAUNCHER, newLoc);
                estLauncherCount++; // Note: This count will not decrease when launchers are destroyed.
            }
        }
        //Determines ideal number of launchers (grid mult / 360 - 1)
        if(turnCount == 0) { maxAmpLim = rc.getMapHeight() * rc.getMapWidth() / 360 - 1; }
        //Makes Amplifiers every 5 rounds after 50 rnds up to max Amp limit
        if (rc.getRoundNum() % 75 == 0) {
//        if (rc.getRoundNum() > 50 && rc.getRoundNum() % 5 == 0 && estAmplifierCount < maxAmpLim) {
            rc.setIndicatorString("Trying to build an amplifier");
            if (rc.canBuildRobot(RobotType.AMPLIFIER, newLoc)) {
                rc.buildRobot(RobotType.AMPLIFIER, newLoc);
                estAmplifierCount++;// Note: This count will not decrease when amplifiers are destroyed.
            }
        }
    }

    static void runAmplifier(RobotController rc) throws GameActionException {
        // Scan for critical locations
        scanIslands(rc);
        scanHQ(rc);
        scanWells(rc);

        // Move towards island
        if (islandLoc != null) {
            moveTowards(rc, islandLoc);
        }
        // Scan for nearby amplifiers
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        for (RobotInfo robot : nearbyRobots) {
            if (robot.getType() == RobotType.AMPLIFIER) {
                // Move towards the well if found nearby
                if (wellLoc != null) {
                    moveTowards(rc, wellLoc);
                }
                // Move towards HQ if another amplifier is found near the well
                if (hqLoc != null) {
                    moveTowards(rc, hqLoc);
                }
            }
        }
    }

    /**
     * Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    //carriers strategy for sprint 1:
    //Get & Place and Anchor
    //Get Resource Data: Obtain information about these enemies.
    //Gather & Deposit Resources:
    //Prioritize by Goal: Claim Islands before gathering
    //Prioritize by Type: Prioritize the lowest resources first.

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

    static void scanHQ(RobotController rc) throws GameActionException{
        RobotInfo[] robots = rc.senseNearbyRobots();
        for(RobotInfo robot : robots){
            if(robot.getTeam() == rc.getTeam() && robot.getType() == HEADQUARTERS){
                hqLoc = robot.getLocation();
                break;
            }
        }
    }

    static void scanWells(RobotController rc) throws GameActionException{
        WellInfo[] wells = rc.senseNearbyWells();
        if (wells.length > 0) {
            wellsLoc = wells[0].getMapLocation();
        }
    }

    static void scanIslands(RobotController rc) throws GameActionException{
        int[] ids = rc.senseNearbyIslands();
        for(int id : ids){
            if(rc.senseTeamOccupyingIsland(id) == Team.NEUTRAL){
                MapLocation[] locs = rc.senseNearbyIslandLocations(id);
                if(locs.length > 0){
                    islandLoc = locs[0];
                    break;
                }
            }
        }
    }

    static void depositResource(RobotController rc, ResourceType type) throws GameActionException {
        int amount = rc.getResourceAmount(type);
        if (amount > 0){
            if(rc.canTransferResource(hqLoc, type, amount)){
                rc.transferResource(hqLoc, type, amount);
            }
        }
    }

    static int getTotalResource(RobotController rc){
        return rc.getResourceAmount(ResourceType.ADAMANTIUM) + rc.getResourceAmount(ResourceType.MANA);
    }

    //CARRIER ALGO
    static void runCarrier(RobotController rc) throws GameActionException {
        if(hqLoc == null) scanHQ(rc);
        if(wellLoc == null) scanWells(rc);
        if(islandLoc == null) scanIslands(rc);
        if(wellsLoc == null) scanWells(rc);

        //Collect from well if close and inventory not full
        if (wellsLoc != null && rc.canCollectResource(wellsLoc, -1))
            rc.collectResource(wellsLoc, -1);

        //Deposit resource to headquarter
        int total = getTotalResource(rc);
        if (total < GameConstants.CARRIER_CAPACITY && wellsLoc != null && rc.getLocation().distanceSquaredTo(wellsLoc) <= RobotType.CARRIER.actionRadiusSquared) {
            rc.collectResource(wellsLoc, -1);
        } else if (total >= GameConstants.CARRIER_CAPACITY || rc.getRoundNum() % 100 == 0) { // Deposit or strategic return
            moveTowards(rc, hqLoc);
            if (rc.getLocation().isAdjacentTo(hqLoc)) {
                depositResource(rc, ResourceType.ADAMANTIUM);
                depositResource(rc, ResourceType.MANA);
            }
        }
        //TODO Don't auto deposit, only deposit if full
        depositResource(rc,ResourceType.ADAMANTIUM);
        depositResource(rc,ResourceType.MANA);

        if(rc.canTakeAnchor(hqLoc, Anchor.STANDARD)){
            rc.takeAnchor(hqLoc,Anchor.STANDARD);
            anchorMode = true;
        }
        if(anchorMode){
            rc.setIndicatorString("Building anchor! " + rc.getAnchor());
            if(islandLoc == null) RobotPlayer.moveRandom(rc);
            else RobotPlayer.moveTowards(rc, islandLoc);
            if(rc.canPlaceAnchor()) rc.placeAnchor();
        } else {
            if (total == 0) {
                if (wellLoc != null) {
                    MapLocation me = rc.getLocation();
                    if (!me.isAdjacentTo(wellLoc)) RobotPlayer.moveTowards(rc, wellLoc);
                } else {
                    RobotPlayer.moveRandom(rc);
                }
            }
            if (total == GameConstants.CARRIER_CAPACITY) {
                RobotPlayer.moveTowards(rc, hqLoc);
            }
        }
    }

    /**
     * Run a single turn for a Launcher.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    //launchers strategy for sprint 1:
    //Find Enemy: Locate nearby enemies.
    //Get Enemy Data: Obtain information about these enemies.
    //Prioritize by Type: Prioritize the enemies based on their type.
    //Attack Enemy: Attack the prioritized enemy.

    static void runLauncher(RobotController rc) throws GameActionException {
        RobotInfo target = findTargetPriority(rc);
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

    public static RobotInfo findTargetPriority(RobotController rc) throws GameActionException {
        RobotInfo[] enemies = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam().opponent());
        if (enemies.length == 0) return null;

        RobotInfo prioritizedTarget = null;
        double highestPriority = Double.MAX_VALUE;
        for (RobotInfo enemy : enemies) {
            double priority = calculatePriority(enemy, rc.getLocation());
            if (priority < highestPriority) {
                highestPriority = priority;
                prioritizedTarget = enemy;
            }
        }
        return prioritizedTarget;
    }

    public static double calculatePriority(RobotInfo enemy, MapLocation myLocation) {
        double typePriority = getTypePriority(enemy.type);
        double healthFactor = 1.0 / (enemy.health + 1); // Lower health = higher priority
        double distanceFactor = 1.0 / myLocation.distanceSquaredTo(enemy.location); // Closer = higher priority

        return typePriority * healthFactor * distanceFactor;
    }

    static int getTypePriority(RobotType type) {
        switch (type) {
            case CARRIER: return 1;
            case AMPLIFIER: return 2;
            case LAUNCHER: return 3;
            default: return 10;
        }
    }
}
