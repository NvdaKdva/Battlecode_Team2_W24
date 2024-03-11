package Team_Player;

import battlecode.common.*;

public  class MockRobotController implements RobotController {

    public RobotType robotType;
    public MapLocation robotLocation;

    public MapLocation moveTowardsLocation;
    public MapLocation wellLoc;
    public MapLocation hqLoc;
    public MapLocation islandLoc;

    public int adamantium;
    public int mana;
    public int elixar;
    public int[] nearByIsland;
    public Team team;
    public MapLocation randomLocation = new MapLocation(0, 0);
    public boolean moveCalled = false;
    public boolean canMoveResult = true;
    public boolean canAttack = true;
    public String indicatorString = null;
    public RobotInfo[] sensedRobots;
    public boolean hasMoved;
    public int roundNum;
    public boolean canBuildAnchor;
    public boolean canBuildRobot;
    public MapLocation[] resourceLocations;
    public int[][] nearbyIslands;
    public Team[] islandOccupyingTeam;
    public MapLocation[][] islandLocations;
    public int moveTowardsCalls = 0;
    public int mapWidth;
    public int mapHeight;
    public int[] sharedArray;
    public MapLocation lastMove;
    public int senseIsland_Ret_Value = 0;
    public WellInfo senseWell_Ret_Value = null;
    public boolean canSenseRobot_Ret_Value = false;
    public RobotInfo sensedRobotInfo = null;
    public MapInfo[] sensedMapInfo = null;
    public MapLocation attackedLocation;
    private int turnCount;
    public boolean buildAnchorCalled;
    public MapLocation lastMoveTarget;

    public MockRobotController(MapLocation islandLoc, MapLocation hqLoc, MapLocation wellLoc, RobotInfo[] nearbyRobots) {
        this.islandLoc = islandLoc;
        this.hqLoc = hqLoc;
        this.wellLoc = wellLoc;
        this.nearbyRobots = nearbyRobots;
    }

    public MockRobotController(int roundNum, boolean canBuildAnchor, boolean canBuildRobot) {
        this.roundNum = roundNum;
        this.canBuildAnchor = canBuildAnchor;
        this.canBuildRobot = canBuildRobot;
    }

    public void setMapWidth(int width) {
        this.mapWidth = width;
    }

    public void setMapHeight(int height) {
        this.mapHeight = height;
    }

    public void setSharedArrayValue(int index, int value) {
        this.sharedArray[index] = value;
    }
    public void setNearbyIslands(int[][] nearbyIslands) {
        this.nearbyIslands = nearbyIslands;
    }

    public void setIslandOccupyingTeam(Team[] islandOccupyingTeam) {
        this.islandOccupyingTeam = islandOccupyingTeam;
    }

    public void setIslandLocations(MapLocation[][] islandLocations) {
        this.islandLocations = islandLocations;
    }
    public void setResourceLocations(MapLocation[] resourceLocations) {
        this.resourceLocations = resourceLocations;
    }


    public MockRobotController(int adamantium, int mana, int elixar) {
        this.adamantium = adamantium;
        this.mana = mana;
        this.elixar = elixar;
    }

    public MockRobotController(boolean canMoveResult) {
        this.canMoveResult = canMoveResult;
        this.hasMoved = false;
    }

    public MockRobotController(RobotType robotType, int x, int y) {
        this.robotType = robotType;
        this.robotLocation = new MapLocation(x,y);
    }

    public MockRobotController() {}

    public int getAdamantium() {
        return adamantium;
    }

    public int getMana() {
        return mana;
    }

    public RobotInfo[] nearbyRobots;
    public WellInfo[] nearbyWells;


    public void setNearbyRobots(RobotInfo[] nearbyRobots) {
        this.nearbyRobots = nearbyRobots;
    }

    @Override
    public int getRoundNum() {
        return roundNum;
    }

    @Override
    public int getMapWidth() {
        return mapWidth;
    }

