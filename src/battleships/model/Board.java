/*
 * Copyright (c) 2015 Shen Yichen <2007.yichen@gmail.com>.
 * Under The MIT License.
 */

package battleships.model;

/**
 * Created by yichen on 4/30/15.
 */

import java.util.Collection;

/**
 * A game board interface, that represents a game board.
 *
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public interface Board {

    /**
     * Gets the horizontal size of the board.
     *
     * @return The width of the board.
     */
    int getWidth();

    /**
     * Gets the vertical size of the board.
     *
     * @return The height of the board.
     */
    int getHeight();

    /**
     * Gets the probability map for all ships on this board.
     * <p>
     * Each square on the board is given a probability that any ship may occupy that square.
     *
     * @return Probability map
     */
    double[][] getProbabilityMap();

    /**
     * Gets the probability map for a single ship on this board.
     * <p>
     * Each square on the board is given a probability that the ship provided may occupy that square.
     *
     * @param ship Ship to get map for
     * @return Probability Map
     */
    double[][] getProbabilityMap(Ship ship);

    /**
     * Adds a ship to the board.
     *
     * @param ship The ship
     */
    void addShip(Ship ship);

    /**
     * Gets the list of ships currently tagged to this board.
     *
     * @return List of ships
     */
    Collection<Ship> getShips();
}
