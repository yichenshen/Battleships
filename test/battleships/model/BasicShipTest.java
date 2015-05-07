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
 * Javadoc documentation here
 *
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v
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
        assertEquals(new Square(6, 7), ship.getBottomRight());
    }

    @Test
    public void testMove() throws Exception {
        ship.move(2, -2);

        List<Square> results = new ArrayList<>();
        ship.iterator().forEachRemaining(results::add);

        List<Square> expected = Arrays.asList(new Square(6, 1), new Square(8, -1), new Square(3, 5));
        Collections.sort(expected);

        assertThat(results, is(expected));
        assertEquals(new Square(8,5), ship.getBottomRight());
    }

    @Test
    public void testNormalize() throws Exception {
        ship.normalize();

        List<Square> results = new ArrayList<>();
        ship.iterator().forEachRemaining(results::add);

        List<Square> expected = Arrays.asList(new Square(3, 2), new Square(5, 0), new Square(0, 6));
        Collections.sort(expected);

        assertThat(results, is(expected));
        assertEquals(new Square(5,6), ship.getBottomRight());
    }


}
