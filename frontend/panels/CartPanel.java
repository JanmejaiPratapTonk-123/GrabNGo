package frontend.panels;

import backend.controllers.KioskController;
import backend.models.CartItem;
import frontend.MainFrame;
import frontend.ui.RoundedButton;
import frontend.dialogs.PaymentDialog;
import frontend.ui.AppTheme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CartPanel extends JPanel {

    private final MainFrame mainFrame;
    private final KioskController controller;
    
    private final JPanel cartItemsPanel;
    private final JLabel totalLabel;
    private final JButton checkoutButton;

    public CartPanel(MainFrame mainFrame, KioskController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;

        setLayout(new BorderLayout(0, 10));
        setOpaque(false);
        setPreferredSize(new Dimension(270, 0));
        setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel cartHeader = new JPanel(new BorderLayout());
        cartHeader.setOpaque(false);
        JLabel title = new JLabel("Your Order");
        title.setFont(AppTheme.getFontBold(17));
        title.setForeground(AppTheme.TEXT_DARK);
        JLabel bagIcon = new JLabel("🛍");
        bagIcon.setFont(AppTheme.getFontPlain(18));
        cartHeader.add(bagIcon, BorderLayout.WEST);
        cartHeader.add(title, BorderLayout.CENTER);
        add(cartHeader, BorderLayout.NORTH);

        cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane(cartItemsPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        add(scroll, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setOpaque(false);

        JSeparator sep = new JSeparator();
        sep.setForeground(AppTheme.BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        footer.add(sep);
        footer.add(Box.createVerticalStrut(10));

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        JLabel totalTxt = new JLabel("Order Total");
        totalTxt.setFont(AppTheme.getFontPlain(13));
        totalTxt.setForeground(AppTheme.TEXT_MUTED);
        
        totalLabel = new JLabel("₹0");
        totalLabel.setFont(AppTheme.getFontBold(22));
        totalLabel.setForeground(AppTheme.TEXT_DARK);
        
        totalRow.add(totalTxt, BorderLayout.WEST);
        totalRow.add(totalLabel, BorderLayout.EAST);
        footer.add(totalRow);
        footer.add(Box.createVerticalStrut(12));

        checkoutButton = new RoundedButton(
            "Checkout",
            AppTheme.MC_RED,
            new Color(200, 20, 20),
            new Color(180, 0, 0),
            30
        );
        checkoutButton.setFont(AppTheme.getFontBold(16));
        checkoutButton.setForeground(AppTheme.MC_WHITE);
        checkoutButton.setEnabled(false);
        checkoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        
        checkoutButton.addActionListener(e -> {
            new PaymentDialog(mainFrame, controller);
        });
        
        footer.add(checkoutButton);
        add(footer, BorderLayout.SOUTH);

        refreshCart();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(AppTheme.MC_WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
        g2.dispose();
    }

    public void refreshCart() {
        cartItemsPanel.removeAll();
        
        if (controller.isCartEmpty()) {
            JLabel empty = new JLabel("Add items to get started");
            empty.setFont(AppTheme.getFontPlain(13));
            empty.setForeground(AppTheme.TEXT_MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            cartItemsPanel.add(Box.createVerticalStrut(30));
            cartItemsPanel.add(empty);
        } else {
            for (CartItem ci : controller.getCartItems()) {
                cartItemsPanel.add(buildCartRow(ci));
                cartItemsPanel.add(Box.createVerticalStrut(6));
            }
        }
        
        totalLabel.setText("₹" + (int) controller.getCartTotal());
        checkoutButton.setEnabled(!controller.isCartEmpty());
        
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();
    }

    private JPanel buildCartRow(CartItem ci) {
        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        row.setPreferredSize(new Dimension(0, 36));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill   = GridBagConstraints.VERTICAL;
        gbc.gridy  = 0;
        gbc.insets = new Insets(0, 0, 0, 0);

        JLabel name = new JLabel(ci.getMenuItem().getName());
        name.setFont(AppTheme.getFontPlain(13));
        name.setForeground(AppTheme.TEXT_DARK);
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        row.add(name, gbc);

        JPanel qty = new JPanel(new GridLayout(1, 3, 3, 0));
        qty.setOpaque(false);

        JButton minus = buildQtyBtn("−", BTN);
        minus.addActionListener(e -> {
            controller.removeFromCart(ci.getMenuItem().getName());
            refreshCart();
        });

        JLabel num = new JLabel(String.valueOf(ci.getQuantity()), SwingConstants.CENTER);
        num.setFont(AppTheme.getFontBold(13));
        num.setForeground(AppTheme.TEXT_DARK);
        num.setHorizontalAlignment(SwingConstants.CENTER);
        num.setVerticalAlignment(SwingConstants.CENTER);
        num.setPreferredSize(new Dimension(BTN, BTN));
        num.setMinimumSize(new Dimension(BTN, BTN));
        num.setMaximumSize(new Dimension(BTN, BTN));

        JButton plus = buildQtyBtn("+", BTN);
        plus.addActionListener(e -> {
            controller.addToCart(ci.getMenuItem());
            refreshCart();
        });

        qty.add(minus);
        qty.add(num);
        qty.add(plus);

        int stripW = BTN * 3 + 3 * 2;
        qty.setPreferredSize(new Dimension(stripW, BTN));
        qty.setMinimumSize(new Dimension(stripW, BTN));
        qty.setMaximumSize(new Dimension(stripW, BTN));

        gbc.gridx   = 1;
        gbc.weightx = 0;
        gbc.fill    = GridBagConstraints.NONE;
        gbc.insets  = new Insets(0, 4, 0, 4);
        row.add(qty, gbc);

        JLabel price = new JLabel("₹" + (int) ci.getSubtotal());
        price.setFont(AppTheme.getFontPlain(13));
        price.setForeground(AppTheme.TEXT_MUTED);
        price.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx  = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        row.add(price, gbc);

        return row;
    }

    private JButton buildQtyBtn(String text, int size) {
        JButton b = new JButton(text);
        b.setFont(new Font(AppTheme.FONT_FAMILY, Font.BOLD, 13));
        b.setHorizontalAlignment(SwingConstants.CENTER);
        b.setVerticalAlignment(SwingConstants.CENTER);
        b.setMargin(new Insets(0, 0, 0, 0));
        b.setPreferredSize(new Dimension(size, size));
        b.setMinimumSize(new Dimension(size, size));
        b.setMaximumSize(new Dimension(size, size));
        b.setFocusPainted(false);
        b.setBackground(AppTheme.MC_GRAY);
        b.setForeground(AppTheme.TEXT_DARK);
        b.setBorder(new LineBorder(AppTheme.BORDER, 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
