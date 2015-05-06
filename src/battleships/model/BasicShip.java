/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
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
        bottomRight = new Square(0, 0);
    }

    @Override
    public void addSquare(int x, int y) {
        Square newSquare = new Square(x, y);

        shipSquares.add(newSquare);

        if (x > bottomRight.getX()) {
            bottomRight.offset(x - bottomRight.getX(), 0);
        }

        if (y >= bottomRight.getY()) {
            bottomRight.offset(0, y - bottomRight.getY());
        }

    }

    @Override
    public int numSquares() {
        return shipSquares.size();
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
        return new Square(bottomRight);
    }

    @Override
    public Iterator<Square> iterator() {
        return shipSquares.iterator();
    }

}
