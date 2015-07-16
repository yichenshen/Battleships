/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.controller;

import battleships.model.BasicShip;
import battleships.model.Board;
import battleships.model.IndependentBoard;
import battleships.model.Ship;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * The controller class for {@code CommandCenter}.
 * <p>
 * This class links up the GUI to the board objects models.
 * <p>
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 * @see battleships.gui.CommandCenter
 */
public class CommandCenterController {

    /**
     * The game board.
     */
    private final Board board;
    /**
     * The ships on the game board.
     * <p>
     * Each ship has a name to identify it.
     */
    private final Map<String, Ship> ships;
    /**
     * A result cache to store processes data.
     */
    private double[][] resultCache;
    private Board.SquareState[][] stateCache;
    /**
     * The largest value for raw data in the data matrix.
     */
    private int maxResult;

    /**
     * Creates a standard battleship game.
     * <p>
     * A standard game comprises of a 10x10 board with the following ships:
     * <ul>
     * <li>Aircraft Carrier (1x5)
     * <li>Battleship (1x4)
     * <li>Submarine (1x3)
     * <li>Cruiser (1x3)
     * <li>Destroyer (1x2)
     * </ul>
     */
    public CommandCenterController() {
        board = new IndependentBoard(10, 10);

        ships = new LinkedHashMap<>();

        int[] shipSizes = {5, 4, 3, 3, 2};
        String[] shipNames = {"Aircraft carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};

        for (int i = 0; i < shipNames.length; i++) {
            Ship newShip = new BasicShip();

            for (int j = 0; j < shipSizes[i]; j++) {
                newShip.addSquare(0, j);
            }
            board.addShip(newShip);
            ships.put(shipNames[i], newShip);
        }
    }

    public Set<String> getShipNames() {
        return ships.keySet();
    }

    /**
     * Gets the ship object by name.
     * <p>
     * @param name The name of the ship.
     * @return The {@code Ship} object
     */
    public Ship getShip(String name) {
        return ships.get(name);
    }

    /**
     * Gets the number of rows of the board.
     * <p>
     * @return The number of rows
     */
    public int getBoardHeight() {
        return board.getHeight();
    }

    /**
     * Gets the number of columns of the board.
     * <p>
     * @return The number of columns
     */
    public int getBoardWidth() {
        return board.getWidth();
    }

    /**
     * Returns the data to be shown on the display panel.
     * <p>
     * A 2D array is returned, with each element being a number from 0 to 1.
     * Depending on the settings, the data may represent different things.
     * <p>
     * For calculations using total number of ship configurations, each element
     * is the number of configurations normalised from 0 to 1 based on the
     * highest number of configurations in the matrix.
     * <p>
     * Returns results from a cache directly. Modifying the array returned will
     * modify the cache too! Cache is recalculated when states are changed. A
     * null cache will cause a call to {@code recalculate()} before results are
     * returned.
     * <p>
     * @return The numerical data to display
     * @see #recalculate()
     */
    public double[][] getData() {
        if (resultCache == null) {
            recalculate();
        }

        return resultCache;
    }

    public int getMax() {
        if (resultCache == null) {
            recalculate();
        }
        return maxResult;
    }

    public int getSqaureVal(int x, int y) {
        if (resultCache == null) {
            recalculate();
        }

        return (int) Math.round(resultCache[x][y] * maxResult);
    }

    /**
     * Returns the matrix for the states of the squares on the board.
     * <p>
     * @return The state matrix
     */
    public Board.SquareState[][] getStateData() {
        if (stateCache == null) {
            recalculate();
        }
        return stateCache;
    }

    /**
     * Changes the state of the particular square on the board.
     * <p>
     * The order of the states are as follows: OPEN > MISS > HIT > OPEN. Calling
     * {@code stateChange} for a square that is {@code SUNK} has no effect.
     * <p>
     * The cache is recalculated after a state change.
     * <p>
     * @param x The X position.
     * @param y The Y position.
     */
    public void stateChange(int x, int y) {
        switch (board.getState(x, y)) {
            case OPEN:
                board.stateChange(x, y, Board.SquareState.MISS);
                break;
            case MISS:
                board.stateChange(x, y, Board.SquareState.HIT);
                break;
            case HIT:
                board.stateChange(x, y, Board.SquareState.OPEN);
                break;
        }

        recalculate();
    }

    /**
     * Recalculates the data in the cache.
     * <p>
     * If the cache is null, a new array is instantiated.
     */
    private void recalculate() {
        Integer[][] raw = board.getShipsMatrix();

        stateCache = board.getStatesMatrix();

        int max = 0;

        for (int i = 0; i < raw.length; i++) {
            for (int j = 0; j < raw[i].length; j++) {
                if (stateCache[i][j].equals(Board.SquareState.OPEN)) {
                    max = Math.max(raw[i][j], max);
                }
            }
        }

        if (resultCache == null) {
            resultCache = new double[board.getWidth()][board.getHeight()];
        }

        for (int i = 0; i < resultCache.length; i++) {
            for (int j = 0; j < resultCache[i].length; j++) {
                if (Double.compare(max, 0) == 0 || !stateCache[i][j].equals(Board.SquareState.OPEN)) {
                    resultCache[i][j] = 0;
                } else {
                    resultCache[i][j] = raw[i][j] * 1.0 / max;
                }
            }
        }

        maxResult = max;
    }
    //TODO implment the rest of the methods
}
