package Team_Player;

import battlecode.common.*;
import battlecode.world.Inventory;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class LauncherTest {


    @Test
    public void testCalculatePriority() {
        MapLocation myLocation = new MapLocation(5, 5);
        RobotInfo enemyCarrier = new RobotInfo(1, Team.A, RobotType.CARRIER, new Inventory(), 100, new MapLocation(0, 0));
        RobotInfo enemyAmplifier = new RobotInfo(2, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(5, 5));
        RobotInfo enemyLauncher = new RobotInfo(3, Team.A, RobotType.LAUNCHER, new Inventory(), 75, new MapLocation(15, 15));

        double priorityCarrier = Launcher.calculatePriority(enemyCarrier, myLocation);
        double priorityAmplifier = Launcher.calculatePriority(enemyAmplifier, myLocation);
        double priorityLauncher = Launcher.calculatePriority(enemyLauncher, myLocation);

        // Carrier has the lowest priority type
        assertTrue(priorityCarrier < priorityAmplifier);

        // Health factor and distance factor are inversely proportional to the priority
        assertTrue(priorityAmplifier > priorityLauncher);
    }

    @Test
    public void testGetTypePriority() {
        assertEquals(1, Launcher.getTypePriority(RobotType.CARRIER));
        assertEquals(2, Launcher.getTypePriority(RobotType.AMPLIFIER));
        assertEquals(3, Launcher.getTypePriority(RobotType.LAUNCHER));
    }


    @Test
    public void testGetPriorityEnemy() {
        MapLocation myLocation = new MapLocation(5, 5);
        RobotInfo enemyCarrier = new RobotInfo(1, Team.A, RobotType.CARRIER, new Inventory(), 100, new MapLocation(0, 0));
        RobotInfo enemyAmplifier = new RobotInfo(2, Team.A, RobotType.AMPLIFIER, new Inventory(), 50, new MapLocation(5, 5));
        RobotInfo enemyLauncher = new RobotInfo(3, Team.A, RobotType.LAUNCHER, new Inventory(), 75, new MapLocation(15, 15));

        double priorityCarrier = Launcher.getTypePriority(RobotType.CARRIER);
        double priorityAmplifier = Launcher.getTypePriority(RobotType.AMPLIFIER);
        double priorityLauncher = Launcher.getTypePriority(RobotType.LAUNCHER);

        // Carrier has the lowest priority type
        assertTrue(priorityCarrier < priorityAmplifier);

        // Health factor and distance factor are inversely proportional to the priority
        assertFalse(priorityAmplifier > priorityLauncher);
    }



}