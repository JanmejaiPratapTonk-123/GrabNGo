package frontend.panels;

import frontend.MainFrame;
import frontend.ui.FoodIconPainter;
import frontend.ui.RoundedButton;
import frontend.ui.AppTheme;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The initial welcome screen for the application.
 */
public class SplashPanel extends JPanel {

    public SplashPanel(MainFrame mainFrame) {
        setBackground(AppTheme.MC_RED);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Logo icon (drawn burger + fork)
        JPanel logoIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();

                // Circle background
                g2.setColor(AppTheme.MC_YELLOW);
                g2.fillOval(0, 0, w, h);

                // Draw burger inside circle
                FoodIconPainter.drawFood(g2, "burger", w, h);

                // Fork on right side
                g2.setColor(AppTheme.MC_RED);
                g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(w - 22, h/2 - 20, w - 22, h/2 + 20);
                g2.drawLine(w - 26, h/2 - 20, w - 26, h/2 - 5);
                g2.drawLine(w - 18, h/2 - 20, w - 18, h/2 - 5);
                g2.drawLine(w - 26, h/2 - 5, w - 18, h/2 - 5);
                g2.setStroke(new BasicStroke(1));
                g2.dispose();
            }
        };
        logoIcon.setOpaque(false);
        logoIcon.setPreferredSize(new Dimension(130, 130));
        gbc.insets = new Insets(0, 0, 16, 0);
        add(logoIcon, gbc);

        // Brand name
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 4, 0);
        JLabel brand = new JLabel("GrabnGo");
        brand.setFont(AppTheme.getFontBold(64));
        brand.setForeground(AppTheme.MC_YELLOW);
        add(brand, gbc);

        // Tagline
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 50, 0);
        JLabel tagline = new JLabel("Order Fast. Eat Fresh.");
        tagline.setFont(AppTheme.getFontPlain(20));
        tagline.setForeground(new Color(255, 255, 255, 200));
        add(tagline, gbc);

        // Start Ordering button
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 40, 0);
        
        JButton startBtn = new RoundedButton(
            "  Start Ordering  ",
            AppTheme.MC_YELLOW,
            new Color(255, 210, 30),
            new Color(200, 160, 0),
            50
        );
        startBtn.setFont(AppTheme.getFontBold(22));
        startBtn.setForeground(AppTheme.MC_RED);
        startBtn.setPreferredSize(new Dimension(260, 60));
        startBtn.addActionListener(e -> mainFrame.showKiosk());
        add(startBtn, gbc);

        // Food icons row at bottom
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 10, 0);
        JPanel iconsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        iconsRow.setOpaque(false);
        
        String[] keys = {"burger","pizza","fries","soda","sandwich"};
        for (String key : keys) {
            JPanel iconBg = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 40));
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    g2.dispose();
                }
            };
            iconBg.setOpaque(false);
            iconBg.setPreferredSize(new Dimension(64, 64));
            
            FoodIconPainter icon = new FoodIconPainter(key, 64);
            iconBg.add(icon, BorderLayout.CENTER);
            iconsRow.add(iconBg);
        }
        add(iconsRow, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        // Dark red bottom wave accent
        g2.setColor(new Color(180, 0, 0));
        g2.fillArc(-100, h - 160, w + 200, 300, 0, 180);

        // Yellow accent arch (McDonald's M shape inspired)
        g2.setColor(AppTheme.MC_YELLOW);
        g2.setStroke(new BasicStroke(14, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(w/2 - 60, h/2 - 180, 50, 70, 0, -180);
        g2.drawArc(w/2 + 10, h/2 - 180, 50, 70, 0, -180);
        g2.setStroke(new BasicStroke(1));

        g2.dispose();
    }
}
