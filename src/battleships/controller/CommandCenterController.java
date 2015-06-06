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

    public int getBoardHeight() {
        return board.getHeight();
    }

    public int getBoardWidth() {
        return board.getWidth();
    }

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
    //TODO implment the rest of the methods
}
