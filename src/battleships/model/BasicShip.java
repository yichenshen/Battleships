/*
 * Copyright (c) 2015 Shen Yichen <2007.yichen@gmail.com>.
 * Under The MIT License.
 */

package battleships.model;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * The model class for a ship on the board that is simply the sum of it's squares.
 * <p>
 * Squares are stored as a Set of squares with no additional properties.
 *
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public class BasicShip implements Ship {

    private Set<Square> shipSquares;
    private Square bottomRight;

    /**
     * Creates a new BasicShip with no squares.
     */
    public BasicShip() {
        shipSquares = new TreeSet<>();
        bottomRight = new BasicSquare(0, 0);
    }

    @Override
    public void addSquare(int x, int y) {
        Square newSquare = new BasicSquare(x, y);

        shipSquares.add(newSquare);

        if (x > bottomRight.getX()) {
            bottomRight.offset(x - bottomRight.getX(), 0);
        }

        if (y >= bottomRight.getY()) {
            bottomRight.offset(0, y - bottomRight.getY());
        }

    }

    @Override
    public void move(int x, int y) {
        //Note that since we're offsetting everything, order is not changed
        shipSquares.forEach((Square s) -> s.offset(x, y));

        bottomRight.offset(x, y);
    }

    @Override
    public void normalize() {
        int minX = 0, minY = 0;
        for (Square sqr : shipSquares) {
            minX = Math.min(minX, sqr.getX());
            minY = Math.min(minY, sqr.getY());
        }

        move(minX * -1, minY * -1);
    }

    @Override
    public Square getBottomRight() {
        return new BasicSquare(bottomRight);
    }

    @Override
    public Iterator<Square> iterator() {
        return shipSquares.iterator();
    }

    /**
     * A basic implementation of the square class.
     */
    public static class BasicSquare implements Square {

        private int X, Y;

        /**
         * Setter constructor.
         *
         * @param x X coordinate
         * @param y Y coordinate
         */
        public BasicSquare(int x, int y) {
            X = x;
            Y = y;
        }

        /**
         * Copy constructor.
         *
         * @param sqr The square to be copied
         */
        public BasicSquare(Square sqr) {
            X = sqr.getX();
            Y = sqr.getY();
        }

        @Override
        public int getX() {
            return X;
        }

        @Override
        public int getY() {
            return Y;
        }

        @Override
        public void offset(int x, int y) {
            X += x;
            Y += y;
        }
    }
}
