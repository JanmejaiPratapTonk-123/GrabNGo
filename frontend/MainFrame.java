package frontend;

import backend.controllers.KioskController;
import frontend.panels.KioskPanel;
import frontend.panels.SplashPanel;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainContainer = new JPanel(cardLayout);
    
    private final KioskController controller;
    private final KioskPanel kioskPanel;

    public MainFrame(KioskController controller) {
        this.controller = controller;
        
        setTitle("GrabNGo — Self-Service Kiosk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        SplashPanel splashPanel = new SplashPanel(this);
        kioskPanel = new KioskPanel(this, controller);

        mainContainer.add(splashPanel, "splash");
        mainContainer.add(kioskPanel, "kiosk");
        
        add(mainContainer);
        showSplash();
    }

    public void showSplash() {
        cardLayout.show(mainContainer, "splash");
    }

    public void showKiosk() {
        kioskPanel.reset();
        cardLayout.show(mainContainer, "kiosk");
    }
}
