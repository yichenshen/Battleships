/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */
package battleships.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * An implementation of Board that calculates probability independently for each
 * {@code Ship}.
 * <p>
 * This board attempts to fit each ship into all possible configurations. The
 * total number is tallied individually for each ship. Total probability is
 * summed up linearly, treating each ship's probability as independent.
 * <p>
 * P{A &#8746; B} = P{A} + P{B} - P{A &#8745; B}
 * <p>
 * Note that this will ignore the fact that a placing of a certain ship
 * invalidates certain positions of another ship . As such there are limitations
 * to the accuracies of the probability derived.
 * <p>
 * Using only the ship placement count map generated by {@code getShipsMatrix()}
 * would generally be preferred.
 * <p>
 * This class stores a list of configurations of each ship that fits into the
 * board. This list is a permanent property of the board, included regardless if
 * they clash with invalid squares. If a configuration is invalid, it's marked
 * inactive and counters adjusted accordingly; it will not be removed from the
 * list. The same goes with the various mappings that links these configurations
 * to the board.
 * <p>
 * This allows the totals and probabilities to be recalculated when the state of
 * a square changes without having to regenerate the configurations.
 * <p>
 * Square coordinates in this class are 0 indexed.
 * <p>
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @see Ship
 * @since v1.0.0
 */
public class IndependentBoard implements Board {

    // <editor-fold desc="Class variables">
    /**
     * For storing the state of the board.
     */
    private SquareState[][] board;
    /**
     * Stores the list of ships.
     */
    private Collection<Ship> ships;
    /**
     * Stores the possible configurations of all ships.
     * <p>
     * Each configuration is given an incremental ID.
     */
    private SortedMap<Integer, Collection<Square>> possibleShipConfigs;
    /**
     * Stores a boolean for each configuration to denote if it's active.
     */
    private Map<Integer, Boolean> configActive;
    /**
     * Stores a link from a square to a configuration that affects it.
     */
    private ArrayList<ArrayList<Collection<Integer>>> reverseMap;
    /**
     * Stores a link from ship to configurations involving it.
     */
    private Map<Ship, Collection<Integer>> shipToConfigID;
    /**
     * Stores boards of possible configurations on each square, one for each
     * ship.
     */
    private Map<Ship, Integer[][]> shipCounter;
    /**
     * Stores the total number of configurations for each ship.
     */
    private Map<Ship, Integer> totalCounter;
    /**
     * Stores the configuration for the position of a sunken ship.
     */
    private Map<Ship, Collection<Square>> sunkMap;
    // </editor-fold>

    /**
     * Creates a board with given width and height.
     * <p>
     * @param width The board width in squares
     * @param height The board height in squares
     */
    public IndependentBoard(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Board width/height must be bigger than 0!");
        }

        board = new SquareState[width][];
        reverseMap = new ArrayList<>(width);
        shipToConfigID = new HashMap<>();

        for (int i = 0; i < board.length; i++) {
            board[i] = new SquareState[height];
            reverseMap.add(new ArrayList<>(height));

            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = SquareState.OPEN;
                //Using array to store reverse mapping indices, change implementation here
                reverseMap.get(i).add(new ArrayList<>());
            }
        }

        shipCounter = new HashMap<>();
        totalCounter = new HashMap<>();

        ships = new ArrayList<>();
        possibleShipConfigs = new TreeMap<>();
        configActive = new HashMap<>();
        sunkMap = new HashMap<>();
    }

    @Override
    public int getWidth() {
        return board.length;
    }

    @Override
    public int getHeight() {
        //Width is guaranteed to be at least 1
        return board[0].length;
    }

    /**
     * Gets the probability matrix for all ships on this board.
     * <p>
     * Each square on the board is given a probability that any ship may occupy
     * that square.
     * <p>
     * Warning: Overall probabilities calculated by this board is linear, and
     * are not exact! It is preferred to use {@code getShipsMatrix()} in this
     * case.
     * <p>
     * @return Probability matrix
     * @see #getShipsMatrix()
     */
    @Override
    public Double[][] getProbabilityMatrix() {
        Double[][] summedProbMatrix = new Double[getWidth()][getHeight()];

        for (Double[] summedProbMatrixCol : summedProbMatrix) {
            for (int j = 0; j < summedProbMatrixCol.length; j++) {
                summedProbMatrixCol[j] = 0.0;
            }
        }

        for (Ship ship : ships) {
            boardMapper(summedProbMatrix,
                    ship,
                    (int newVal, Double orgVal, int total) -> {
                        double newProb = total > 0 ? (double) newVal / total : 0;
                        return orgVal + newProb - orgVal * newProb;
                    });
        }
        //P(A or B) = P(A) + P(B) - P(A and B)
        //P(A and B) = P(A)P(B) as we are assuming they are independent

        return summedProbMatrix;
    }

    @Override
    public Double[][] getProbabilityMatrix(Ship ship) {
        Double[][] probMatrix = new Double[getWidth()][getHeight()];

        boardMapper(probMatrix, ship, (int newVal, Double orgVal, int total) -> total > 0 ? ((double) newVal) / total : 0);

        return probMatrix;
    }

    @Override
    public Integer[][] getShipsMatrix() {
        Integer[][] sumMatrix = new Integer[getWidth()][getHeight()];

        for (Integer[] sumMatrixCol : sumMatrix) {
            for (int j = 0; j < sumMatrixCol.length; j++) {
                sumMatrixCol[j] = 0;
            }
        }

        ships.stream().forEach((ship) -> {
            boardMapper(sumMatrix, ship, (int newVal, Integer orgVal, int total) -> orgVal + newVal);
        });

        return sumMatrix;
    }

    @Override
    public Integer[][] getShipsMatrix(Ship ship) {
        Integer[][] copyMatrix = new Integer[getWidth()][getHeight()];

        boardMapper(copyMatrix, ship, (int newVal, Integer orgVal, int total) -> newVal);

        return copyMatrix;
    }

    @Override
    public SquareState getState(int x, int y) {
        return board[x][y];
    }

    @Override
    public SquareState[][] getStatesMatrix() {
        SquareState[][] copyMatrix = new SquareState[getWidth()][getHeight()];

        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copyMatrix[i], 0, board.length);
        }

        return copyMatrix;
    }

    @Override
    public void addShip(Ship ship) {
        ships.add(ship);

        Integer[][] counterMatrix = new Integer[getWidth()][getHeight()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                counterMatrix[i][j] = 0;
            }
        }
        shipCounter.put(ship, counterMatrix);
        totalCounter.put(ship, 0);
        shipToConfigID.put(ship, new ArrayList<>());
        genMap(ship);
    }

    @Override
    public Collection<Ship> getShips() {
        return new ArrayList<>(ships);
    }

    @Override
    public boolean shipWithinBoard(Ship ship, int x, int y) {
        return x >= 0 && x < getWidth() - ship.getMaxSquare().getX()
                && y >= 0 && y < getHeight() - ship.getMaxSquare().getY();
    }

    @Override
    public boolean shipWithinBoard(Ship ship, Square sqr) {
        return shipWithinBoard(ship, sqr.getX(), sqr.getY());
    }

    @Override
    public void stateChange(int x, int y, SquareState newState) {

        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException("Coordinates out-of-bound: (" + x + ", " + y + ")");
        }

        switch (newState) {
            case MISS: {
                disable(x, y);
                break;
            }

            case HIT:
            case OPEN: {
                //Need to set to OPEN for check config
                board[x][y] = SquareState.OPEN;
                enable(x, y);
                break;
                //TODO add other
                //Hit: similiar to miss
                //Sunk: error
                //Open: do nothing
            }
        }

        //If new state is not to be assigned, an IllegalStateException should be thrown before this.
        //TODO after everything is implemented, relook this line
        board[x][y] = newState;
        //TODO add in other states
    }

    @Override
    public boolean sink(Ship ship, int rotateCW, int x, int y) {
        if (!ships.contains(ship)) {
            throw new IllegalArgumentException("No such ship!");
        }

        Ship rotatedShip = ship.rotateCWNinety(rotateCW);

        boolean sinkable = true;
        for (Iterator<Square> it = rotatedShip.iterator(); it.hasNext() && sinkable;) {
            Square sqr = it.next();
            int absX = sqr.getX() + x;
            int absY = sqr.getY() + y;

            if (absX >= 0 && absX < getWidth() && absY >= 0 && absY < getHeight()) {
                sinkable &= board[absX][absY].equals(SquareState.HIT);
            } else {
                sinkable = false;
            }
        }

        if (sinkable) {
            Collection<Square> sunkPos = new ArrayList<>();
            for (Square sqr : rotatedShip) {
                int absX = sqr.getX() + x;
                int absY = sqr.getY() + y;

                //Disable required to deactivate configs of other ships
                board[absX][absY] = SquareState.SUNK;
                sunkPos.add(new Square(absX, absY));

                disable(absX, absY);
            }

            sunkMap.put(ship, sunkPos);

            Collection<Integer> configList = shipToConfigID.get(ship);

            configList.forEach((id) -> configActive.put(id, Boolean.FALSE));

            totalCounter.put(ship, 0);

            Integer[][] countMatrix = shipCounter.get(ship);

            for (Integer[] row : countMatrix) {
                for (int j = 0; j < row.length; j++) {
                    row[j] = 0;
                }
            }
        }

        return sinkable;
    }

    @Override
    public void raise(Ship ship) {
        //TODO only unsink at sunk position instead
        if (!sunkMap.containsKey(ship)) {
            throw new IllegalArgumentException("No such sunken ship!");
        }

        sunkMap.get(ship).stream().forEach((sqr) -> board[sqr.getX()][sqr.getY()] = SquareState.HIT);

        Collection<Integer> configList = shipToConfigID.get(ship);

        configList.forEach((id) -> {
            Iterable<Square> config = possibleShipConfigs.get(id);
            if (checkConfig(config)) {
                configActive.put(id, Boolean.TRUE);

                config.forEach(
                        (Square sqr) -> {
                            shipCounter.get(ship)[sqr.getX()][sqr.getY()]++;
                        }
                );

                totalCounter.put(ship, totalCounter.get(ship) + 1);
            }
        });

        //TODO implement test
    }

    /**
     * Check if the ship could fit onto the board starting at the specified
     * position, without interference from obstacles.
     * <p>
     * @param ship The ship
     * @param x The starting x-position
     * @param y The starting y-position
     * @return {@code true} if the ship can fit, {@code false} if the ship
     * cannot fit
     */
    protected boolean checkConfig(Ship ship, int x, int y) {
        boolean fits = shipWithinBoard(ship, x, y);

        if (fits) {
            for (Square square : ship) {
                int checkX = x + square.getX();
                int checkY = y + square.getY();

                fits &= (board[checkX][checkY] == SquareState.OPEN || board[checkX][checkY] == SquareState.HIT);
            }
        }

        return fits;
        //TODO define in terms of function below
    }

    /**
     * Checks if the given configuration is within the board and is clear of any
     * obstacles.
     * <p>
     * @param config The {@code Iterable} of squares that makes up the
     * configuration.
     * @return {@code true} if the configuration can fit, {@code false} if the
     * configuration cannot fit
     */
    protected boolean checkConfig(Iterable<Square> config) {
        boolean fits = true;

        for (Square square : config) {
            int x = square.getX();
            int y = square.getY();

            fits &= (x >= 0 && x < getWidth() && y >= 0 && y < getHeight());

            fits &= (board[x][y] == SquareState.OPEN || board[x][y] == SquareState.HIT);
        }

        return fits;
    }

    /**
     * Maps ship count values to a computed value stored in resultMatrix.
     * <p>
     * This method takes in an matrix and loops through each of the cells of the
     * board, setting the value of the provided matrix with the return value of
     * the function provided.
     * <p>
     * @param resultMatrix Matrix to operate on (must be the same size as the
     * board!)
     * @param ship The ship to map for
     * @param folder Function that takes in (boardCellData, originalMatrixData,
     * shipTotalCount) and returns a new value to assign to the matrix.
     * @param <T> The data type of the matrix cells
     */
    protected <T> void boardMapper(T[][] resultMatrix, Ship ship, ShipFold<T> folder) {
        Integer[][] shipMatrix = shipCounter.get(ship);

        for (int i = 0; i < shipMatrix.length; i++) {
            Integer[] col = shipMatrix[i];

            for (int j = 0; j < col.length; j++) {
                resultMatrix[i][j] = folder.fold(col[j], resultMatrix[i][j], totalCounter.get(ship));
            }
        }
    }

    /**
     * Adds a new configuration to the map, using the squares on ship.
     * <p>
     * This method throws an {@code IllegalArgumentException} if the rotated
     * ship is out of the board.
     * <p>
     * If the config overlaps illegal squares e.g. {@code SquareState.MISS}, it
     * will be marked as inactive, if not it'll remain active. Only active
     * squares will cause {@code shipCounter} and {@code totalCounter} to
     * increment.
     * <p>
     * You should supply both the original ship, and the ship that has been
     * rotated. The original ship will be used for mapping and the rotated to
     * set up positions.
     * <p>
     * Note that this method will add a config given to it as long as it fits,
     * and will set it to active as long as it is valid given current
     * circumstances. It will not check for duplicates of configs. Thus it is
     * possible to add 2 identical configs and they will both contribute to
     * counts.
     * <p>
     * @param orgShip The ship for mapping
     * @param shipRotated The ship, rotated for the config
     * @param x The starting x position of the config
     * @param y The starting y position of the config
     * @throws IllegalArgumentException If ship is out of board or
     * {@code orgShip} is invalid.
     */
    void addConfig(Ship orgShip, Ship shipRotated, int x, int y) {
        if (!ships.contains(orgShip)) {
            throw new IllegalArgumentException("Unknown original ship supplied");
        }

        //Check if ship is within board
        if (!shipWithinBoard(shipRotated, x, y)) {
            throw new IllegalArgumentException(String.format(
                    "Ship is not contained within board!\n(x, y): (%d, %d)\nAccepted Range => x: [0, %d), y: [0, %d)",
                    x,
                    y,
                    getWidth() - shipRotated.getMaxSquare().getX(),
                    getHeight() - shipRotated.getMaxSquare().getY()));
        }

        int key;
        if (possibleShipConfigs.isEmpty()) {
            key = 1;
        } else {
            key = possibleShipConfigs.lastKey() + 1;
        }

        Collection<Square> coords = new ArrayList<>(shipRotated.numSquares());

        boolean active = checkConfig(shipRotated, x, y);

        for (Square square : shipRotated) {
            int checkX = x + square.getX();
            int checkY = y + square.getY();

            //Add coords to list
            coords.add(new Square(checkX, checkY));
            //Reverse Map
            reverseMap.get(checkX).get(checkY).add(key);
            //Add to square count
            if (active) {
                shipCounter.get(orgShip)[checkX][checkY]++;
            }
        }

        possibleShipConfigs.put(key, coords);
        shipToConfigID.get(orgShip).add(key);

        //Set configActive and totalCounter if ship is active
        if (active) {
            totalCounter.put(orgShip, totalCounter.get(orgShip) + 1);
            configActive.put(key, Boolean.TRUE);
        } else {
            configActive.put(key, Boolean.FALSE);
        }
    }

    /**
     * Enables the square, changing all configs that overlap to active.
     * <p>
     * Also increments the counters accordingly.
     * <p>
     * @param x The X-coordinate of the square
     * @param y The Y-coordinate of the square
     */
    private void enable(int x, int y) {
        Collection<Integer> affectedConfig = reverseMap.get(x).get(y);

        affectedConfig.forEach(
                (Integer id) -> {
                    //Ship of this config
                    Ship ship = getShipOfConfig(id);
                    Collection<Square> config = possibleShipConfigs.get(id);

                    if (!configActive.get(id) && !sunkMap.containsKey(ship) && checkConfig(config)) {
                        configActive.put(id, Boolean.TRUE);

                        config.forEach(
                                (Square sqr) -> {
                                    shipCounter.get(ship)[sqr.getX()][sqr.getY()]++;
                                }
                        );

                        totalCounter.put(ship, totalCounter.get(ship) + 1);
                    }
                }
        );
    }

    /**
     * Disable the square, setting all configs that overlaps the square to
     * inactive.
     * <p>
     * Also decrements the counters accordingly.
     * <p>
     * @param x The X-coordinate of the square
     * @param y The Y-coordinate of the square
     */
    private void disable(int x, int y) {
        Collection<Integer> affectedConfig = reverseMap.get(x).get(y);

        affectedConfig.forEach(
                (Integer id) -> {
                    if (configActive.get(id)) {
                        //Ship of this config
                        Ship ship = getShipOfConfig(id);

                        configActive.put(id, Boolean.FALSE);

                        Collection<Square> config = possibleShipConfigs.get(id);

                        config.forEach(
                                (Square sqr) -> {
                                    shipCounter.get(ship)[sqr.getX()][sqr.getY()]--;
                                }
                        );

                        //Decrement total configs for ship
                        totalCounter.put(ship, totalCounter.get(ship) - 1);
                    }
                }
        );
    }

    /**
     * Generates mapping for a given ship.
     * <p>
     * This method adds 4 mappings.
     * <p>
     * One giving an incremental index to a fitted configuration coordinate
     * list.
     * <p>
     * One matching squares that are affected by this configuration to the
     * index.
     * <p>
     * One that matches ships to the index.
     * <p>
     * And finally one that denotes if the configuration is active.
     * <p>
     * The counter are incremented for active configurations. Make sure
     * {@code shipCounter} and {@code totalCounter} are initialised for the ship
     * before calling.
     * <p>
     * @param ship Ship to generate mapping for.
     */
    private void genMap(Ship ship) {
        Ship rotatedShip = ship;

        for (int i = 0; i < 4; i++) {
            //Just consider all, let checkConfig figure out if ship is outside board
            for (int x = 0; x < getWidth() - rotatedShip.getMaxSquare().getX(); x++) {
                for (int y = 0; y < getHeight() - rotatedShip.getMaxSquare().getY(); y++) {
                    //Config still added even if it conflicts with board elements, but marked as inactive
                    addConfig(ship, rotatedShip, x, y);
                }
            }

            //Rotate the ship and try again
            rotatedShip = rotatedShip.rotateCWNinety(1);
        }
    }

    /**
     * Returns the ship which the configuration as specified by {@code configID}
     * is related to.
     * <p>
     * @param configID The ID of the configuration for matching.
     * @return The matching ship.
     */
    private Ship getShipOfConfig(int configID) {
        return ships.stream()
                .filter(
                        (Ship ship) -> {
                            return shipToConfigID.get(ship).stream().anyMatch((Integer id) -> id == configID);
                        }
                )
                .findFirst()
                .orElse(null);

    }

    /**
     * Functional interface for collating output matrices.
     * <p>
     * @param <T> The type of an output matrix cell
     */
    @FunctionalInterface
    protected interface ShipFold<T> {

        T fold(int nextVal, T retVal, int divisor);
    }
}
