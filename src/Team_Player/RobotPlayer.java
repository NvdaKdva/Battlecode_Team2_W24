package Team_Player;

import battlecode.common.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function:
 * this is what we'll call once your robot is created!
 */
public strictfp class RobotPlayer {

    /**
     *  Global Variables
     *  Can be used across robots BUT ARE UNIQUE per robot
     **/
    static MapLocation hqLoc;
    static MapLocation wellLoc;
    static MapLocation islandLoc;
    static boolean anchorMode = false;
    static final int MAX_INITIAL_LAUNCHERS = 20;
    static final int MIN_MAINTAIN_LAUNCHERS = 10;
    static int estimatedLauncherCount = 0;
    /* A random number generator.
     * Use this RNG to make some random moves.*seed* the with (6147);
     * this makes sure we get the same sequence of numbers every time this code is run.
     * This is very useful for debugging!
     */
    static final Random rng = new Random(6147);
    /* Use this variable to count the number of turns this robot has been alive.
     * You can use static variables like this to save any information you want.
     * Keep in mind that even though these variables are static,
     * in Battlecode they aren't actually shared between your robots.
     */
    static int turnCount = 0;

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
                    case HEADQUARTERS:  runHeadquarters(rc);    break;
                    case CARRIER:       runCarrier(rc);         break;
                    case LAUNCHER:      runLauncher(rc);        break;
// 'Examplefuncsplayer' doesn't use any of these robot types below. You might want to give them a try!
                    case BOOSTER:
                    case DESTABILIZER:
                    case AMPLIFIER:     runAmplifier(rc);       break;
                }

            } catch (GameActionException e) {
                // Oh-no! It looks like we did something illegal in the Battlecode world. You should
                // handle GameActionExceptions judiciously, in case unexpected events occur in the game
                // world. Remember, uncaught exceptions cause your robot to explode!
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } catch (Exception e) {
                // Oh-no! It looks like our code tried to do something bad. This isn't a
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
     * Run a single turn for a Carrier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runCarrier(RobotController rc) throws GameActionException {
        if(hqLoc == null) hqLoc = Movement.scanHQ(rc);
        if(wellLoc == null) wellLoc = Movement.scanWells(rc);
        if(islandLoc == null) islandLoc = Movement.scanIslands(rc);
        //if(wellsLoc == null) Movement.scanWells(rc);

        //Collect from well if close and inventory not full
        if (wellLoc != null && rc.canCollectResource(wellLoc, -1))
            rc.collectResource(wellLoc, -1);

        //Deposit resource to headquarter
        int total = Carriers.getTotalResource(rc);
        Carriers.depositResource(rc,ResourceType.ADAMANTIUM, hqLoc);
        Carriers.depositResource(rc,ResourceType.MANA, hqLoc);

        if(rc.canTakeAnchor(hqLoc, Anchor.STANDARD)){
            rc.takeAnchor(hqLoc,Anchor.STANDARD);
            anchorMode = true;
        }
        if(anchorMode){
            rc.setIndicatorString("Building anchor! " + rc.getAnchor());
            if(islandLoc == null) Movement.moveRandom(rc);
            else Movement.moveTowards(rc, islandLoc);
            if(rc.canPlaceAnchor()) rc.placeAnchor();
        } else {
            if (total == 0) {
                if (wellLoc != null) {
                    MapLocation me = rc.getLocation();
                    if (!me.isAdjacentTo(wellLoc)) Movement.moveTowards(rc, wellLoc);
                } else {
                    Movement.moveRandom(rc);
                }
            }
            if (total == GameConstants.CARRIER_CAPACITY) {
                Movement.moveTowards(rc, hqLoc);
            }
        }
    }

    /**
     * Run a single turn for a Launcher.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    static void runLauncher(RobotController rc) throws GameActionException {
        RobotInfo target = Launchers.findTargetPriority(rc);
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

}
