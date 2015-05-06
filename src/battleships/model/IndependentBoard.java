/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.model;

import java.util.*;

/**
 * An implementation of Board that calculates probability independently for each {@code Ship}.
 * <p>
 * This board attempts to fit each ship into all possible configurations. The total number is tallied individually for
 * each ship. Total probability is summed up linearly, treating each ship's probability as independent.
 * <p>
 * P{A &#8746; B} = P{A} + P{B} - P{A &#8745; B}
 * <p>
 * Note that this will ignore the fact that a placing of a certain ship invalidates certain positions of another ship .
 * As such there are limitations to the accuracies of the probability derived.
 * <p>
 * Using only the ship placement count map generated by {@code getShipsMap()} would generally be preferred.
 *
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @see Ship
 * @since v1.0.0
 */
public class IndependentBoard implements Board {

    /**
     * States of a square on the board.
     */
    public static enum SquareState {
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
         * When the sqaure is occupied by a sunken ship.
         */
        SUNK
    }

    private SquareState[][] board;
    private List<Ship> ships;
    private SortedMap<Integer, List<Square>> possibleShipConfigs;

    /**
     * Creates a board with given width and height.
     *
     * @param width  The board width in squares
     * @param height The board height in squares
     */
    public IndependentBoard(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Board width/height must be bigger than 0!");
        }
        board = new SquareState[width][];
        for (int i = 0; i < board.length; i++) {
            board[i] = new SquareState[height];
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = SquareState.OPEN;
            }
        }

        ships = new ArrayList<>();
        possibleShipConfigs = new TreeMap<>();
    }

    @Override
    public int getWidth() {
        return board.length;
    }

    @Override
    public int getHeight() {
        //Height is guaranteed to be at least 1
        return board[0].length;
    }

    @Override
    public double[][] getProbabilityMap() {
        return new double[0][];
    }

    @Override
    public double[][] getProbabilityMap(Ship ship) {
        return new double[0][];
    }

    @Override
    public int[][] getShipsMap() {
        return new int[0][];
    }

    @Override
    public int[][] getShipsMap(Ship ship) {
        return new int[0][];
    }

    @Override
    public void addShip(Ship ship) {
        ships.add(ship);
        genMap(ship);
    }

    @Override
    public Collection<Ship> getShips() {
        return null;
    }

    private void genMap(Ship ship) {
        Square shipSize = ship.getBottomRight();

        for (int x = 0; x < board.length - shipSize.getX() + 1; x++) {
            SquareState[] col = board[x];
            for (int y = 0; y < col.length - shipSize.getY() + 1; y++) {
                //Try each config
                //TODO account for rotation
                boolean fits = true;
                for (Square square : ship) {
                    int checkX = x + square.getX();
                    int checkY = y + square.getY();

                    //TODO Consider cases besides OPEN
                    fits &= board[checkX][checkY] == SquareState.OPEN;
                }
                //Add to config list if config is a valid fit
                //TODO add to count matrix
                //TODO set up reverse and ship reference maps
                if (fits) {
                    int newKey = possibleShipConfigs.lastKey() + 1;
                    List<Square> coords = new ArrayList<>(ship.numSquares());
                    for (Square square : ship) {
                        int checkX = x + square.getX();
                        int checkY = y + square.getY();

                        coords.add(new Square(checkX, checkY));
                    }
                    possibleShipConfigs.put(newKey, coords);
                }
            }
        }
    }
}