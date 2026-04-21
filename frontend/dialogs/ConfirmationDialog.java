package frontend.dialogs;

import frontend.MainFrame;
import frontend.ui.RoundedButton;
import frontend.ui.AppTheme;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Screen showing token and confirmation of placed order.
 */
public class ConfirmationDialog extends JDialog {

    public ConfirmationDialog(MainFrame mainFrame, int token, String method) {
        super(mainFrame, "Order Confirmed!", true);

        setSize(320, 300);
        setLocationRelativeTo(mainFrame);
        getContentPane().setBackground(AppTheme.MC_WHITE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(AppTheme.MC_WHITE);
        content.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Green checkmark circle
        JPanel check = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(59, 109, 17));
                g2.fillOval(10, 10, 50, 50);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(22, 36, 30, 44);
                g2.drawLine(30, 44, 48, 26);
                g2.setStroke(new BasicStroke(1));
                g2.dispose();
            }
        };
        check.setOpaque(false);
        check.setPreferredSize(new Dimension(70, 70));
        check.setMaximumSize(new Dimension(70, 70));
        check.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel placed = new JLabel("Order Placed!");
        placed.setFont(AppTheme.getFontBold(20));
        placed.setForeground(new Color(59, 109, 17));
        placed.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tokenNum = new JLabel(String.valueOf(token));
        tokenNum.setFont(AppTheme.getFontBold(60));
        tokenNum.setForeground(AppTheme.MC_RED);
        tokenNum.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tokenSub = new JLabel("Token Number");
        tokenSub.setFont(AppTheme.getFontPlain(13));
        tokenSub.setForeground(AppTheme.TEXT_MUTED);
        tokenSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel methodLbl = new JLabel("Paid via " + method);
        methodLbl.setFont(AppTheme.getFontPlain(12));
        methodLbl.setForeground(AppTheme.TEXT_MUTED);
        methodLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newOrder = new RoundedButton(
            "New Order",
            AppTheme.MC_RED,
            new Color(200, 20, 20),
            new Color(180, 0, 0),
            30
        );
        newOrder.setFont(AppTheme.getFontBold(14));
        newOrder.setForeground(AppTheme.MC_WHITE);
        newOrder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        newOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        newOrder.addActionListener(e -> {
            dispose();
            mainFrame.showSplash(); // Returning to splash starts a new order
        });

        content.add(check);
        content.add(Box.createVerticalStrut(8));
        content.add(placed);
        content.add(Box.createVerticalStrut(4));
        content.add(tokenNum);
        content.add(tokenSub);
        content.add(Box.createVerticalStrut(2));
        content.add(methodLbl);
        content.add(Box.createVerticalStrut(18));
        content.add(newOrder);

        add(content, BorderLayout.CENTER);
        setVisible(true);
    }
}
