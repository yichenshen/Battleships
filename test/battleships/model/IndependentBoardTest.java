/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.model;

import org.junit.After;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@code IndependentBoard}.
 * <p>
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public class IndependentBoardTest {

    IndependentBoard board;
    //Test ships
    Ship ship1, ship2, ship3;

    @Before
    public void setUp() throws Exception {
        board = new IndependentBoard(3, 3);

        // x x
        // x
        ship1 = new BasicShip();
        ship1.addSquare(0, 0);
        ship1.addSquare(0, 1);
        ship1.addSquare(1, 0);
        ship1.normalize();

        // x x
        ship2 = new BasicShip();
        ship2.addSquare(0, 0);
        ship2.addSquare(0, 1);
        ship2.normalize();

        // x x
        // x
        //     x
        ship3 = new BasicShip();
        ship3.addSquare(0, 0);
        ship3.addSquare(0, 1);
        ship3.addSquare(1, 0);
        ship3.addSquare(2, 2);
        ship3.normalize();

        board.addShip(ship1);
        board.addShip(ship2);
        board.addShip(ship3);
    }

    @After
    public void tearDown() throws Exception {
        board = null;
        ship1 = null;
        ship2 = null;
        ship3 = null;
    }

    @Test
    public void testGetProbabilityMatrix() throws Exception {

        double val1 = 3.0 / 16 + 4.0 / 24 - 3.0 / 16 * 4.0 / 24;
        val1 = val1 + 0.5 - val1 * 0.5;

        double val2 = 6.0 / 16 + 6.0 / 24 - 6.0 / 16 * 6.0 / 24;
        val2 = val2 + 0.5 - val2 * 0.5;

        double val3 = 12.0 / 16 + 8.0 / 24 - 12.0 / 16 * 8.0 / 24;
        //center for ship3 is 0

        double[][] expected = {{val1, val2, val1}, {val2, val3, val2}, {val1, val2, val1}};

        assertArrayEquals(expected, board.getProbabilityMatrix());
    }

    @Test
    public void testGetProbabilityMatrixForOneShip() throws Exception {
        Double[][] probMatrix = board.getProbabilityMatrix(ship3);
        double[][] expected = {{0.5, 0.5, 0.5}, {0.5, 0, 0.5}, {0.5, 0.5, 0.5}};

        assertArrayEquals(expected, probMatrix);
    }

    @Test
    public void testGetShipsMatrix() throws Exception {
        Integer[][] shipsMatrix = board.getShipsMatrix();
        int[][] expected = {{9, 14, 9}, {14, 20, 14}, {9, 14, 9}};

        assertArrayEquals(expected, shipsMatrix);
    }

    @Test
    public void testGetShipsMatrixForOneShip() throws Exception {
        Integer[][] shipMatrix = board.getShipsMatrix(ship1);
        int[][] expected = {{3, 6, 3}, {6, 12, 6}, {3, 6, 3}};

        assertArrayEquals(expected, shipMatrix);
    }

    @Test
    public void testShipWithinBoard() throws Exception {
        Ship testShip = new BasicShip();

        testShip.addSquare(4, 4);
        testShip.addSquare(5, 5);

        //Remember ship is not normalized
        assertFalse(board.shipWithinBoard(testShip, 0, 0));

        testShip.normalize();
        assertTrue(board.shipWithinBoard(testShip, 1, 1));
    }

    @Test
    public void testCheckConfig() throws Exception {
        board.stateChange(0, 0, Board.SquareState.MISS);

        assertFalse(board.checkConfig(ship1, 0, 0));
        assertTrue(board.checkConfig(ship1.rotateCWNinety(2), 0, 0));
    }

    @Test
    public void testBoardMapper() throws Exception {
        Integer[][] iniArray = new Integer[3][3];

        for (Integer[] iniArrayCol : iniArray) {
            for (int j = 0; j < iniArrayCol.length; j++) {
                iniArrayCol[j] = 5;
            }
        }

        board.boardMapper(iniArray, ship3, (int newVal, Integer orgVal, int total) -> orgVal * newVal - total);

        int[][] expected = {{6, 6, 6}, {6, -4, 6}, {6, 6, 6}};

        assertArrayEquals(expected, iniArray);
    }

    @Test
    @SuppressWarnings("UnnecessaryUnboxing")
    public void testAddConfig() throws Exception {
        board.addConfig(ship1, ship1, 0, 0);

        assertEquals(4, board.getShipsMatrix(ship1)[0][0].intValue());
    }

    @Test
    public void testSquareMiss() throws Exception {
        board.stateChange(1, 1, Board.SquareState.MISS);

        double val1 = 1.0 / 4 + 4.0 / 16 - 1.0 / 4 * 4.0 / 16;
        val1 = val1 + 0.5 - val1 * 0.5;

        double val2 = 2.0 / 4 + 4.0 / 16 - 2.0 / 4 * 4.0 / 16;
        val2 = val2 + 0.5 - val2 * 0.5;

        double val3 = 0;

        double[][] expected = {{val1, val2, val1}, {val2, val3, val2}, {val1, val2, val1}};

        assertArrayEquals(expected, board.getProbabilityMatrix());
    }
}
