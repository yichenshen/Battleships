/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.model;

import java.util.Collection;

/**
 * A game board interface, that represents a game board.
 * <p>
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public interface Board {

    /**
     * States of a square on the board.
     */
    public enum SquareState {

        /**
         * When square is open and untouched.
         */
        OPEN,
        /**
         * When the square is confirmed to be empty.
         */
        MISS,
        /**
         * When the square is confirmed to be occupied.
         */
        HIT,
        /**
         * When the square is occupied by a sunken ship.
         */
        SUNK
    }

    /**
     * Gets the horizontal size of the board.
     * <p>
     * @return The width of the board.
     */
    int getWidth();

    /**
     * Gets the vertical size of the board.
     * <p>
     * @return The height of the board.
     */
    int getHeight();

    /**
     * Gets the probability matrix for all ships on this board.
     * <p>
     * Each square on the board is given a probability that any ship may occupy
     * that square.
     * <p>
     * @return Probability matrix
     */
    Double[][] getProbabilityMatrix();

    /**
     * Gets the probability matrix for a single ship on this board.
     * <p>
     * Each square on the board is given a probability that the ship provided
     * may occupy that square.
     * <p>
     * @param ship Ship to get matrix for
     * @return Probability matrix
     */
    Double[][] getProbabilityMatrix(Ship ship);

    /**
     * Returns the matrix showing number of possible ship placements for each
     * square.
     * <p>
     * @return The ship placement matrix
     */
    Integer[][] getShipsMatrix();

    /**
     * Returns the map showing number of possible ship placements for each
     * square, for the given ship.
     * <p>
     * @param ship Ship to get map for
     * @return The ship placement map
     */
    Integer[][] getShipsMatrix(Ship ship);

    /**
     * Adds a ship to the board.
     * <p>
     * @param ship The ship
     */
    void addShip(Ship ship);

    /**
     * Gets the list of ships currently tagged to this board.
     * <p>
     * @return List of ships
     */
    Collection<Ship> getShips();

    /**
     * Checks if the ship could fit into the board at specified position.
     * <p>
     * @param ship The ship
     * @param x    Starting x-position
     * @param y    Starting y-position
     * @return {@code true} if the ship fits, {@code false} if the ship extends
     *         of out of the board
     */
    boolean shipWithinBoard(Ship ship, int x, int y);

    /**
     * Checks if the ship could fit into the board at specified position.
     * <p>
     * @param ship The ship
     * @param sqr  The starting position
     * @return {@code true} if the ship fits, {@code false} if the ship extends
     *         of out of the board
     */
    boolean shipWithinBoard(Ship ship, Square sqr);
}
