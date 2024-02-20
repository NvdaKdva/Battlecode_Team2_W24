package Team_Player;

import battlecode.common.*;

public class Map {
    static final String[] sqType = {
            "Open", "Wall", "Cloud", "HQ", "Island", "Current",
    };

    public MapSquare[][] arenaMap;

    private class MapSquare {
        /** Attributes of a square */
        private int[] sqCords;  //(X,Y) cord of square
        private String type; //e.g. open, island, wall, HQ, well
        private boolean mapped = false; //has this robot mapped this
        private Direction currDir = null; //direction of current if any
        private ResourceType wellType = null; //Resource type if a well

        /** Functions for a square */
        public MapSquare(int height, int width) {
            sqCords = new int[2];
            sqCords[0] = height;
            sqCords[1] = width;
        }

        public void setSqType(RobotController rc, MapInfo info) throws GameActionException {
            String type = null;
            MapLocation here = info.getMapLocation();
            if(!info.isPassable()) type = "Wall";
            else if(info.hasCloud()) type = "Cloud";
            else if(!info.getCurrentDirection().equals("CENTER")) {
                type = "Current"; currDir = info.getCurrentDirection(); }
            else if(rc.senseIsland(here) != -1) type = "Island";
            else if(rc.senseWell(here) != null) {
                type = "Well"; wellType = rc.senseWell(here).getResourceType();
            }
            else if(rc.canSenseRobotAtLocation(here)) {
                if(rc.senseRobotAtLocation(here).type == RobotType.HEADQUARTERS) type = "HQ";
            }
            else type = "Open";

            this.type = type;
            this.setMapped(true);
        }

        public boolean isMapped() { return mapped; }
        public void setMapped(boolean mapped) { this.mapped = mapped; }

    }


    public void createMap(int width, int height) {
        this.arenaMap = new MapSquare[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.arenaMap[i][j] = new MapSquare(i,j);
            }
        }
    }

    public void updateMap(RobotController rc) throws GameActionException {
        //determine vision squared distance for robot type
        int vision = 0; //make default 20?
        switch(rc.getType()) {
            case AMPLIFIER:
            case HEADQUARTERS: vision = 34; break;
            case BOOSTER:
            case CARRIER:
            case LAUNCHER:
            case DESTABILIZER: vision = 20; break;
        }
        //Gets list of all visible areas on map
        MapLocation[] view = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), vision);

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

}


