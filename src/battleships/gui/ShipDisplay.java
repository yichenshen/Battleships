/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.gui;

import battleships.model.Square;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (ship != null && rows > 0 && cols > 0) {

            final Graphics2D g2 = (Graphics2D) g.create();

            try {
                //Draw grid
                double sqrSize = Math.min((getWidth() - 1) * 1.0 / (cols + 1), (getHeight() - 1) * 1.0 / (rows + 1));

                double xOffset = (getWidth() - sqrSize * cols) / 2;
                double yOffset = (getHeight() - sqrSize * rows) / 2;

                for (int x = 1; x <= cols; x++) {
                    double xSquareStart = x * sqrSize;

                    Line2D.Double xGrid = new Line2D.Double(xSquareStart + xOffset, sqrSize + yOffset, xSquareStart + xOffset, (rows + 1) * sqrSize + yOffset);
                    g2.draw(xGrid);
                }

                for (int y = 1; y <= rows; y++) {
                    double ySquareStart = y * sqrSize;

                    Line2D.Double yGrid = new Line2D.Double(sqrSize + xOffset, ySquareStart + yOffset, (cols + 1) * sqrSize + xOffset, ySquareStart + yOffset);
                    g2.draw(yGrid);
                }
                //End of draw grid

            } finally {
                g2.dispose();
            }
        }

    }

}
