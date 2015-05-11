/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Test class for {@code BasicShip}
 *
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @see BasicShip
 * @since v1.0.0
 */
public class BasicShipTest {
    private BasicShip ship;

    @Before
    public void setUp() throws Exception {
        ship = new BasicShip();

        ship.addSquare(4, 3);
        ship.addSquare(6, 1);
        ship.addSquare(1, 7);
    }

    @After
    public void tearDown() throws Exception {
        ship = null;
    }

    @Test
    public void testBottomRight() throws Exception {
        assertEquals(new Square(6, 7), ship.getMaxSquare());
    }

    @Test
    public void testMove() throws Exception {
        ship.move(2, -2);

        List<Square> results = new ArrayList<>();
        ship.iterator().forEachRemaining(results::add);

        List<Square> expected = Arrays.asList(new Square(6, 1), new Square(8, -1), new Square(3, 5));
        Collections.sort(expected);

        assertThat(results, is(expected));
        assertEquals(new Square(8, 5), ship.getMaxSquare());
    }

    @Test
    public void testNormalize() throws Exception {
        ship.normalize();

        List<Square> results = new ArrayList<>();
        ship.iterator().forEachRemaining(results::add);

        List<Square> expected = Arrays.asList(new Square(3, 2), new Square(5, 0), new Square(0, 6));
        Collections.sort(expected);

        assertThat(results, is(expected));
        assertEquals(new Square(5, 6), ship.getMaxSquare());
    }

    @Test
    public void testRotateCW() throws Exception {
        Ship rotatedShip = ship.rotateCWNinety(1);

        List<Square> results = new ArrayList<>();
        rotatedShip.iterator().forEachRemaining(results::add);

        List<Square> expected = Arrays.asList(new Square(2, 2), new Square(0, 0), new Square(6, 5));
        Collections.sort(expected);

        assertThat(results, is(expected));
        assertEquals(new Square(6, 5), rotatedShip.getMaxSquare());
    }
}
