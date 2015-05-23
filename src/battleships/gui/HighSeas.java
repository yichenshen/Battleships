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
 *
 * @author Shen Yichen <2007.yichen@gmail.com>
 */
public class HighSeas extends JPanel {

    static final double MAX_SQUARE_SIZE = 60;

    private int xSqrs = 0;
    private int ySqrs = 0;
    private double sqrSize = MAX_SQUARE_SIZE;

    public HighSeas() {

    }

    public void setSquares(int x, int y) {
        if (x < 1 || y < 0) {
            throw new IllegalArgumentException("There must be as least 1 row/column!");
        }

        xSqrs = x;
        ySqrs = y;

        refresh();
    }

    public void refresh() {
        sqrSize = Math.min(MAX_SQUARE_SIZE, Math.min((getWidth() - 1) * 1.0 / (xSqrs + 1), (getHeight() - 1) * 1.0 / (ySqrs + 1)));

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        if (xSqrs > 0 && ySqrs > 0) {
            for (int x = 1; x <= xSqrs + 1; x++) {
                Line2D.Double xGrid = new Line2D.Double(x * sqrSize, sqrSize, x * sqrSize, (ySqrs + 1) * sqrSize);
                g2.draw(xGrid);
            }

            for (int y = 1; y <= ySqrs + 1; y++) {
                Line2D.Double xGrid = new Line2D.Double(sqrSize, y * sqrSize, (xSqrs + 1) * sqrSize, y * sqrSize);
                g2.draw(xGrid);
            }
        }

    }

}