    @Override
    public int getMapHeight() {
        return mapHeight;
    }

    @Override
    public int getIslandCount() {
        return 0;
    }

    @Override
    public int getRobotCount() {
        return 0;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team value) { team = value;}

    @Override
    public RobotType getType() {
        return robotType;
    }
//    public RobotType getType(RobotType robotType) { return robotType; }

    @Override
    public MapLocation getLocation() {
        return randomLocation;
    }

    @Override
    public int getHealth() {
        return 0;
    }

    @Override
    public int getResourceAmount(ResourceType rType) {
        if (rType.equals(ResourceType.MANA)){
            return getMana();
        }
        return getAdamantium();
    }

    @Override
    public Anchor getAnchor() throws GameActionException {
        return null;
    }

    @Override
    public int getNumAnchors(Anchor anchorType) {
        return 0;
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public boolean onTheMap(MapLocation loc) {
        return false;
    }

    @Override
    public boolean canSenseLocation(MapLocation loc) {
        return false;
    }

    @Override
    public boolean canActLocation(MapLocation loc) {
        return false;
    }

    @Override
    public boolean isLocationOccupied(MapLocation loc) throws GameActionException {
        return false;
    }

    @Override
    public boolean canSenseRobotAtLocation(MapLocation loc) {
        return canSenseRobot_Ret_Value;
    }

    public void setCanSenseRobot_Ret_Value(boolean value) { canSenseRobot_Ret_Value = value; }


    @Override
    public RobotInfo senseRobotAtLocation(MapLocation loc) throws GameActionException {
        return sensedRobotInfo;
    }

    public void setSensedRobot(RobotInfo values) {
        sensedRobotInfo = values;
    }

    @Override
    public boolean canSenseRobot(int id) {
        return false;
    }

    @Override
    public RobotInfo senseRobot(int id) throws GameActionException {
        return null;
    }

    @Override
    public RobotInfo[] senseNearbyRobots() {
        return nearbyRobots;
    }

    @Override
    public RobotInfo[] senseNearbyRobots(int radiusSquared) throws GameActionException {
        return new RobotInfo[0];
    }

    @Override
    public RobotInfo[] senseNearbyRobots(int radiusSquared, Team team) throws GameActionException {
        return sensedRobots;

    }

    @Override
    public RobotInfo[] senseNearbyRobots(MapLocation center, int radiusSquared, Team team) throws GameActionException {
        return new RobotInfo[0];
    }

    @Override
    public boolean sensePassability(MapLocation loc) throws GameActionException {
        return false;
    }

    @Override
    public int senseIsland(MapLocation loc) throws GameActionException {
        return senseIsland_Ret_Value;
    }

    public void setSenseIsland_Ret_Value(int value) {
        senseIsland_Ret_Value = value;
    }

    @Override
    public int[] senseNearbyIslands() {
        return new int[]{1};


    }

    @Override
    public MapLocation[] senseNearbyIslandLocations(int idx) throws GameActionException {
        if (idx == 1) {
            islandLoc = new MapLocation(10, 10); // Assuming islandLoc is (10, 10) for island 1
            return new MapLocation[]{ islandLoc };
        } else {
            return new MapLocation[0];
        }

    }

    @Override
    public MapLocation[] senseNearbyIslandLocations(int radiusSquared, int idx) throws GameActionException {
        return new MapLocation[0];
    }

    @Override
    public MapLocation[] senseNearbyIslandLocations(MapLocation center, int radiusSquared, int idx) throws GameActionException {
        return new MapLocation[0];
    }

    @Override
    public Team senseTeamOccupyingIsland(int islandIdx) throws GameActionException {
        return Team.NEUTRAL;

    }

    @Override
    public int senseAnchorPlantedHealth(int islandIdx) throws GameActionException {
        return 0;
    }

    @Override
    public Anchor senseAnchor(int islandIdx) throws GameActionException {
        return null;
    }

    @Override
    public boolean senseCloud(MapLocation loc) throws GameActionException {
        return false;
    }

    @Override
    public MapLocation[] senseNearbyCloudLocations() {
        return new MapLocation[0];
    }

    @Override
    public MapLocation[] senseNearbyCloudLocations(int radiusSquared) throws GameActionException {
        return new MapLocation[0];
    }

    @Override
    public MapLocation[] senseNearbyCloudLocations(MapLocation center, int radiusSquared) throws GameActionException {
        return new MapLocation[0];
    }

    @Override
    public WellInfo senseWell(MapLocation loc) throws GameActionException {
        return senseWell_Ret_Value;
    }

    public void setSenseWell_Ret_Value(WellInfo wInfo) {
        senseWell_Ret_Value = wInfo;
    }

    @Override
    public WellInfo[] senseNearbyWells() {
        return new WellInfo[0];
    }

    @Override
    public WellInfo[] senseNearbyWells(int radiusSquared) throws GameActionException {
        return new WellInfo[0];
    }

    @Override
    public WellInfo[] senseNearbyWells(MapLocation center, int radiusSquared) throws GameActionException {
        return new WellInfo[0];
    }

    @Override
    public WellInfo[] senseNearbyWells(ResourceType resourceType) {
        return new WellInfo[0];
    }

    @Override
    public WellInfo[] senseNearbyWells(int radiusSquared, ResourceType resourceType) throws GameActionException {
        return new WellInfo[0];
    }

    @Override
    public WellInfo[] senseNearbyWells(MapLocation center, int radiusSquared, ResourceType resourceType) throws GameActionException {
        return new WellInfo[0];
    }

    @Override
    public MapInfo senseMapInfo(MapLocation loc) throws GameActionException {
        return sensedMapInfo[loc.x*10 + loc.y];
    }
    public void setSensedMapInfo(MapInfo[] value) { sensedMapInfo = value; }

    @Override
    public MapInfo[] senseNearbyMapInfos() {
        return new MapInfo[0];
    }

    @Override
    public MapInfo[] senseNearbyMapInfos(int radiusSquared) throws GameActionException {
        return new MapInfo[0];
    }

    @Override
    public MapInfo[] senseNearbyMapInfos(MapLocation center) throws GameActionException {
        return new MapInfo[0];
    }

    @Override
    public MapInfo[] senseNearbyMapInfos(MapLocation center, int radiusSquared) throws GameActionException {
        return new MapInfo[0];
    }

    @Override
    public MapLocation adjacentLocation(Direction dir) {
        return null;
    }

    @Override
    public MapLocation[] getAllLocationsWithinRadiusSquared(MapLocation center, int radiusSquared) throws GameActionException {
        return new MapLocation[0];
    }

    @Override
    public boolean isActionReady() {
        return false;
    }

    @Override
    public int getActionCooldownTurns() {
        return 0;
    }

    @Override
    public boolean isMovementReady() {
        return false;
    }

    @Override
    public int getMovementCooldownTurns() {
        return 0;
    }

    @Override
    public boolean canMove(Direction dir) {

        return canMoveResult;

    }


    @Override
    public void move(Direction dir) throws GameActionException {
        moveCalled = true;
        lastMoveTarget = getLocation().add(dir);

        if (canMoveResult) {
            hasMoved = true;
        }
    }

    @Override
    public boolean canBuildRobot(RobotType type, MapLocation loc) {
        return canBuildRobot;
    }

    @Override
    public void buildRobot(RobotType type, MapLocation loc) throws GameActionException {

    }

    @Override
    public boolean canAttack(MapLocation loc) {

        this.attackedLocation = loc;
        return false;
    }

    @Override
    public void attack(MapLocation loc) throws GameActionException {

    }

    @Override
    public boolean canBoost() {
        return false;
    }

    @Override
    public void boost() throws GameActionException {

    }

    @Override
    public boolean canDestabilize(MapLocation loc) {
        return false;
    }

    @Override
    public void destabilize(MapLocation loc) throws GameActionException {

    }

    @Override
    public boolean canCollectResource(MapLocation loc, int amount) {


        return true;
    }

    @Override
    public void collectResource(MapLocation loc, int amount) throws GameActionException {



    }

    @Override
    public boolean canTransferResource(MapLocation loc, ResourceType rType, int amount) {
        return true;
    }

    @Override
    public void transferResource(MapLocation loc, ResourceType rType, int amount) throws GameActionException {

    }

    @Override
    public boolean canBuildAnchor(Anchor anchor) {
        return canBuildAnchor;
    }

    @Override
    public void buildAnchor(Anchor anchor) throws GameActionException {
        buildAnchorCalled = true;
    }

    @Override
    public boolean canTakeAnchor(MapLocation loc, Anchor anchorType) {
        return true;
    }

    @Override
    public void takeAnchor(MapLocation loc, Anchor anchorType) throws GameActionException {

    }

    @Override
    public boolean canReturnAnchor(MapLocation loc) {
        return false;
    }

    @Override
    public void returnAnchor(MapLocation loc) throws GameActionException {

    }

    @Override
    public boolean canPlaceAnchor() {
        return true;
    }

    @Override
    public void placeAnchor() throws GameActionException {

    }

    @Override
    public int readSharedArray(int index) throws GameActionException {
        return 0;
    }

    @Override
    public boolean canWriteSharedArray(int index, int value) {
        return false;
    }

    @Override
    public void writeSharedArray(int index, int value) throws GameActionException {

    }

    @Override
    public void disintegrate() {

    }

    @Override
    public void resign() {

    }

    @Override
    public void setIndicatorString(String string) {
        indicatorString = string;
    }

    @Override
    public void setIndicatorDot(MapLocation loc, int red, int green, int blue) {

    }

    @Override
    public void setIndicatorLine(MapLocation startLoc, MapLocation endLoc, int red, int green, int blue) {

    }

    public void setNearbyWells(WellInfo[] wells) {
        this.nearbyWells = wells;
    }

    public void setNearbyIslands(int[] id) {
        nearByIsland = new int[id.length];
        System.arraycopy(id, 0, nearByIsland, 0, id.length);
    }

    public void setTeamOccupyingIsland(int neutralIslandId, Team team) {
        this.team = team;
    }

    public void setNearbyIslandLocations(int neutralIslandId, MapLocation[] loc) {
        if (nearByIsland == null || nearByIsland.length == 0) {
            // Handle the case when no nearby islands are set
            return;
        }
        if (nearByIsland[0] == neutralIslandId) {
            islandLocations = new MapLocation[][]{loc};
        }
    }



    public void setCanMoveResult(boolean canMoveResult) {
        this.canMoveResult = canMoveResult;
    }


    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean getMoveCalled() {
        return moveCalled;
    }

    public boolean getAttackCalled() {
        return canAttack;
    }

    public String getIndicatorString() {

        return indicatorString;
    }

    public void setSenseNearbyRobots(RobotInfo[] robotInfos) {
        this.sensedRobots = robotInfos;
    }

    public void setLocation(MapLocation currentLocation) {
        this.randomLocation = currentLocation;
    }


    public boolean didAttack(MapLocation targetLocation) {
        return canAttack(targetLocation);
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    void moveTowards(MapLocation location) {

        moveTowardsCalls++;
        // Simulate movement towards the location
    }
    void moveToward(MapLocation location) {
        moveTowardsLocation = location;
        lastMove = location;

    }


    public boolean isMoveRandomCalled() {
        return moveCalled;
    }


    public Object getLastMove() {
        return lastMove;
    }




    public void setTurnCount(int i) {
        this.turnCount = i;
    }


    public void setSharedArray(int[] sharedArray) {
        this.sharedArray = sharedArray;
    }

    public void setWellLocation(MapLocation wellLocation) {
        this.wellLoc = wellLocation;
    }



}

