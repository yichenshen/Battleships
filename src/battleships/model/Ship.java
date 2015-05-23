/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.model;

/**
 * The interface for a ship on the board.
 * <p>
 * A ship consists of a number of squares.
 * <p>
 * This interface should be able to return an Iterator that iterates through all
 * the ship's squares.
 * <p>
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public interface Ship extends Iterable<Square> {

    /**
     * Adds a new square to the ship.
     * <p>
     * @param x X-coordinate of square
     * @param y Y-coordinate of square
     */
    void addSquare(int x, int y);

    /**
     * Adds a new square with the {@code Square} object.
     * <p>
     * @param sqr The {@code Square} object
     * @see Square
     */
    void addSquare(Square sqr);

    /**
     * Returns the number of squares which makes up the ship.
     * <p>
     * @return The number of squares
     */
    int numSquares();

    /**
     * Moves the ship.
     * <p>
     * @param x X offset
     * @param y Y offset
     */
    void move(int x, int y);

    /**
     * Shifts the top left of the ship to 0,0
     */
    void normalize();

    /**
     * Returns the maximum square of the rectangle containing the ship.
     * <p>
     * When used after normalize, gives the length and breadth of the ship minus
     * 1.
     * <p>
     * @return The maximum square.
     */
    Square getMaxSquare();

    /**
     * Returns a new ship, that is this ship rotated 90 degrees clockwise times
     * the number of time specified.
     * <p>
     * Ship is normalized after rotation.
     * <p>
     * @param repeat Number of times to rotate 90 degrees
     * @return The new rotated ship
     * @see #normalize()
     */
    Ship rotateCWNinety(int repeat);
}
