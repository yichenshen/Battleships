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
     * Returns the state of the specified square.
     * <p>
     * @param x The X position
     * @param y The Y position
     * @return The state of the square.
     */
    SquareState getState(int x, int y);

    /**
     * Returns a matrix of the states of the board.
     * <p>
     * @return The states matrix
     */
    SquareState[][] getStatesMatrix();

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

    /**
     * Changes the state of a square.
     * <p>
     * The state of the square is changed together with the necessary data to
     * reflect the state change when one of the data matrix methods is called.
     * <p>
     * If the new state defined is illegal given the current state of the board,
     * an {@code IllegalStateException} is thrown.
     * <p>
     * @param x        X-coordinate of the square
     * @param y        Y-coordinate of the square
     * @param newState The state of the square to change to.
     * @throws IllegalStateException If the new state is not allowed for the
     *                               specified square.
     */
    void stateChange(int x, int y, SquareState newState);
}
