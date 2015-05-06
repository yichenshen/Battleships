/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.model;

/**
 * A basic implementation of a square on the board.
 *
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public class Square {

    private int x, y;

    /**
     * Setter constructor.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor.
     *
     * @param sqr The square to be copied
     */
    public Square(Square sqr) {
        x = sqr.getX();
        y = sqr.getY();
    }

    /**
     * Returns the x position of the square.
     *
     * @return x-coordinate position
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y position of the square.
     *
     * @return y-coordinate position.
     */
    public int getY() {
        return y;
    }

    /**
     * Offsets(moves) the position of the square by the given values.
     *
     * @param x x-position offset
     * @param y y-position offset
     */
    public void offset(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
