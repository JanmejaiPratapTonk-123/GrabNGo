package frontend.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Renders a mock QR code for UPI payments.
 */
public class QRCodePanel extends JPanel {
    
    public QRCodePanel() {
        setPreferredSize(new Dimension(180, 180));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawQR(g2);
        g2.dispose();
    }

    private void drawQR(Graphics2D g) {
        int size = 160;
        int off  = 10;
        int cell = size / 21;

        // Fake QR data pattern (simplified)
        int[][] pattern = {
            {1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,0,0},
            {1,0,0,0,0,0,1,0,0,1,0,1,0,1,0,0,0,0,1,1,0},
            {1,0,1,1,1,0,1,0,1,0,1,0,1,1,0,1,1,0,1,0,1},
            {1,0,1,1,1,0,1,0,0,1,0,1,0,1,0,1,1,0,1,1,0},
            {1,0,1,1,1,0,1,0,1,0,1,0,1,1,0,1,1,0,1,0,1},
            {1,0,0,0,0,0,1,0,0,1,0,1,0,1,0,0,0,0,1,1,0},
            {1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,0,1},
            {0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0},
            {1,0,1,1,0,1,1,1,0,0,1,0,1,1,0,1,0,1,1,0,1},
            {0,1,0,0,1,0,0,0,1,0,0,1,0,0,1,0,1,0,0,1,0},
            {1,0,1,0,1,0,1,1,0,1,1,0,1,0,1,0,1,1,0,1,1},
            {0,1,0,1,0,1,0,0,1,0,0,1,0,1,0,1,0,0,1,0,0},
            {1,1,1,0,1,1,1,1,0,1,1,0,1,1,1,0,1,1,1,0,1},
            {0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0},
            {1,1,1,1,1,1,1,0,0,1,1,0,1,0,1,1,0,1,1,0,1},
            {1,0,0,0,0,0,1,0,1,0,0,1,0,1,0,0,1,0,0,1,0},
            {1,0,1,1,1,0,1,0,0,1,1,0,1,1,0,1,1,1,0,1,1},
            {1,0,1,1,1,0,1,0,1,0,0,1,0,0,1,0,0,0,1,0,0},
            {1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1,1,1,0,1},
            {1,0,0,0,0,0,1,0,1,0,0,1,0,0,0,0,0,0,0,1,0},
            {1,1,1,1,1,1,1,0,0,1,1,0,1,0,1,1,0,1,1,0,1},
        };

        g.setColor(Color.BLACK);
        for (int row = 0; row < 21; row++) {
            for (int col = 0; col < 21; col++) {
                if (pattern[row][col] == 1) {
                    g.fillRect(off + col * cell, off + row * cell, cell - 1, cell - 1);
                }
            }
        }

        // Quiet zone border
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawRect(off - 2, off - 2, size + 3, size + 3);
    }
}
