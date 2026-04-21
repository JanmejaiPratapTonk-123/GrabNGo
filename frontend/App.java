package frontend;

import backend.controllers.KioskController;


import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Single entry point for the GrabNGo application.
 */
public class App {
    public static void main(String[] args) {
        // Use system look and feel for a bit nicer default styling
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            KioskController controller = new KioskController();
            MainFrame frame = new MainFrame(controller);
            frame.setVisible(true);
        });
    }
}
