/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.gui;

import battleships.model.Square;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 * The JPanel which displays the ship.
 * <p>
 * This class is a panel that displays a ship which it is passed to. The grid is
 * resized and scaled automatically based on it's size and the squares of the
 * ship.
 * <p>
 * @author Shen Yichen <2007.yichen@gmail.com>
 * @since v1.0.0
 */
public class ShipDisplay extends JPanel {

    private int cols;
    private int rows;
    private Iterable<Square> ship;

    /**
     * Creates a new ShipDisplay.
     */
    public ShipDisplay() {
    }

    /**
     * Displays the list of squares in the list provided.
     * <p>
     * This function sets the variables accordingly and then repaints the panel
     * <p>
     * @param squareList The list of square to display
     */
    public void display(Iterable<Square> squareList) {
        this.ship = squareList;

        cols = 0;
        rows = 0;

        squareList.forEach((sqr) -> {
            cols = Math.max(cols, sqr.getX() + 1);
            rows = Math.max(rows, sqr.getY() + 1);
        });

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (ship != null && rows > 0 && cols > 0) {
            double sqrSize = Math.min((getWidth() - 2) * 1.0 / cols, (getHeight() - 2) * 1.0 / rows);

            double xOffset = (getWidth() - sqrSize * cols) / 2;
            double yOffset = (getHeight() - sqrSize * rows) / 2;

            Graphics2D g2 = (Graphics2D) g.create();

            try {
                g2.setPaint(Color.DARK_GRAY);
                for (Square sqr : ship) {
                    Rectangle2D sqrShape = new Rectangle2D.Double(xOffset + sqr.getX() * sqrSize, yOffset + sqr.getY() * sqrSize, sqrSize, sqrSize);

                    g2.fill(sqrShape);
                }
            } finally {
                g2.dispose();
            }

            //Draw grid
            g2 = (Graphics2D) g.create();

            try {
                g2.setPaint(Color.GRAY);
                for (int x = 0; x <= cols; x++) {
                    double xSquareStart = x * sqrSize;

                    Line2D.Double xGrid = new Line2D.Double(xSquareStart + xOffset, yOffset, xSquareStart + xOffset, rows * sqrSize + yOffset);
                    g2.draw(xGrid);
                }

                for (int y = 0; y <= rows; y++) {
                    double ySquareStart = y * sqrSize;

                    Line2D.Double yGrid = new Line2D.Double(xOffset, ySquareStart + yOffset, cols * sqrSize + xOffset, ySquareStart + yOffset);
                    g2.draw(yGrid);
                }
            } finally {
                g2.dispose();
            }
            //End of draw grid
        }
    }
}
