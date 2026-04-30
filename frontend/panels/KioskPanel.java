package frontend.panels;

import backend.controllers.KioskController;
import frontend.MainFrame;
import frontend.dialogs.OrderHistoryDialog;
import frontend.ui.FoodIconPainter;
import frontend.ui.AppTheme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The main container for the order interface.
 * Contains the Top Navbar, Menu Grid, and Shopping Cart.
 */
public class KioskPanel extends JPanel {

    private final KioskController controller;
    private final MainFrame mainFrame;
    private MenuPanel menuPanel;
    private CartPanel cartPanel;

    public KioskPanel(MainFrame mainFrame, KioskController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(AppTheme.MC_GRAY);

        add(buildNavbar(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 0));
        body.setBackground(AppTheme.MC_GRAY);
        body.setBorder(new EmptyBorder(14, 14, 14, 14));

        cartPanel = new CartPanel(mainFrame, controller);
        menuPanel = new MenuPanel(controller, cartPanel::refreshCart);

        body.add(menuPanel, BorderLayout.CENTER);
        body.add(cartPanel, BorderLayout.EAST);
        
        add(body, BorderLayout.CENTER);
    }

    /**
     * Resets the ordering view.
     */
    public void reset() {
        menuPanel.resetToAll();
        cartPanel.refreshCart();
    }

    private JPanel buildNavbar() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(AppTheme.MC_RED);
        nav.setBorder(new EmptyBorder(0, 20, 0, 20));
        nav.setPreferredSize(new Dimension(0, 62));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JPanel miniLogo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.MC_YELLOW);
                g2.fillOval(0, 0, 36, 36);
                FoodIconPainter.drawFood(g2, "burger", 36, 36);
                g2.dispose();
            }
        };
        miniLogo.setOpaque(false);
        miniLogo.setPreferredSize(new Dimension(36, 36));

        JLabel brandLabel = new JLabel("GrabnGo");
        brandLabel.setFont(AppTheme.getFontBold(26));
        brandLabel.setForeground(AppTheme.MC_YELLOW);

        left.add(miniLogo);
        left.add(brandLabel);
        nav.add(left, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        right.setOpaque(false);

        JButton historyBtn = new JButton("📋 Order History");
        historyBtn.setFont(AppTheme.getFontBold(13));
        historyBtn.setForeground(AppTheme.MC_YELLOW);
        historyBtn.setContentAreaFilled(false);
        historyBtn.setBorderPainted(false);
        historyBtn.setFocusPainted(false);
        historyBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        historyBtn.addActionListener(e -> {
            new OrderHistoryDialog(mainFrame, controller);
        });

        JButton homeBtn = new JButton("← Back to Home");
        homeBtn.setFont(AppTheme.getFontPlain(13));
        homeBtn.setForeground(new Color(255, 220, 220));
        homeBtn.setContentAreaFilled(false);
        homeBtn.setBorderPainted(false);
        homeBtn.setFocusPainted(false);
        homeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        homeBtn.addActionListener(e -> {
            controller.clearCart();
            mainFrame.showSplash();
        });

        right.add(historyBtn);
        right.add(homeBtn);
        nav.add(right, BorderLayout.EAST);

        return nav;
    }
}

