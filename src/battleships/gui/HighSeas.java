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
        if (x < 1 || y < 1) {
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
            //Calculates number of digits
            int maxDigits = (int) Math.max(Math.floor(Math.log10(xSqrs)) + 1, Math.floor(Math.log(ySqrs - 1) / Math.log(26) + 1));

            //Get max dimension of current font characters
            double maxBound = g2.getFontMetrics(g2.getFont()).getHeight();
            for (int w : g2.getFontMetrics().getWidths()) {
                maxBound = Math.max(maxBound, w);
            }

            //Scale font according to space availible
            double fontSize = sqrSize / (maxBound * maxDigits) * g2.getFont().getSize();
            g2.setFont(g2.getFont().deriveFont((float) fontSize));

            double xOffset = (getWidth() - 1 - (xSqrs + 1) * sqrSize) / 2;
            double yOffset = (getHeight() - 1 - (ySqrs + 1) * sqrSize) / 2;

            //Distance to bottom of text to placement position
            double fontLineOffset = g2.getFontMetrics().getMaxDescent() + g2.getFontMetrics().getLeading();

            for (int x = 1; x <= xSqrs + 1; x++) {
                double xSquareStart = x * sqrSize;

                if (x <= xSqrs) {
                    double labelWidth = g2.getFontMetrics().getStringBounds(Integer.toString(x), g2).getWidth();

                    double xRel = xSquareStart + (sqrSize - labelWidth) / 2;
                    //Resting on grid
                    double yRel = sqrSize - fontLineOffset;

                    g2.drawString(Integer.toString(x), (float) (xRel + xOffset), (float) (yRel + yOffset));
                }
                Line2D.Double xGrid = new Line2D.Double(xSquareStart + xOffset, sqrSize + yOffset, xSquareStart + xOffset, (ySqrs + 1) * sqrSize + yOffset);
                g2.draw(xGrid);
            }

            //Total height of font
            double fontHeight = g2.getFontMetrics().getHeight();

            for (int y = 1; y <= ySqrs + 1; y++) {
                double ySquareStart = y * sqrSize;

                if (y <= ySqrs) {
                    double fontX = g2.getFontMetrics().getStringBounds(Integer.toString(y), g2).getWidth();

                    double xRel = (sqrSize - fontX) / 2;
                    //Vertical Center
                    double yRel = ySquareStart + sqrSize / 2 + fontHeight / 2 - fontLineOffset;

                    g2.drawString(Integer.toString(y), (float) (xRel + xOffset), (float) (yRel + yOffset));
                }

                Line2D.Double rowLine = new Line2D.Double(sqrSize + xOffset, ySquareStart + yOffset, (xSqrs + 1) * sqrSize + xOffset, ySquareStart + yOffset);
                g2.draw(rowLine);
            }
        }
    }
}
