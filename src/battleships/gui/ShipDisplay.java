/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */

package battleships.gui;

import battleships.model.Square;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

    private int gridWidth;
    private int gridHeight;
    private Iterable<Square> ship;

    /**
     * Creates a new ShipDisplay.
     */
    public ShipDisplay() {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (ship != null && gridHeight > 0 && gridWidth > 0) {

            final Graphics2D g2 = (Graphics2D) g.create();

            try {

            } finally {
                g2.dispose();
            }
        }

    }

}
