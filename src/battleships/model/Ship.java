/*
 * Copyright (c) 2015 Shen Yichen <2007.yichen@gmail.com>.
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
public interface Ship extends Iterable<Ship.Square> {

    /**
     * Adds a new square to the ship.
     *
     * @param x X-coordinate of square
     * @param y Y-coordinate of square
     */
    void addSquare(int x, int y);

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

    /**
     * A interface describing a square on the board.
     */
    interface Square {

        /**
         * Returns the X value of this square.
         *
         * @return X value
         */
        int getX();

        /**
         * Returns the Y value of this square.
         *
         * @return Y value.
         */
        int getY();

        /**
         * Shifts the square.
         *
         * @param x X offset
         * @param y Y offset
         */
        void offset(int x, int y);
    }
}
