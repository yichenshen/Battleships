/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * The model class for a ship on the board that is simply the sum of it's
 * squares.
 * <p>
 * Squares are stored as a Set of squares with no additional properties.
 * <p>
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public class BasicShip implements Ship {

    private final Set<Square> shipSquares;
    private final Square bottomRight;

    /**
     * Creates a new BasicShip with no squares.
     */
    public BasicShip() {
        shipSquares = new TreeSet<>();
        bottomRight = new Square(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @Override
    public void addSquare(int x, int y) {
        Square newSquare = new Square(x, y);
        addSquare(newSquare);
    }

    @Override
    public void addSquare(Square sqr) {
        shipSquares.add(sqr);

        if (sqr.getX() > bottomRight.getX()) {
            bottomRight.offset(sqr.getX() - bottomRight.getX(), 0);
        }

        if (sqr.getY() > bottomRight.getY()) {
            bottomRight.offset(0, sqr.getY() - bottomRight.getY());
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
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (Square sqr : shipSquares) {
            minX = Math.min(minX, sqr.getX());
            minY = Math.min(minY, sqr.getY());
        }

        move(minX * -1, minY * -1);
    }

    @Override
    public Square getMaxSquare() {
        return new Square(bottomRight);
    }

    @Override
    public Ship rotateCWNinety(int repeat) {
        repeat %= 4;

        ArrayList<Square> squareList = new ArrayList<>(shipSquares.size());
        for (Square sqr : this) {
            squareList.add(new Square(sqr));
        }

        for (int i = 0; i < repeat; i++) {
            squareList.forEach(battleships.model.Square::rotateCW);
        }

        Ship rotatedShip = new BasicShip();
        squareList.forEach(rotatedShip::addSquare);
        rotatedShip.normalize();

        return rotatedShip;
    }

    @Override
    public Iterator<Square> iterator() {
        return shipSquares.iterator();
    }
}
