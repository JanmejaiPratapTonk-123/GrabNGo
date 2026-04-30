package frontend.panels;

import backend.controllers.KioskController;
import backend.models.MenuItem;
import frontend.ui.FoodIconPainter;
import frontend.ui.RoundedButton;
import frontend.ui.AppTheme;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

/**
 * Displays the menu items and category filters.
 */
public class MenuPanel extends JPanel {

    private final KioskController controller;
    private final Runnable onCartUpdated;
    
    private final JPanel tabBar;
    private final JPanel grid;
    private String currentCategory = "All";
    
    private static final String[] CATEGORIES = {"All", "Snacks", "Drinks"};

    public MenuPanel(KioskController controller, Runnable onCartUpdated) {
        this.controller = controller;
        this.onCartUpdated = onCartUpdated;

        setLayout(new BorderLayout(0, 10));
        setBackground(AppTheme.MC_GRAY);
        setBorder(new EmptyBorder(0, 0, 0, 14));

        tabBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        tabBar.setBackground(AppTheme.MC_GRAY);
        buildTabs();
        add(tabBar, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(0, 3, 12, 12));
        grid.setBackground(AppTheme.MC_GRAY);
        grid.setBorder(new EmptyBorder(8, 0, 0, 0));
        
        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setBackground(AppTheme.MC_GRAY);
        scroll.getViewport().setBackground(AppTheme.MC_GRAY);
        add(scroll, BorderLayout.CENTER);

        loadMenu();
    }

    public void resetToAll() {
        this.currentCategory = "All";
        buildTabs();
        loadMenu();
    }

    private void buildTabs() {
        tabBar.removeAll();
        for (String cat : CATEGORIES) {
            tabBar.add(makeCategoryTab(cat));
        }
        tabBar.revalidate();
        tabBar.repaint();
    }

    private void loadMenu() {
        grid.removeAll();
        List<MenuItem> items = controller.getMenu(currentCategory);
        for (MenuItem item : items) {
            grid.add(buildMenuCard(item));
        }
        grid.revalidate();
        grid.repaint();
    }

    private JButton makeCategoryTab(String cat) {
        boolean active = cat.equals(currentCategory);
        
        JButton tab = new JButton(cat) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getText().equals(currentCategory)) {
                    g2.setColor(AppTheme.MC_RED);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                    g2.setColor(AppTheme.MC_RED);
                } else {
                    g2.setColor(AppTheme.MC_WHITE);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                    g2.setColor(AppTheme.BORDER);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        tab.setFont(AppTheme.getFontBold(13));
        tab.setForeground(active ? AppTheme.MC_WHITE : AppTheme.TEXT_MUTED);
        tab.setContentAreaFilled(false);
        tab.setBorderPainted(false);
        tab.setFocusPainted(false);
        tab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tab.setPreferredSize(new Dimension(90, 34));

        tab.addActionListener(e -> {
            currentCategory = cat;
            loadMenu();
            
            for (Component c : tabBar.getComponents()) {
                if (c instanceof JButton) {
                    ((JButton) c).setForeground(((JButton) c).getText().equals(currentCategory) ? AppTheme.MC_WHITE : AppTheme.TEXT_MUTED);
                }
                c.repaint();
            }
        });
        
        return tab;
    }

    private JPanel buildMenuCard(MenuItem item) {
        JPanel card = new JPanel(new BorderLayout(0, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.MC_WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
            new AbstractBorder() {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(AppTheme.BORDER);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRoundRect(x, y, w-1, h-1, 16, 16);
                    g2.dispose();
                }
                @Override
                public Insets getBorderInsets(Component c) { return new Insets(1,1,1,1); }
            },
            new EmptyBorder(0, 0, 0, 0)
        ));

        JPanel imageArea = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(item.getIconBg());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
            }
        };
        imageArea.setOpaque(false);
        imageArea.setPreferredSize(new Dimension(0, 110));

        FoodIconPainter icon = new FoodIconPainter(item.getDrawKey(), 90);
        icon.setOpaque(false);
        imageArea.add(icon, BorderLayout.CENTER);
        card.add(imageArea, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(8, 10, 10, 10));

        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(AppTheme.getFontBold(14));
        nameLabel.setForeground(AppTheme.TEXT_DARK);

        JLabel priceLabel = new JLabel("₹" + (int) item.getPrice());
        priceLabel.setFont(AppTheme.getFontPlain(13));
        priceLabel.setForeground(AppTheme.TEXT_MUTED);

        JButton addBtn = new RoundedButton(
            "+ Add to Order",
            AppTheme.MC_RED,
            new Color(200, 20, 20),
            new Color(180, 0, 0),
            20
        );
        addBtn.setFont(AppTheme.getFontBold(12));
        addBtn.setForeground(AppTheme.MC_WHITE);
        addBtn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        addBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        addBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        addBtn.addActionListener(e -> {
            controller.addToCart(item);
            onCartUpdated.run();
        });

        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        info.add(nameLabel);
        info.add(Box.createVerticalStrut(2));
        info.add(priceLabel);
        info.add(Box.createVerticalStrut(8));
        info.add(addBtn);

        card.add(info, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.addToCart(item);
                onCartUpdated.run();
            }
        });

        return card;
    }
}
