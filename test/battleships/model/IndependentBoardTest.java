/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Javadoc documentation here
 *
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v
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

        board.addShip(ship1);
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

    }

    @Test
    public void testGetProbabilityMatrixForOneShip() throws Exception {

    }

    @Test
    public void testGetShipsMatrix() throws Exception {

    }

    @Test
    public void testGetShipsMatrixForOneShip() throws Exception {
        assertEquals(3, board.getShipsMatrix(ship1)[0][0].intValue());

        assertEquals(12, board.getShipsMatrix(ship1)[1][1].intValue());
    }

    @Test
    public void testShipWithinBoard() throws Exception {

    }

    @Test
    public void testCheckConfig() throws Exception {

    }

    @Test
    public void testBoardMapper() throws Exception {

    }

    @Test
    public void testAddConfig() throws Exception {

    }
}
