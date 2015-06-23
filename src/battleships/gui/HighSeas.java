/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.gui;

import battleships.model.Board;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 * A GUI panel for the game board display.
 * <p>
 * The panel automatically scales both labels and squares based on the number of
 * columns/rows and the width/height of the panel.
 * <p>
 * Call {@code setSquares(int cols, int rows)} to set the desired number of
 * columns and rows.
 * <p>
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public class HighSeas extends JPanel {

    /**
     * Maximum allowed size for a square.
     */
    static final double MAX_SQUARE_SIZE = 60;
    /**
     * The transparent colour.
     */
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    /**
     * The colour for overlay highlights
     */
    private static final Color OVERLAY = new Color(0, 0, 0, 0.25f);
    /**
     * The HSB brightness for square colouring.
     */
    private static final float BRIGHTNESS = 0.8f;
    /**
     * The HSB saturation for square colouring.
     */
    private static final float SATURATION = 1f;
    /**
     * The polynomial exponent for increasing hue.
     */
    private static final float HUE_SLOPE = 1.5f;

    /**
     * Number of columns for the grid.
     */
    private int cols = 0;
    /**
     * Number of rows for the grid.
     */
    private int rows = 0;
    /**
     * The size of a square on the grid.
     */
    private double sqrSize = MAX_SQUARE_SIZE;
    /**
     * The starting x position to draw the board (including labels) on.
     */
    private double xOffset = 0;
    /**
     * The starting y position to draw the board (including labels) on.
     */
    private double yOffset = 0;
    /**
     * The data of the board.
     * <p>
     * This determines the colouring of the squares corresponds to the
     * calculations of ships.
     */
    private double[][] data;
    /**
     * The states of the squares.
     * <p>
     * This determines additional markers places on top of squares.
     */
    private Board.SquareState[][] states;
    /**
     * The X position of the mouse in board coordinates.
     * <p>
     * This is used to determine the overlays for hover.
     */
    private int mouseGridX = -1;
    /**
     * The Y position of the mouse in board coordinates.
     * <p>
     * This is used to determine the overlays for hover.
     */
    private int mouseGridY = -1;

    /**
     * Create a new HighSeas Panel.
     */
    public HighSeas() {
    }

    /**
     * Gets the grid X-coordinate based on relative X pixel position.
     * <p>
     * Returns -1 if pixel position is outside the board.
     * <p>
     * @param x The pixel X position
     * @return The X-coordinate of the corresponding square or -1.
     */
    public int getGridX(double x) {
        int gridX = (int) (Math.floor(x - xOffset) / sqrSize) - 1;

        if (gridX < 0 || gridX >= cols) {
            gridX = -1;
        }

        return gridX;
    }

    /**
     * Gets the grid Y-coordinate based on relative Y pixel position.
     * <p>
     * Returns -1 if pixel position is outside the board.
     * <p>
     * @param y The pixel Y position
     * @return The Y-coordinate of the corresponding square or -1.
     */
    public int getGridY(double y) {
        int gridY = (int) (Math.floor(y - yOffset) / sqrSize) - 1;

        if (gridY < 0 || gridY >= rows) {
            gridY = -1;
        }

        return gridY;
    }

    /**
     * Sets the columns and rows for the grid.
     * <p>
     * Method will also cause the panel to refresh.
     * <p>
     * @param cols The number of columns of squares
     * @param rows The number of rows of squares
     * @see #refresh()
     */
    public void setSquares(int cols, int rows) {
        if (cols < 1 || rows < 1) {
            throw new IllegalArgumentException("There must be as least 1 row/column!");
        }

        this.cols = cols;
        this.rows = rows;

        refresh();
    }

    /**
     * Sets the given arrays as the data array and states array for the panel
     * respectively.
     * <p>
     * The data array is used to determine the colouring of cells on the panel.
     * The states array will determine special symbols to be displayed.
     * <p>
     * Note: This method does not make a copy of the array, and thus the array
     * may be modified else where.
     * <p>
     * @param newData   The new data to be represented.
     * @param newStates The new states of the board.
     */
    public void setData(double[][] newData, Board.SquareState[][] newStates) {
        data = newData;
        states = newStates;
        refresh();
    }

    /**
     * Sets the position of the mouse on the panel.
     * <p>
     * This grid it is hovering over is calculated, thus allowing the row and
     * column to be highlighted.
     * <p>
     * @param x
     * @param y
     */
    public void setMousePos(double x, double y) {
        int newX = getGridX(x);
        int newY = getGridY(y);

        if (mouseGridX != newX || mouseGridY != newY) {
            mouseGridX = newX;
            mouseGridY = newY;

            refresh();
        }
    }

    /**
     * Refreshes the board.
     * <p>
     * This method calculates the the suitable square size based on the current
     * height and width and calls {@code repaint()}.
     */
    public void refresh() {
        sqrSize = Math.min(MAX_SQUARE_SIZE, Math.min((getWidth() - 1) * 1.0 / (cols + 1), (getHeight() - 1) * 1.0 / (rows + 1)));

        xOffset = (getWidth() - 1 - (cols + 1) * sqrSize) / 2;
        yOffset = (getHeight() - 1 - (rows + 1) * sqrSize) / 2;

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    final Graphics2D g2 = (Graphics2D) g.create();

                    try {
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        Rectangle2D cell = new Rectangle2D.Double(sqrSize * (i + 1) + xOffset, sqrSize * (j + 1) + yOffset, sqrSize, sqrSize);

                        if (Double.compare(data[i][j], 0) == 0) {
                            g2.setPaint(TRANSPARENT);
                        } else {
                            g2.setPaint(Color.getHSBColor((float) (1f / 3 - Math.pow(data[i][j], HUE_SLOPE) / 3), SATURATION, BRIGHTNESS));
                        }

                        g2.fill(cell);

                        if (mouseGridX != -1 && mouseGridY != -1 && (i == mouseGridX || j == mouseGridY)) {
                            g2.setPaint(OVERLAY);
                            g2.fill(cell);
                        }

                        g2.setPaint(Color.BLACK);
                        g2.setStroke(new BasicStroke(3));
                        switch (states[i][j]) {
                            case MISS:
                                Ellipse2D mark = new Ellipse2D.Double(sqrSize * (i + 1) + xOffset + 2, sqrSize * (j + 1) + yOffset + 2, sqrSize - 4, sqrSize - 4);
                                g2.draw(mark);
                                break;
                        }
                    } finally {
                        g2.dispose();
                    }
                }
            }
        }

        final Graphics2D g2 = (Graphics2D) g.create();

        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            paintGrid(g2);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Draws a grid based on rows and columns on the panel.
     * <p>
     * @param g2 The graphics object to use for drawing.
     */
    private void paintGrid(Graphics2D g2) {
        if (cols > 0 && rows > 0) {
            //Calculates number of digits
            int maxDigits = (int) Math.max(Math.floor(Math.log10(cols)) + 1, Math.floor(Math.log(rows - 1) / Math.log(26) + 1));

            //Get max dimension of current font characters
            double maxBound = g2.getFontMetrics(g2.getFont()).getHeight();
            for (int w : g2.getFontMetrics().getWidths()) {
                maxBound = Math.max(maxBound, w);
            }

            //Scale font according to space availible
            double fontSize = sqrSize / (maxBound * maxDigits) * g2.getFont().getSize();
            g2.setFont(g2.getFont().deriveFont((float) fontSize));

            //Distance to bottom of text to placement position
            double fontLineOffset = g2.getFontMetrics().getMaxDescent() + g2.getFontMetrics().getLeading();

            for (int x = 1; x <= cols + 1; x++) {
                double xSquareStart = x * sqrSize;

                if (x <= cols) {
                    double labelWidth = g2.getFontMetrics().getStringBounds(Integer.toString(x), g2).getWidth();

                    double xRel = xSquareStart + (sqrSize - labelWidth) / 2;
                    //Resting on grid
                    double yRel = sqrSize - fontLineOffset;

                    g2.drawString(Integer.toString(x), (float) (xRel + xOffset), (float) (yRel + yOffset));
                }
                Line2D.Double xGrid = new Line2D.Double(xSquareStart + xOffset, sqrSize + yOffset, xSquareStart + xOffset, (rows + 1) * sqrSize + yOffset);
                g2.draw(xGrid);
            }

            //Total height of font
            double fontHeight = g2.getFontMetrics().getHeight();

            for (int y = 1; y <= rows + 1; y++) {
                double ySquareStart = y * sqrSize;

                if (y <= rows) {
                    double fontX = g2.getFontMetrics().getStringBounds(Integer.toString(y), g2).getWidth();

                    double xRel = (sqrSize - fontX) / 2;
                    //Vertical Center
                    double yRel = ySquareStart + sqrSize / 2 + fontHeight / 2 - fontLineOffset;

                    g2.drawString(Integer.toString(y), (float) (xRel + xOffset), (float) (yRel + yOffset));
                }

                Line2D.Double rowLine = new Line2D.Double(sqrSize + xOffset, ySquareStart + yOffset, (cols + 1) * sqrSize + xOffset, ySquareStart + yOffset);
                g2.draw(rowLine);
            }
        }
    }
}
