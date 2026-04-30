package frontend.dialogs;

import backend.controllers.KioskController;
import frontend.MainFrame;
import frontend.ui.RoundedButton;
import frontend.ui.AppTheme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PaymentDialog extends JDialog {

    private final MainFrame mainFrame;
    private final KioskController controller;
    private boolean upiSelected = false;

    public PaymentDialog(MainFrame mainFrame, KioskController controller) {
        super(mainFrame, "Payment", true);
        this.mainFrame = mainFrame;
        this.controller = controller;

        setSize(380, 280);
        setLocationRelativeTo(mainFrame);
        getContentPane().setBackground(AppTheme.MC_WHITE);
        setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(AppTheme.MC_WHITE);
        content.setBorder(new EmptyBorder(24, 28, 24, 28));

        JLabel heading = new JLabel("Choose payment method");
        heading.setFont(AppTheme.getFontBold(17));
        heading.setForeground(AppTheme.TEXT_DARK);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel totalLbl = new JLabel("Total: ₹" + (int) controller.getCartTotal());
        totalLbl.setFont(AppTheme.getFontPlain(14));
        totalLbl.setForeground(AppTheme.TEXT_MUTED);
        totalLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel optRow = new JPanel(new GridLayout(1, 2, 12, 0));
        optRow.setOpaque(false);
        optRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        optRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JButton cashBtn = buildPayOptionBtn("💵  Cash");
        JButton upiBtn  = buildPayOptionBtn("📱  UPI");

        cashBtn.addActionListener(e -> {
            upiSelected = false;
            cashBtn.setBackground(AppTheme.MC_LIGHT_RED);
            cashBtn.setBorder(BorderFactory.createLineBorder(AppTheme.MC_RED, 2));
            upiBtn.setBackground(AppTheme.MC_WHITE);
            upiBtn.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER, 1));
        });
        
        upiBtn.addActionListener(e -> {
            upiSelected = true;
            upiBtn.setBackground(AppTheme.MC_LIGHT_RED);
            upiBtn.setBorder(BorderFactory.createLineBorder(AppTheme.MC_RED, 2));
            cashBtn.setBackground(AppTheme.MC_WHITE);
            cashBtn.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER, 1));
        });

        optRow.add(cashBtn);
        optRow.add(upiBtn);

        JButton payBtn = new RoundedButton(
            "Pay Now",
            AppTheme.MC_RED,
            new Color(200, 20, 20),
            new Color(180, 0, 0),
            30
        );
        payBtn.setFont(AppTheme.getFontBold(15));
        payBtn.setForeground(AppTheme.MC_WHITE);
        payBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        payBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        payBtn.addActionListener(e -> {
            if (!cashBtn.getBackground().equals(AppTheme.MC_LIGHT_RED) && 
                !upiBtn.getBackground().equals(AppTheme.MC_LIGHT_RED)) {
                JOptionPane.showMessageDialog(this, "Please select a payment method.", "Select Payment", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dispose();
            
            if (upiSelected) {
                new UPIDialog(mainFrame, controller);
            } else {
                processCheckout("Cash");
            }
        });

        content.add(heading);
        content.add(Box.createVerticalStrut(4));
        content.add(totalLbl);
        content.add(Box.createVerticalStrut(20));
        content.add(optRow);
        content.add(Box.createVerticalStrut(20));
        content.add(payBtn);

        add(content, BorderLayout.CENTER);
        setVisible(true);
    }

    private void processCheckout(String method) {
        try {
            var order = controller.checkout(method);
            new ConfirmationDialog(mainFrame, order.getToken(), order.getPaymentMethod());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "Checkout failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton buildPayOptionBtn(String label) {
        JButton btn = new JButton(label);
        btn.setFont(AppTheme.getFontBold(14));
        btn.setForeground(AppTheme.TEXT_DARK);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBackground(AppTheme.MC_WHITE);
        btn.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER, 1));
        btn.setPreferredSize(new Dimension(0, 52));
        return btn;
    }
}
