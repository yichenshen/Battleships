/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
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
     * Create a new HighSeas Panel.
     */
    public HighSeas() {
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
     * Refreshes the board.
     * <p>
     * This method calculates the the suitable square size based on the current
     * height and width and calls {@code repaint()}.
     */
    public void refresh() {
        sqrSize = Math.min(MAX_SQUARE_SIZE, Math.min((getWidth() - 1) * 1.0 / (cols + 1), (getHeight() - 1) * 1.0 / (rows + 1)));

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        paintGrid(g2);
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

            double xOffset = (getWidth() - 1 - (cols + 1) * sqrSize) / 2;
            double yOffset = (getHeight() - 1 - (rows + 1) * sqrSize) / 2;

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
