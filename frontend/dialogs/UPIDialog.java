package frontend.dialogs;

import backend.controllers.KioskController;
import frontend.MainFrame;
import frontend.ui.QRCodePanel;
import frontend.ui.RoundedButton;
import frontend.ui.AppTheme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Dialog prompting the user to scan a QR code for UPI payment.
 */
public class UPIDialog extends JDialog {

    public UPIDialog(MainFrame mainFrame, KioskController controller) {
        super(mainFrame, "UPI Payment", true);

        setSize(300, 380);
        setLocationRelativeTo(mainFrame);
        getContentPane().setBackground(AppTheme.MC_WHITE);
        setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(AppTheme.MC_WHITE);
        content.setBorder(new EmptyBorder(20, 24, 20, 24));

        JLabel title = new JLabel("Scan to Pay");
        title.setFont(AppTheme.getFontBold(18));
        title.setForeground(AppTheme.TEXT_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel amt = new JLabel("₹" + (int) controller.getCartTotal());
        amt.setFont(AppTheme.getFontBold(28));
        amt.setForeground(AppTheme.MC_RED);
        amt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("GrabnGo Canteen • UPI");
        sub.setFont(AppTheme.getFontPlain(12));
        sub.setForeground(AppTheme.TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        QRCodePanel qr = new QRCodePanel();
        qr.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scanHint = new JLabel("Scan with any UPI app");
        scanHint.setFont(AppTheme.getFontPlain(12));
        scanHint.setForeground(AppTheme.TEXT_MUTED);
        scanHint.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton doneBtn = new RoundedButton(
            "Payment Done",
            new Color(59, 109, 17),
            new Color(50, 140, 20),
            new Color(30, 100, 10),
            30
        );
        doneBtn.setFont(AppTheme.getFontBold(14));
        doneBtn.setForeground(AppTheme.MC_WHITE);
        doneBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        doneBtn.addActionListener(e -> {
            dispose();
            try {
                var order = controller.checkout("UPI");
                new ConfirmationDialog(mainFrame, order.getToken(), "UPI");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Checkout failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        content.add(title);
        content.add(Box.createVerticalStrut(4));
        content.add(amt);
        content.add(Box.createVerticalStrut(2));
        content.add(sub);
        content.add(Box.createVerticalStrut(14));
        content.add(qr);
        content.add(Box.createVerticalStrut(10));
        content.add(scanHint);
        content.add(Box.createVerticalStrut(16));
        content.add(doneBtn);

        add(content, BorderLayout.CENTER);
        setVisible(true);
    }
}
