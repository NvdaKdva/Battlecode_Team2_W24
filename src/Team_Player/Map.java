package Team_Player;

import battlecode.common.*;

public class Map {
    static final String[] sqType = {
            "Open", "Wall", "Cloud", "HQ", "Island", "Current", };

    public MapSquare[][] arenaMap;

    public class MapSquare {
        /** Attributes of a square */
        private MapLocation cords;
        private String type; //e.g. open, island, wall, HQ, well
        private boolean mapped = false; //has this robot mapped this
        private Direction currDir = Direction.CENTER; //direction of current if any
        private ResourceType wellType = null; //Resource type if a well
        private String FOFid = "";

        /** Functions for a square */
        public MapSquare(int width, int height) {
            cords = new MapLocation(width,height);
        }

        public String setSqType(RobotController rc, MapInfo info) throws GameActionException {
            String type = null;
            MapLocation here = info.getMapLocation();
            if(!info.isPassable()) type = "Wall";
            else if(info.hasCloud()) type = "Cloud";
            else if(!info.getCurrentDirection().name().equals("CENTER")) {
                type = "Current"; currDir = info.getCurrentDirection(); }
            else if(rc.senseIsland(here) != -1) type = "Island";
            else if(rc.senseWell(here) != null) {
                type = "Well"; wellType = rc.senseWell(here).getResourceType();
            }
            else if(rc.canSenseRobotAtLocation(here)) {
                if(rc.senseRobotAtLocation(here).type == RobotType.HEADQUARTERS) {
                    type = "HQ";
                    if (rc.getTeam() == rc.senseRobotAtLocation(here).team) {
                        FOFid = "Friend";
                    } else {
                        FOFid = "Foe";
                    }
                }
            }
            else type = "Open";

            this.type = type;
            setMapped(true);
            return type;
        }

        public boolean isMapped() { return mapped; }
        public void setMapped(boolean mapped) { this.mapped = mapped; }
        public Direction getCurrDir() { return currDir; }
        public String getFOFid() { return FOFid; }
    }

    public Map(int width, int height) {
        arenaMap = new MapSquare[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                arenaMap[j][i] = new MapSquare(j,i);
            }
        }
    }

    static int getVision(RobotController rc, int turnCount, int vision) {
        switch (rc.getType()) {
            case AMPLIFIER:
            case HEADQUARTERS:
                vision = 34;
                break;
        }
        if(turnCount == 1) vision = vision/2;

        return vision;
    }

    static void updateSquares(MapLocation[] view, RobotController rc, MapSquare[][] arenaMap) throws GameActionException {
        //Maps all unmapped visible squares
        for(MapLocation loc : view) {
            //check if already mapped, if mapped skip
            if(!arenaMap[loc.x][loc.y].isMapped()) {
                int x = loc.x, y = loc.y;
                MapInfo info = rc.senseMapInfo(loc);
                arenaMap[x][y].setSqType(rc, info);
            }
        }
    }

    public void updateMap(RobotController rc, int turnCount) throws GameActionException {
        //determine vision squared distance for robot type
        int vision = 20;
        if(rc.getType() != null) { vision = getVision(rc, turnCount, vision);}
        //Gets list of all visible areas on map
        MapLocation[] view = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), vision);
        //Maps all unmapped visible squares
        updateSquares(view, rc, arenaMap);
    }

}

