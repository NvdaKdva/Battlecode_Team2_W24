package Team_Player;

import battlecode.common.*;
import battlecode.server.GameMaker;
import battlecode.world.GameWorld;
import battlecode.world.InternalRobot;
import battlecode.world.Inventory;
import battlecode.world.TestMapBuilder;
import battlecode.world.control.RobotControlProvider;
import battlecode.world.control.TeamControlProvider;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest {

    @Test
    public void testSetSqType() throws GameActionException {
        String type = null;
        double[] cdMult = {1,1};
        int[][] numActiveEle = {{1,1},{1,1}};
        int[][] turnsLeft = {{1,1},{1,1}};

        MockRobotController mockRobotController = new MockRobotController(RobotType.CARRIER,0,0);
        Map testMap = new Map(20,20);

        //MapInfo(MapLocation loc, bool hasCloud, bool isPassable, double[] cdMult, Direction currdir, int[][] numActiveEle, int[][] turnsLeft
        MapInfo wallTestSq = new MapInfo(new MapLocation(1,0),false, false,
                cdMult, Direction.CENTER, numActiveEle, turnsLeft);
        assert testMap.arenaMap[1][0].setSqType(mockRobotController, wallTestSq) == "Wall";

        double [] cloudCdMult = {2,0};
        MapInfo cloudTestSq = new MapInfo(new MapLocation(2,0),true, true,
                cloudCdMult, Direction.CENTER, numActiveEle, turnsLeft);
        assert testMap.arenaMap[2][2].setSqType(mockRobotController, cloudTestSq) == "Cloud";

        MapInfo currentTestSq = new MapInfo(new MapLocation(3,0),false, true,
                cdMult, Direction.NORTH, numActiveEle, turnsLeft);
        assert testMap.arenaMap[3][0].setSqType(mockRobotController, currentTestSq) == "Current";
        assert testMap.arenaMap[3][0].getCurrDir() == Direction.NORTH;

        mockRobotController.setSenseIsland_Ret_Value(0);
        MapInfo islandTestSq = new MapInfo(new MapLocation(4,0),false, true,
                cdMult, Direction.CENTER, numActiveEle, turnsLeft);
        assert testMap.arenaMap[4][0].setSqType(mockRobotController, islandTestSq) == "Island";
        mockRobotController.setSenseIsland_Ret_Value(-1);

        MapLocation testWell = new MapLocation(0,1);
        Inventory testInv = new Inventory();
        WellInfo testWellInfo = new WellInfo(testWell,ResourceType.ADAMANTIUM, testInv, false);
        mockRobotController.setSenseWell_Ret_Value(testWellInfo);
        MapInfo wellTestSq = new MapInfo(new MapLocation(0,1),false, true,
                cdMult, Direction.CENTER, numActiveEle, turnsLeft);
        assert testMap.arenaMap[0][1].setSqType(mockRobotController, wellTestSq) == "Well";
        mockRobotController.setSenseWell_Ret_Value(null);

        MapLocation testFoeHQ = new MapLocation(0,2);
        RobotInfo testFoeHQInfo = new RobotInfo(100, Team.A, RobotType.HEADQUARTERS, testInv, 1, testFoeHQ);
        MapLocation testFriHQ = new MapLocation(0,3);
        RobotInfo testFriHQInfo = new RobotInfo(200, Team.B, RobotType.HEADQUARTERS, testInv, 1, testFriHQ);
        mockRobotController.setCanSenseRobot_Ret_Value(true);
        mockRobotController.setTeam(Team.B);
        mockRobotController.setSensedRobot(testFoeHQInfo);
        MapInfo hqFoeTestSq = new MapInfo(new MapLocation(0,2),false, true,
                cdMult, Direction.CENTER, numActiveEle, turnsLeft);
        assert testMap.arenaMap[0][2].setSqType(mockRobotController, hqFoeTestSq) == "HQ";
        assert testMap.arenaMap[0][2].getFOFid() == "Foe";
        mockRobotController.setSensedRobot(testFriHQInfo);
        MapInfo hqFriTestSq = new MapInfo(new MapLocation(0,2),false, true,
                cdMult, Direction.CENTER, numActiveEle, turnsLeft);
        assert testMap.arenaMap[0][3].setSqType(mockRobotController, hqFriTestSq) == "HQ";
        assert testMap.arenaMap[0][3].getFOFid() == "Friend";
        mockRobotController.setCanSenseRobot_Ret_Value(false);

        MapInfo openTestSq = new MapInfo(new MapLocation(1,1),false, true,
                cdMult, Direction.CENTER, numActiveEle, turnsLeft);
        assert testMap.arenaMap[1][1].setSqType(mockRobotController, openTestSq) == "Open";
    }

    @Test
    public void testIsMapped() {
        Map testMap = new Map(10,10);
        Map.MapSquare testSq = testMap.new MapSquare(1,1);
        assertFalse(testSq.isMapped());
    }

    @Test
    public void testSetMapped() {
        Map testMap = new Map(10,10);
        Map.MapSquare testSq = testMap.new MapSquare(1,1);
        testSq.setMapped(true);
        assertTrue(testSq.isMapped());
    }

    @Test
    public void testGetVision() {
        Map testMap = new Map(10,10);
        MockRobotController mockCarrier = new MockRobotController(RobotType.CARRIER,0,0);
        assert Map.getVision(mockCarrier, 1, 20) == 10;
        assert Map.getVision(mockCarrier, 2, 20) == 20;

        MockRobotController mockAmp = new MockRobotController(RobotType.AMPLIFIER,0,0);
        assert Map.getVision(mockAmp, 1, 20) == 17;
        assert Map.getVision(mockAmp, 2, 20) == 34;
    }

    @Test
    public void testUpdateSquare() throws GameActionException {
        int x = 10, y = 10;
        double[] cdMult = {1,1};
        int[][] numActiveEle = {{1,1},{1,1}};
        int[][] turnsLeft = {{1,1},{1,1}};
        MapLocation[] testView = new MapLocation[x*y];
        for(int i = 0; i < y; i++) {
            for(int j = 0; j < x; j++) {
                testView[j*10 + i] = new MapLocation(j,i);
            }
        }
        MapInfo[] testInfo = new MapInfo[x*y];
        for(int i = 0; i < y; i++) {
            for(int j = 0; j < x; j++) {
                testInfo[j*10 + i] = new MapInfo(testView[j*10 + i],false,true, cdMult,Direction.CENTER, numActiveEle,turnsLeft);
            }
        }
        Map testMap = new Map(x,y);

        MockRobotController mockRobotController = new MockRobotController();
        mockRobotController.setSensedMapInfo(testInfo);

        assert testMap.arenaMap[5][5].isMapped() == false;

        Map.updateSquares( testView, mockRobotController, testMap.arenaMap);

        assert testMap.arenaMap[5][5].isMapped() == true;

        /*
        TestMapBuilder tmap = new TestMapBuilder("test map",0,0,20,20, 2024);
        tmap.setWall(0,0,true);
        tmap.addHeadquarters(1000, Team.A, new MapLocation(2,2));
        System.out.println("");
        */
    }

}
