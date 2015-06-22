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
     * @return The numerical data to display
     */
    public double[][] getData() {
        Integer[][] raw = board.getShipsMatrix();

        int max = 0;

        for (Integer[] col : raw) {
            for (Integer cell : col) {
                max = Math.max(cell, max);
            }
        }

        double[][] returnData = new double[board.getWidth()][board.getHeight()];

        for (int i = 0; i < returnData.length; i++) {
            for (int j = 0; j < returnData[i].length; j++) {
                returnData[i][j] = raw[i][j] * 1.0 / max;
            }
        }

        return returnData;
        //Use a cache for results
    }

    /**
     * Returns the matrix for the states of the squares on the board.
     * <p>
     * @return The state matrix
     */
    public Board.SquareState[][] getStateData() {
        return board.getStatesMatrix();
    }

    /**
     * Changes the state of the particular square on the board.
     * <p>
     * The order of the states are as follows: OPEN > MISS > HIT > OPEN. Calling
     * {@code stateChange} for a square that is {@code SUNK} has no effect.
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
                board.stateChange(x, y, Board.SquareState.OPEN);
                break;
        }
    }

    //TODO implment the rest of the methods
}
