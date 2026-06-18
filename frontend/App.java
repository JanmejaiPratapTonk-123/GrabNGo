package frontend;

import backend.controllers.KioskController;


import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
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
