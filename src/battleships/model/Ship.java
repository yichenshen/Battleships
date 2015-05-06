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
 * This interface should be able to return an Iterator that iterates through all the ship's squares.
 *
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public interface Ship extends Iterable<Square> {

    /**
     * Adds a new square to the ship.
     *
     * @param x X-coordinate of square
     * @param y Y-coordinate of square
     */
    void addSquare(int x, int y);

    /**
     * Returns the number of squares which makes up the ship.
     *
     * @return The number of squares
     */
    int numSquares();

    /**
     * Moves the ship.
     *
     * @param x X offset
     * @param y Y offset
     */
    void move(int x, int y);

    /**
     * Shifts the top left of the ship to 0,0
     */
    void normalize();

    /**
     * Returns the bottom right position of the rectangle containing the ship.
     * <p>
     * When used after normalize, gives the length and breadth of the ship.
     *
     * @return The bottom right square.
     */
    Square getBottomRight();
}
