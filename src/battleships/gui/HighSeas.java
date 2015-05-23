/*
 * Copyright (c) 2015. Shen Yichen <2007.yichen@gmail.com>
 * Under The MIT License.
 */
package battleships.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Stack;
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

        int maxDigits = (int) Math.max(Math.floor(Math.log10(xSqrs)) + 1, Math.floor(Math.log(ySqrs - 1) / Math.log(26) + 1));

        double maxBound = g2.getFontMetrics(g2.getFont()).getHeight();

        for (int w : g2.getFontMetrics().getWidths()) {
            maxBound = Math.max(maxBound, w);
        }

        double fontSize = sqrSize / (maxBound * maxDigits) * g2.getFont().getSize();
        g2.setFont(g2.getFont().deriveFont((float) fontSize));

        if (xSqrs > 0 && ySqrs > 0) {

            double fontY = g2.getFontMetrics().getMaxDescent() + g2.getFontMetrics().getLeading();
            double fontX;

            for (int x = 1; x <= xSqrs + 1; x++) {
                if (x <= xSqrs) {
                    fontX = g2.getFontMetrics().getStringBounds(Integer.toString(x), g2).getWidth();

                    g2.drawString(Integer.toString(x), (float) (x * sqrSize + (sqrSize - fontX) / 2), (float) (sqrSize - fontY));
                }
                Line2D.Double xGrid = new Line2D.Double(x * sqrSize, sqrSize, x * sqrSize, (ySqrs + 1) * sqrSize);
                g2.draw(xGrid);
            }

            double fontYHeight = g2.getFontMetrics().getHeight();

            for (int y = 1; y <= ySqrs + 1; y++) {
                if (y <= ySqrs) {
                    fontX = g2.getFontMetrics().getStringBounds(Integer.toString(y), g2).getWidth();

                    g2.drawString(Integer.toString(y), (float) ((sqrSize - fontX) / 2), (float) (sqrSize * (y + 1) - (sqrSize - fontYHeight) / 2 - fontY));
                }

                Line2D.Double xGrid = new Line2D.Double(sqrSize, y * sqrSize, (xSqrs + 1) * sqrSize, y * sqrSize);
                g2.draw(xGrid);
            }
        }
    }
}
