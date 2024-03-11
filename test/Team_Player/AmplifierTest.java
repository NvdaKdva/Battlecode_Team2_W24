package Team_Player;

import battlecode.common.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class AmplifierTest {

    @Test
    public void testSetAmpArr() {
        int[] expected = {5,5,10,5,15,5,5,10,10,10,15,10,5,15,10,15,15,15};
        int[] result = Amplifier.setAmpArr(20,20);
        assertArrayEquals(result,expected);
    }

    @Test
    public void testGetMyPlace() {
        Amplifier test_amp = new Amplifier();
        test_amp.amp_arr = Amplifier.setAmpArr(20,20);
        MapLocation expected_1 = new MapLocation(5,5);
        MapLocation expected_2 = new MapLocation(15,15);

        MapLocation result_1 = Amplifier.getMyPlace(0,test_amp.amp_arr .length);
        assertEquals(expected_1, result_1);

        MapLocation result_2 = Amplifier.getMyPlace(8,test_amp.amp_arr .length);
        assertEquals(expected_2, result_2);
    }

    @Test
    public void runAmplifierTest() throws GameActionException {
        MockRobotController mrc = new MockRobotController(RobotType.AMPLIFIER, 4, 10);
        Amplifier test_amp_2 = new Amplifier();
        test_amp_2.amp_arr = Amplifier.setAmpArr(20,20);
        Map myMap = new Map(20,20);

        test_amp_2.myPlace = test_amp_2.getMyPlace(0, test_amp_2.amp_arr.length);

        test_amp_2.runAmplifier(mrc,2,myMap);
        assert mrc.moveCalled;

        mrc.moveCalled = false;
        MapLocation non_adj_loc = new MapLocation(5,5);
        mrc.setLocation(non_adj_loc);

        test_amp_2.runAmplifier(mrc,2,myMap);
        assert  mrc.isMoveRandomCalled();
    }

}