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
public class Square implements Comparable<Square> {

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

    /**
     * Rotates the square 90 degrees clockwise.
     */
    public void rotateCW() {
        int oldX = this.x;

        //noinspection SuspiciousNameCombination
        this.x = this.y;
        this.y = -1 * oldX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Square square = (Square) o;

        return getX() == square.getX() && getY() == square.getY();
    }

    @Override
    public String toString() {
        return "Square{"
                + "x=" + x
                + ", y=" + y
                + '}';
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }

    @Override
    public int compareTo(Square o) {
        return (x == o.getX()) ? (y - o.getY()) : (x - o.getX());
    }
}
