import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

// ===================== MODEL =====================

class MenuItem {
    String name;
    double price;
    String category;
    Color iconBg;
    // Each item has a unique food drawing key
    String drawKey;

    MenuItem(String name, double price, String category, Color iconBg, String drawKey) {
        this.name     = name;
        this.price    = price;
        this.category = category;
        this.iconBg   = iconBg;
        this.drawKey  = drawKey;
    }
}

class CartItem {
    MenuItem menuItem;
    int quantity;
    CartItem(MenuItem m) { this.menuItem = m; this.quantity = 1; }
    double getSubtotal() { return menuItem.price * quantity; }
}

// ===================== SERVICE =====================

class CartService {
    private LinkedHashMap<String, CartItem> cartMap = new LinkedHashMap<>();
    private static int tokenCounter = 100;

    public void addItem(MenuItem item) {
        if (cartMap.containsKey(item.name)) cartMap.get(item.name).quantity++;
        else cartMap.put(item.name, new CartItem(item));
    }
    public void removeItem(String name) {
        CartItem ci = cartMap.get(name);
        if (ci != null) { ci.quantity--; if (ci.quantity <= 0) cartMap.remove(name); }
    }
    public Collection<CartItem> getCartItems() { return cartMap.values(); }
    public double getTotal() { return cartMap.values().stream().mapToDouble(CartItem::getSubtotal).sum(); }
    public boolean isEmpty() { return cartMap.isEmpty(); }
    public int generateToken() { return tokenCounter++; }
    public void clearCart() { cartMap.clear(); }
}

// ===================== FOOD ICON PAINTER =====================

class FoodIconPainter extends JPanel {
    private String key;
    FoodIconPainter(String key, int size) {
        this.key = key;
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        drawFood(g2, key, w, h);
        g2.dispose();
    }

    static void drawFood(Graphics2D g2, String key, int w, int h) {
        switch (key) {
            case "burger":   drawBurger(g2, w, h);   break;
            case "pizza":    drawPizza(g2, w, h);     break;
            case "sandwich": drawSandwich(g2, w, h);  break;
            case "samosa":   drawSamosa(g2, w, h);    break;
            case "fries":    drawFries(g2, w, h);     break;
            case "noodles":  drawNoodles(g2, w, h);   break;
            case "chai":     drawChai(g2, w, h);      break;
            case "coffee":   drawCoffee(g2, w, h);    break;
            case "juice":    drawJuice(g2, w, h);     break;
            case "lassi":    drawLassi(g2, w, h);     break;
            case "soda":     drawSoda(g2, w, h);      break;
            case "water":    drawWater(g2, w, h);     break;
        }
    }

    static void drawBurger(Graphics2D g, int w, int h) {
        // Top bun
        g.setColor(new Color(210, 140, 50));
        g.fillArc(w/2-30, h/2-28, 60, 36, 0, 180);
        g.setColor(new Color(230, 160, 70));
        g.fillRect(w/2-30, h/2-12, 60, 8);
        // Lettuce
        g.setColor(new Color(80, 180, 80));
        g.fillRect(w/2-32, h/2-4, 64, 7);
        // Patty
        g.setColor(new Color(110, 60, 20));
        g.fillRoundRect(w/2-28, h/2+3, 56, 9, 6, 6);
        // Bottom bun
        g.setColor(new Color(210, 140, 50));
        g.fillRoundRect(w/2-30, h/2+12, 60, 12, 10, 10);
        // Sesame seeds
        g.setColor(new Color(245, 220, 140));
        g.fillOval(w/2-10, h/2-22, 5, 3);
        g.fillOval(w/2+4, h/2-24, 5, 3);
        g.fillOval(w/2-2, h/2-18, 4, 3);
    }

    static void drawPizza(Graphics2D g, int w, int h) {
        // Slice shape
        int[] xp = {w/2, w/2-30, w/2+30};
        int[] yp = {h/2-28, h/2+24, h/2+24};
        g.setColor(new Color(220, 160, 60));
        g.fillPolygon(xp, yp, 3);
        // Crust
        g.setColor(new Color(200, 140, 80));
        g.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(w/2-30, h/2+24, w/2+30, h/2+24);
        g.setStroke(new BasicStroke(1));
        // Sauce
        g.setColor(new Color(210, 60, 40));
        int[] xs = {w/2, w/2-22, w/2+22};
        int[] ys = {h/2-18, h/2+18, h/2+18};
        g.fillPolygon(xs, ys, 3);
        // Cheese
        g.setColor(new Color(245, 200, 60));
        g.fillOval(w/2-8, h/2-4, 16, 12);
        g.fillOval(w/2-18, h/2+4, 12, 10);
        g.fillOval(w/2+4, h/2+6, 12, 10);
        // Toppings
        g.setColor(new Color(160, 40, 40));
        g.fillOval(w/2-4, h/2, 7, 7);
        g.fillOval(w/2+6, h/2+10, 6, 6);
        g.fillOval(w/2-14, h/2+10, 6, 6);
    }

    static void drawSandwich(Graphics2D g, int w, int h) {
        // Bottom bread
        g.setColor(new Color(220, 170, 80));
        g.fillRoundRect(w/2-32, h/2+10, 64, 14, 8, 8);
        // Filling layers
        g.setColor(new Color(80, 160, 80));
        g.fillRect(w/2-30, h/2+4, 60, 7);
        g.setColor(new Color(230, 80, 60));
        g.fillRect(w/2-30, h/2-2, 60, 7);
        g.setColor(new Color(240, 220, 100));
        g.fillRect(w/2-30, h/2-8, 60, 7);
        // Top bread
        g.setColor(new Color(220, 170, 80));
        g.fillRoundRect(w/2-32, h/2-22, 64, 16, 10, 10);
        g.setColor(new Color(200, 140, 60));
        g.fillArc(w/2-30, h/2-26, 60, 14, 0, 180);
    }

    static void drawSamosa(Graphics2D g, int w, int h) {
        // Triangle body
        int[] xs = {w/2, w/2-28, w/2+28};
        int[] ys = {h/2-28, h/2+22, h/2+22};
        g.setColor(new Color(210, 160, 70));
        g.fillPolygon(xs, ys, 3);
        // Shading
        g.setColor(new Color(180, 120, 40));
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(xs, ys, 3);
        g.setStroke(new BasicStroke(1));
        // Crease lines
        g.setColor(new Color(240, 190, 100));
        g.drawLine(w/2, h/2-28, w/2, h/2+22);
        // Texture dots
        g.setColor(new Color(180, 120, 40));
        g.fillOval(w/2-10, h/2, 4, 4);
        g.fillOval(w/2+5, h/2+4, 4, 4);
        g.fillOval(w/2-4, h/2+10, 4, 4);
    }

    static void drawFries(Graphics2D g, int w, int h) {
        // Box
        g.setColor(new Color(220, 40, 40));
        g.fillRoundRect(w/2-22, h/2+2, 44, 24, 6, 6);
        g.setColor(new Color(255, 220, 0));
        g.fillRect(w/2-18, h/2+6, 36, 4);
        // Fries sticks
        g.setColor(new Color(245, 200, 80));
        int[][] fries = {{w/2-14,h/2-28},{w/2-6,h/2-30},{w/2+2,h/2-26},{w/2+10,h/2-29},{w/2+18,h/2-27}};
        for (int[] f : fries) {
            g.fillRoundRect(f[0], f[1], 6, 34, 3, 3);
        }
        g.setColor(new Color(220, 160, 50));
        for (int[] f : fries) {
            g.drawRoundRect(f[0], f[1], 6, 34, 3, 3);
        }
    }

    static void drawNoodles(Graphics2D g, int w, int h) {
        // Bowl
        g.setColor(new Color(240, 220, 190));
        g.fillArc(w/2-30, h/2-8, 60, 36, 180, 180);
        g.setColor(new Color(210, 180, 140));
        g.fillRect(w/2-30, h/2-8, 60, 10);
        // Broth
        g.setColor(new Color(200, 140, 60));
        g.fillArc(w/2-26, h/2-4, 52, 28, 180, 180);
        // Noodle waves
        g.setColor(new Color(245, 220, 150));
        g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int row = 0; row < 3; row++) {
            int y = h/2 + 2 + row * 5;
            GeneralPath wave = new GeneralPath();
            wave.moveTo(w/2-20, y);
            for (int i = 0; i < 4; i++) wave.quadTo(w/2-12+i*10, y-4, w/2-6+i*10, y);
            g.draw(wave);
        }
        g.setStroke(new BasicStroke(1));
    }

    static void drawChai(Graphics2D g, int w, int h) {
        // Cup
        g.setColor(new Color(180, 100, 40));
        int[] cx = {w/2-18, w/2+18, w/2+14, w/2-14};
        int[] cy = {h/2-14, h/2-14, h/2+22, h/2+22};
        g.fillPolygon(cx, cy, 4);
        // Tea liquid
        g.setColor(new Color(150, 80, 20));
        g.fillRect(w/2-14, h/2-2, 28, 14);
        // Saucer
        g.setColor(new Color(220, 180, 120));
        g.fillOval(w/2-22, h/2+20, 44, 8);
        // Steam
        g.setColor(new Color(200, 200, 200, 180));
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        GeneralPath s1 = new GeneralPath();
        s1.moveTo(w/2-6, h/2-16); s1.quadTo(w/2-10, h/2-24, w/2-6, h/2-30);
        g.draw(s1);
        GeneralPath s2 = new GeneralPath();
        s2.moveTo(w/2+6, h/2-16); s2.quadTo(w/2+10, h/2-24, w/2+6, h/2-30);
        g.draw(s2);
        g.setStroke(new BasicStroke(1));
    }

    static void drawCoffee(Graphics2D g, int w, int h) {
        // Mug body
        g.setColor(new Color(80, 50, 20));
        g.fillRoundRect(w/2-20, h/2-16, 40, 38, 8, 8);
        // Coffee surface
        g.setColor(new Color(140, 90, 40));
        g.fillOval(w/2-16, h/2-14, 32, 10);
        // Foam
        g.setColor(Color.WHITE);
        g.fillOval(w/2-10, h/2-14, 20, 7);
        // Handle
        g.setColor(new Color(80, 50, 20));
        g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawArc(w/2+16, h/2-4, 16, 18, -30, -120);
        g.setStroke(new BasicStroke(1));
        // Steam
        g.setColor(new Color(200, 200, 200, 180));
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        GeneralPath s = new GeneralPath();
        s.moveTo(w/2, h/2-18); s.quadTo(w/2-6, h/2-26, w/2, h/2-32);
        g.draw(s);
        g.setStroke(new BasicStroke(1));
    }

    static void drawJuice(Graphics2D g, int w, int h) {
        // Glass
        int[] gx = {w/2-16, w/2+16, w/2+12, w/2-12};
        int[] gy = {h/2-18, h/2-18, h/2+22, h/2+22};
        g.setColor(new Color(180, 230, 255, 120));
        g.fillPolygon(gx, gy, 4);
        g.setColor(new Color(80, 160, 200));
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(gx, gy, 4);
        g.setStroke(new BasicStroke(1));
        // Juice liquid
        int[] lx = {w/2-14, w/2+14, w/2+10, w/2-10};
        int[] ly = {h/2-4, h/2-4, h/2+20, h/2+20};
        g.setColor(new Color(255, 160, 40, 200));
        g.fillPolygon(lx, ly, 4);
        // Orange slice
        g.setColor(new Color(255, 140, 20));
        g.fillOval(w/2+8, h/2-24, 16, 16);
        g.setColor(new Color(255, 200, 100));
        g.fillArc(w/2+8, h/2-24, 16, 16, 30, 120);
        g.fillArc(w/2+8, h/2-24, 16, 16, 210, 120);
        // Straw
        g.setColor(new Color(220, 60, 60));
        g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(w/2+4, h/2+20, w/2+10, h/2-26);
        g.setStroke(new BasicStroke(1));
    }

    static void drawLassi(Graphics2D g, int w, int h) {
        // Glass
        g.setColor(new Color(245, 235, 210));
        int[] gx = {w/2-18, w/2+18, w/2+14, w/2-14};
        int[] gy = {h/2-18, h/2-18, h/2+22, h/2+22};
        g.fillPolygon(gx, gy, 4);
        g.setColor(new Color(200, 180, 140));
        g.setStroke(new BasicStroke(1.5f));
        g.drawPolygon(gx, gy, 4);
        g.setStroke(new BasicStroke(1));
        // Froth
        g.setColor(Color.WHITE);
        g.fillOval(w/2-14, h/2-22, 28, 10);
        g.fillOval(w/2-10, h/2-24, 10, 8);
        g.fillOval(w/2+2, h/2-24, 10, 8);
        // Liquid
        g.setColor(new Color(240, 220, 180));
        g.fillRect(w/2-14, h/2-14, 28, 34);
        // Rose petals
        g.setColor(new Color(230, 100, 120));
        g.fillOval(w/2-4, h/2-10, 8, 6);
        g.fillOval(w/2+2, h/2-6, 6, 5);
    }

    static void drawSoda(Graphics2D g, int w, int h) {
        // Can body
        g.setColor(new Color(200, 30, 30));
        g.fillRoundRect(w/2-16, h/2-20, 32, 42, 8, 8);
        // Can top
        g.setColor(new Color(180, 180, 180));
        g.fillOval(w/2-16, h/2-24, 32, 10);
        // Tab
        g.setColor(new Color(200, 200, 200));
        g.fillRoundRect(w/2-4, h/2-24, 8, 6, 3, 3);
        // Label white stripe
        g.setColor(Color.WHITE);
        g.fillRect(w/2-13, h/2-8, 26, 10);
        // Logo text placeholder
        g.setColor(new Color(200, 30, 30));
        g.setFont(new Font("SansSerif", Font.BOLD, 8));
        g.drawString("SODA", w/2-10, h/2);
        // Bottom rim
        g.setColor(new Color(180, 180, 180));
        g.fillOval(w/2-16, h/2+18, 32, 8);
    }

    static void drawWater(Graphics2D g, int w, int h) {
        // Bottle body
        g.setColor(new Color(180, 220, 255, 200));
        g.fillRoundRect(w/2-14, h/2-10, 28, 34, 10, 10);
        // Bottle neck
        g.setColor(new Color(180, 220, 255, 200));
        g.fillRoundRect(w/2-8, h/2-22, 16, 14, 6, 6);
        // Cap
        g.setColor(new Color(0, 160, 220));
        g.fillRoundRect(w/2-7, h/2-26, 14, 8, 4, 4);
        // Water label
        g.setColor(Color.WHITE);
        g.fillRect(w/2-10, h/2-4, 20, 18);
        g.setColor(new Color(0, 120, 200));
        g.setFont(new Font("SansSerif", Font.BOLD, 7));
        g.drawString("WATER", w/2-10, h/2+6);
        // Bubbles
        g.setColor(new Color(200, 240, 255, 180));
        g.fillOval(w/2-6, h/2+2, 4, 4);
        g.fillOval(w/2+2, h/2+8, 3, 3);
        g.fillOval(w/2-2, h/2+14, 5, 5);
    }
}

// ===================== QR CODE PANEL =====================

class QRCodePanel extends JPanel {
    QRCodePanel() {
        setPreferredSize(new Dimension(180, 180));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 2));
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawQR(g2);
        g2.dispose();
    }

    private void drawQR(Graphics2D g) {
        int size = 160;
        int off  = 10;
        int cell = size / 21;

        // Fake QR data pattern (simplified)
        int[][] pattern = {
            {1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,0,0},
            {1,0,0,0,0,0,1,0,0,1,0,1,0,1,0,0,0,0,1,1,0},
            {1,0,1,1,1,0,1,0,1,0,1,0,1,1,0,1,1,0,1,0,1},
            {1,0,1,1,1,0,1,0,0,1,0,1,0,1,0,1,1,0,1,1,0},
            {1,0,1,1,1,0,1,0,1,0,1,0,1,1,0,1,1,0,1,0,1},
            {1,0,0,0,0,0,1,0,0,1,0,1,0,1,0,0,0,0,1,1,0},
            {1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,0,1},
            {0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0},
            {1,0,1,1,0,1,1,1,0,0,1,0,1,1,0,1,0,1,1,0,1},
            {0,1,0,0,1,0,0,0,1,0,0,1,0,0,1,0,1,0,0,1,0},
            {1,0,1,0,1,0,1,1,0,1,1,0,1,0,1,0,1,1,0,1,1},
            {0,1,0,1,0,1,0,0,1,0,0,1,0,1,0,1,0,0,1,0,0},
            {1,1,1,0,1,1,1,1,0,1,1,0,1,1,1,0,1,1,1,0,1},
            {0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0},
            {1,1,1,1,1,1,1,0,0,1,1,0,1,0,1,1,0,1,1,0,1},
            {1,0,0,0,0,0,1,0,1,0,0,1,0,1,0,0,1,0,0,1,0},
            {1,0,1,1,1,0,1,0,0,1,1,0,1,1,0,1,1,1,0,1,1},
            {1,0,1,1,1,0,1,0,1,0,0,1,0,0,1,0,0,0,1,0,0},
            {1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1,1,1,0,1},
            {1,0,0,0,0,0,1,0,1,0,0,1,0,0,0,0,0,0,0,1,0},
            {1,1,1,1,1,1,1,0,0,1,1,0,1,0,1,1,0,1,1,0,1},
        };

        g.setColor(Color.BLACK);
        for (int row = 0; row < 21; row++) {
            for (int col = 0; col < 21; col++) {
                if (pattern[row][col] == 1) {
                    g.fillRect(off + col * cell, off + row * cell, cell - 1, cell - 1);
                }
            }
        }

        // Quiet zone border
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawRect(off - 2, off - 2, size + 3, size + 3);
    }
}

// ===================== SPLASH SCREEN =====================

class SplashPanel extends JPanel {
    SplashPanel() {
        setBackground(new Color(219, 0, 0)); // McDonald's red
        setLayout(new GridBagLayout());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        // Dark red bottom wave accent
        g2.setColor(new Color(180, 0, 0));
        g2.fillArc(-100, h - 160, w + 200, 300, 0, 180);

        // Yellow accent arch (McDonald's M shape inspired)
        g2.setColor(new Color(255, 188, 0));
        g2.setStroke(new BasicStroke(14, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(w/2 - 60, h/2 - 180, 50, 70, 0, -180);
        g2.drawArc(w/2 + 10, h/2 - 180, 50, 70, 0, -180);
        g2.setStroke(new BasicStroke(1));

        g2.dispose();
    }
}

// ===================== MAIN APP =====================

public class GrabnGo extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer;
    private CartService cartService = new CartService();
    private JPanel cartItemsPanel;
    private JLabel totalLabel;
    private JButton checkoutButton;
    private String currentCategory = "All";

    // McDonald's palette
    static final Color MC_RED    = new Color(219, 0, 0);
    static final Color MC_YELLOW = new Color(255, 188, 0);
    static final Color MC_DARK   = new Color(39, 39, 39);
    static final Color MC_GRAY   = new Color(245, 245, 245);
    static final Color MC_WHITE  = Color.WHITE;
    static final Color MC_LIGHT_RED = new Color(255, 235, 235);
    static final Color TEXT_DARK = new Color(28, 28, 26);
    static final Color TEXT_MUTED = new Color(110, 108, 100);
    static final Color BORDER    = new Color(220, 218, 212);

    static final java.util.List<MenuItem> MENU = Arrays.asList(
        new MenuItem("Burger",   80,  "Snacks", new Color(255, 235, 180), "burger"),
        new MenuItem("Pizza",    120, "Snacks", new Color(255, 225, 210), "pizza"),
        new MenuItem("Sandwich", 60,  "Snacks", new Color(220, 245, 220), "sandwich"),
        new MenuItem("Samosa",   20,  "Snacks", new Color(255, 240, 180), "samosa"),
        new MenuItem("Fries",    50,  "Snacks", new Color(255, 245, 200), "fries"),
        new MenuItem("Noodles",  70,  "Snacks", new Color(255, 235, 190), "noodles"),
        new MenuItem("Chai",     15,  "Drinks", new Color(255, 230, 200), "chai"),
        new MenuItem("Coffee",   30,  "Drinks", new Color(230, 215, 200), "coffee"),
        new MenuItem("Juice",    40,  "Drinks", new Color(255, 240, 200), "juice"),
        new MenuItem("Lassi",    35,  "Drinks", new Color(255, 245, 230), "lassi"),
        new MenuItem("Soda",     25,  "Drinks", new Color(255, 220, 220), "soda"),
        new MenuItem("Water",    10,  "Drinks", new Color(220, 240, 255), "water")
    );

    public GrabnGo() {
        setTitle("GrabnGo — Self-Service Kiosk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        mainContainer = new JPanel(cardLayout);
        mainContainer.add(buildSplashScreen(), "splash");
        mainContainer.add(buildKioskScreen(), "kiosk");
        add(mainContainer);
        cardLayout.show(mainContainer, "splash");
        setVisible(true);
    }

    // ======================== SPLASH SCREEN ========================

    private JPanel buildSplashScreen() {
        SplashPanel splash = new SplashPanel();
        splash.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Logo icon (drawn burger + fork)
        JPanel logoIcon = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();

                // Circle background
                g2.setColor(MC_YELLOW);
                g2.fillOval(0, 0, w, h);

                // Draw burger inside circle
                FoodIconPainter.drawBurger(g2, w, h);

                // Fork on right side
                g2.setColor(MC_RED);
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
        splash.add(logoIcon, gbc);

        // Brand name
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 4, 0);
        JLabel brand = new JLabel("GrabnGo");
        brand.setFont(new Font("SansSerif", Font.BOLD, 64));
        brand.setForeground(MC_YELLOW);
        splash.add(brand, gbc);

        // Tagline
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 50, 0);
        JLabel tagline = new JLabel("Order Fast. Eat Fresh.");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 20));
        tagline.setForeground(new Color(255, 255, 255, 200));
        splash.add(tagline, gbc);

        // Start Ordering button
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 40, 0);
        JButton startBtn = new JButton("  Start Ordering  ") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(new Color(200, 160, 0));
                else if (getModel().isRollover()) g2.setColor(new Color(255, 210, 30));
                else g2.setColor(MC_YELLOW);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 22));
        startBtn.setForeground(MC_RED);
        startBtn.setContentAreaFilled(false);
        startBtn.setBorderPainted(false);
        startBtn.setFocusPainted(false);
        startBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startBtn.setPreferredSize(new Dimension(260, 60));
        startBtn.addActionListener(e -> cardLayout.show(mainContainer, "kiosk"));
        splash.add(startBtn, gbc);

        // Food icons row at bottom
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 10, 0);
        JPanel iconsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        iconsRow.setOpaque(false);
        String[] keys = {"burger","pizza","fries","soda","sandwich"};
        for (String key : keys) {
            JPanel iconBg = new JPanel(new BorderLayout()) {
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
        splash.add(iconsRow, gbc);

        return splash;
    }

    // ======================== KIOSK SCREEN ========================

    private JPanel buildKioskScreen() {
        JPanel screen = new JPanel(new BorderLayout());
        screen.setBackground(MC_GRAY);

        // Top navbar
        JPanel navbar = buildNavbar();
        screen.add(navbar, BorderLayout.NORTH);

        // Body
        JPanel body = new JPanel(new BorderLayout(0, 0));
        body.setBackground(MC_GRAY);
        body.setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel menuArea = buildMenuArea();
        JPanel cartArea = buildCartPanel();

        body.add(menuArea, BorderLayout.CENTER);
        body.add(cartArea, BorderLayout.EAST);
        screen.add(body, BorderLayout.CENTER);

        return screen;
    }

    private JPanel buildNavbar() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(MC_RED);
        nav.setBorder(new EmptyBorder(0, 20, 0, 20));
        nav.setPreferredSize(new Dimension(0, 62));

        // Left: logo + name
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JPanel miniLogo = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(MC_YELLOW);
                g2.fillOval(0, 0, 36, 36);
                FoodIconPainter.drawBurger(g2, 36, 36);
                g2.dispose();
            }
        };
        miniLogo.setOpaque(false);
        miniLogo.setPreferredSize(new Dimension(36, 36));

        JLabel brandLabel = new JLabel("GrabnGo");
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        brandLabel.setForeground(MC_YELLOW);

        left.add(miniLogo);
        left.add(brandLabel);
        nav.add(left, BorderLayout.WEST);

        // Right: back to home
        JButton homeBtn = new JButton("← Back to Home");
        homeBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        homeBtn.setForeground(new Color(255, 220, 220));
        homeBtn.setContentAreaFilled(false);
        homeBtn.setBorderPainted(false);
        homeBtn.setFocusPainted(false);
        homeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeBtn.addActionListener(e -> {
            cartService.clearCart();
            refreshCart();
            cardLayout.show(mainContainer, "splash");
        });

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 14));
        right.setOpaque(false);
        right.add(homeBtn);
        nav.add(right, BorderLayout.EAST);

        return nav;
    }

    // ======================== MENU AREA ========================

    private JPanel buildMenuArea() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 10));
        wrapper.setBackground(MC_GRAY);
        wrapper.setBorder(new EmptyBorder(0, 0, 0, 14));

        // Category tab bar
        JPanel tabBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        tabBar.setBackground(MC_GRAY);

        String[] cats = {"All", "Snacks", "Drinks"};
        for (String cat : cats) tabBar.add(makeCategoryTab(cat, tabBar));
        wrapper.add(tabBar, BorderLayout.NORTH);

        // Grid
        JPanel grid = new JPanel(new GridLayout(0, 3, 12, 12));
        grid.setBackground(MC_GRAY);
        grid.setBorder(new EmptyBorder(8, 0, 0, 0));
        for (MenuItem item : MENU) grid.add(buildMenuCard(item));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setBackground(MC_GRAY);
        scroll.getViewport().setBackground(MC_GRAY);
        wrapper.add(scroll, BorderLayout.CENTER);

        return wrapper;
    }

    private JButton makeCategoryTab(String cat, JPanel tabBar) {
        boolean active = cat.equals(currentCategory);
        JButton tab = new JButton(cat) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getText().equals(currentCategory)) {
                    g2.setColor(MC_RED);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                    g2.setColor(MC_RED);
                } else {
                    g2.setColor(MC_WHITE);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                    g2.setColor(BORDER);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tab.setFont(new Font("SansSerif", Font.BOLD, 13));
        tab.setForeground(active ? MC_WHITE : TEXT_MUTED);
        tab.setContentAreaFilled(false);
        tab.setBorderPainted(false);
        tab.setFocusPainted(false);
        tab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tab.setPreferredSize(new Dimension(90, 34));

        tab.addActionListener(e -> {
            currentCategory = cat;
            // Find scroll → grid
            Container wrapperPanel = tabBar.getParent();
            JScrollPane scroll = (JScrollPane) wrapperPanel.getComponent(1);
            JPanel grid = (JPanel) scroll.getViewport().getView();
            grid.removeAll();
            for (MenuItem item : MENU) {
                if (currentCategory.equals("All") || item.category.equals(currentCategory))
                    grid.add(buildMenuCard(item));
            }
            grid.revalidate(); grid.repaint();
            // Repaint tabs
            for (Component c : tabBar.getComponents()) { c.repaint(); if (c instanceof JButton) ((JButton)c).setForeground(((JButton)c).getText().equals(currentCategory) ? MC_WHITE : TEXT_MUTED); }
        });
        return tab;
    }

    private JPanel buildMenuCard(MenuItem item) {
        JPanel card = new JPanel(new BorderLayout(0, 0)) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(MC_WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
            new AbstractBorder() {
                public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(BORDER);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRoundRect(x, y, w-1, h-1, 16, 16);
                    g2.dispose();
                }
                public Insets getBorderInsets(Component c) { return new Insets(1,1,1,1); }
            },
            new EmptyBorder(0, 0, 0, 0)
        ));

        // Food image panel
        JPanel imageArea = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(item.iconBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
            }
        };
        imageArea.setOpaque(false);
        imageArea.setPreferredSize(new Dimension(0, 110));

        FoodIconPainter icon = new FoodIconPainter(item.drawKey, 90);
        icon.setOpaque(false);
        imageArea.add(icon, BorderLayout.CENTER);
        card.add(imageArea, BorderLayout.NORTH);

        // Info area
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(8, 10, 10, 10));

        JLabel nameLabel = new JLabel(item.name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setForeground(TEXT_DARK);

        JLabel priceLabel = new JLabel("₹" + (int) item.price);
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        priceLabel.setForeground(TEXT_MUTED);

        JButton addBtn = new JButton("+ Add to Order") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(new Color(180, 0, 0));
                else if (getModel().isRollover()) g2.setColor(new Color(200, 20, 20));
                else g2.setColor(MC_RED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        addBtn.setForeground(MC_WHITE);
        addBtn.setContentAreaFilled(false);
        addBtn.setBorderPainted(false);
        addBtn.setFocusPainted(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        addBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        addBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        addBtn.addActionListener(e -> { cartService.addItem(item); refreshCart(); });

        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        info.add(nameLabel);
        info.add(Box.createVerticalStrut(2));
        info.add(priceLabel);
        info.add(Box.createVerticalStrut(8));
        info.add(addBtn);

        card.add(info, BorderLayout.CENTER);

        // Click anywhere on card also adds
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { cartService.addItem(item); refreshCart(); }
        });

        return card;
    }

    // ======================== CART PANEL ========================

    private JPanel buildCartPanel() {
        JPanel cart = new JPanel(new BorderLayout(0, 10)) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(MC_WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
            }
        };
        cart.setOpaque(false);
        cart.setPreferredSize(new Dimension(270, 0));
        cart.setBorder(new EmptyBorder(14, 14, 14, 14));

        // Header
        JPanel cartHeader = new JPanel(new BorderLayout());
        cartHeader.setOpaque(false);
        JLabel title = new JLabel("Your Order");
        title.setFont(new Font("SansSerif", Font.BOLD, 17));
        title.setForeground(TEXT_DARK);
        JLabel bagIcon = new JLabel("🛍") { { setFont(new Font("SansSerif", Font.PLAIN, 18)); } };
        cartHeader.add(bagIcon, BorderLayout.WEST);
        cartHeader.add(title, BorderLayout.CENTER);
        cart.add(cartHeader, BorderLayout.NORTH);

        // Items
        cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane(cartItemsPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        cart.add(scroll, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setOpaque(false);

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        footer.add(sep);
        footer.add(Box.createVerticalStrut(10));

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        JLabel totalTxt = new JLabel("Order Total");
        totalTxt.setFont(new Font("SansSerif", Font.PLAIN, 13));
        totalTxt.setForeground(TEXT_MUTED);
        totalLabel = new JLabel("₹0");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalLabel.setForeground(TEXT_DARK);
        totalRow.add(totalTxt, BorderLayout.WEST);
        totalRow.add(totalLabel, BorderLayout.EAST);
        footer.add(totalRow);
        footer.add(Box.createVerticalStrut(12));

        checkoutButton = new JButton("Checkout") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (!isEnabled()) g2.setColor(new Color(200, 198, 192));
                else if (getModel().isPressed()) g2.setColor(new Color(180, 0, 0));
                else if (getModel().isRollover()) g2.setColor(new Color(200, 20, 20));
                else g2.setColor(MC_RED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        checkoutButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        checkoutButton.setForeground(MC_WHITE);
        checkoutButton.setContentAreaFilled(false);
        checkoutButton.setBorderPainted(false);
        checkoutButton.setFocusPainted(false);
        checkoutButton.setEnabled(false);
        checkoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        checkoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkoutButton.addActionListener(e -> showPaymentDialog());
        footer.add(checkoutButton);

        cart.add(footer, BorderLayout.SOUTH);

        refreshCart();
        return cart;
    }

    private void refreshCart() {
        cartItemsPanel.removeAll();
        if (cartService.isEmpty()) {
            JLabel empty = new JLabel("Add items to get started");
            empty.setFont(new Font("SansSerif", Font.PLAIN, 13));
            empty.setForeground(TEXT_MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            cartItemsPanel.add(Box.createVerticalStrut(30));
            cartItemsPanel.add(empty);
        } else {
            for (CartItem ci : cartService.getCartItems()) {
                cartItemsPanel.add(buildCartRow(ci));
                cartItemsPanel.add(Box.createVerticalStrut(6));
            }
        }
        totalLabel.setText("₹" + (int) cartService.getTotal());
        checkoutButton.setEnabled(!cartService.isEmpty());
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();
    }

    private JPanel buildCartRow(CartItem ci) {
        JPanel row = new JPanel(new BorderLayout(4, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JLabel name = new JLabel(ci.menuItem.name);
        name.setFont(new Font("SansSerif", Font.PLAIN, 13));
        name.setForeground(TEXT_DARK);

        JPanel qty = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 2));
        qty.setOpaque(false);

        JButton minus = qtyBtn("−");
        minus.addActionListener(e -> { cartService.removeItem(ci.menuItem.name); refreshCart(); });
        JLabel num = new JLabel(String.valueOf(ci.quantity), SwingConstants.CENTER);
        num.setFont(new Font("SansSerif", Font.BOLD, 13));
        num.setForeground(TEXT_DARK);
        num.setPreferredSize(new Dimension(22, 22));
        JButton plus = qtyBtn("+");
        plus.addActionListener(e -> { cartService.addItem(ci.menuItem); refreshCart(); });

        qty.add(minus); qty.add(num); qty.add(plus);

        JLabel price = new JLabel("₹" + (int) ci.getSubtotal());
        price.setFont(new Font("SansSerif", Font.PLAIN, 13));
        price.setForeground(TEXT_MUTED);

        row.add(name, BorderLayout.WEST);
        row.add(qty,  BorderLayout.CENTER);
        row.add(price, BorderLayout.EAST);
        return row;
    }

    private JButton qtyBtn(String t) {
        JButton b = new JButton(t);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setPreferredSize(new Dimension(24, 24));
        b.setFocusPainted(false);
        b.setBackground(MC_GRAY);
        b.setForeground(TEXT_DARK);
        b.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ======================== PAYMENT DIALOG ========================

    private void showPaymentDialog() {
        JDialog dialog = new JDialog(this, "Payment", true);
        dialog.setSize(380, 280);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(MC_WHITE);
        dialog.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(MC_WHITE);
        content.setBorder(new EmptyBorder(24, 28, 24, 28));

        JLabel heading = new JLabel("Choose payment method");
        heading.setFont(new Font("SansSerif", Font.BOLD, 17));
        heading.setForeground(TEXT_DARK);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel totalLbl = new JLabel("Total: ₹" + (int) cartService.getTotal());
        totalLbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        totalLbl.setForeground(TEXT_MUTED);
        totalLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Payment option buttons
        JPanel optRow = new JPanel(new GridLayout(1, 2, 12, 0));
        optRow.setOpaque(false);
        optRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        optRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        final boolean[] upiSelected = {false};

        JButton cashBtn = buildPayOptionBtn("💵  Cash", false);
        JButton upiBtn  = buildPayOptionBtn("📱  UPI", false);

        cashBtn.addActionListener(e -> {
            upiSelected[0] = false;
            cashBtn.setBackground(MC_LIGHT_RED);
            cashBtn.setBorder(BorderFactory.createLineBorder(MC_RED, 2));
            upiBtn.setBackground(MC_WHITE);
            upiBtn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        });
        upiBtn.addActionListener(e -> {
            upiSelected[0] = true;
            upiBtn.setBackground(MC_LIGHT_RED);
            upiBtn.setBorder(BorderFactory.createLineBorder(MC_RED, 2));
            cashBtn.setBackground(MC_WHITE);
            cashBtn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        });

        optRow.add(cashBtn);
        optRow.add(upiBtn);

        JButton payBtn = new JButton("Pay Now") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(new Color(180, 0, 0));
                else if (getModel().isRollover()) g2.setColor(new Color(200, 20, 20));
                else g2.setColor(MC_RED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        payBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        payBtn.setForeground(MC_WHITE);
        payBtn.setContentAreaFilled(false);
        payBtn.setBorderPainted(false);
        payBtn.setFocusPainted(false);
        payBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        payBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        payBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        payBtn.addActionListener(e -> {
            if (!cashBtn.getBackground().equals(MC_LIGHT_RED) && !upiBtn.getBackground().equals(MC_LIGHT_RED)) {
                JOptionPane.showMessageDialog(dialog, "Please select a payment method.", "Select Payment", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dialog.dispose();
            if (upiSelected[0]) showUPIDialog();
            else {
                int token = cartService.generateToken();
                cartService.clearCart(); refreshCart();
                showConfirmDialog(token, "Cash");
            }
        });

        content.add(heading);
        content.add(Box.createVerticalStrut(4));
        content.add(totalLbl);
        content.add(Box.createVerticalStrut(20));
        content.add(optRow);
        content.add(Box.createVerticalStrut(20));
        content.add(payBtn);

        dialog.add(content, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private JButton buildPayOptionBtn(String label, boolean selected) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(TEXT_DARK);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBackground(MC_WHITE);
        btn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        btn.setPreferredSize(new Dimension(0, 52));
        return btn;
    }

    // ======================== UPI QR DIALOG ========================

    private void showUPIDialog() {
        JDialog dialog = new JDialog(this, "UPI Payment", true);
        dialog.setSize(300, 380);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(MC_WHITE);
        dialog.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(MC_WHITE);
        content.setBorder(new EmptyBorder(20, 24, 20, 24));

        JLabel title = new JLabel("Scan to Pay");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel amt = new JLabel("₹" + (int) cartService.getTotal());
        amt.setFont(new Font("SansSerif", Font.BOLD, 28));
        amt.setForeground(MC_RED);
        amt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("GrabnGo Canteen • UPI");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        QRCodePanel qr = new QRCodePanel();
        qr.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scanHint = new JLabel("Scan with any UPI app");
        scanHint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        scanHint.setForeground(TEXT_MUTED);
        scanHint.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton doneBtn = new JButton("Payment Done") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(new Color(30, 100, 10));
                else if (getModel().isRollover()) g2.setColor(new Color(50, 140, 20));
                else g2.setColor(new Color(59, 109, 17));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        doneBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        doneBtn.setForeground(MC_WHITE);
        doneBtn.setContentAreaFilled(false);
        doneBtn.setBorderPainted(false);
        doneBtn.setFocusPainted(false);
        doneBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        doneBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        doneBtn.addActionListener(e -> {
            int token = cartService.generateToken();
            dialog.dispose();
            cartService.clearCart(); refreshCart();
            showConfirmDialog(token, "UPI");
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

        dialog.add(content, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    // ======================== CONFIRMATION DIALOG ========================

    private void showConfirmDialog(int token, String method) {
        JDialog dialog = new JDialog(this, "Order Confirmed!", true);
        dialog.setSize(320, 300);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(MC_WHITE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(MC_WHITE);
        content.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Green checkmark circle
        JPanel check = new JPanel() {
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
        placed.setFont(new Font("SansSerif", Font.BOLD, 20));
        placed.setForeground(new Color(59, 109, 17));
        placed.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tokenNum = new JLabel(String.valueOf(token));
        tokenNum.setFont(new Font("SansSerif", Font.BOLD, 60));
        tokenNum.setForeground(MC_RED);
        tokenNum.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tokenSub = new JLabel("Token Number");
        tokenSub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tokenSub.setForeground(TEXT_MUTED);
        tokenSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel methodLbl = new JLabel("Paid via " + method);
        methodLbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        methodLbl.setForeground(TEXT_MUTED);
        methodLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newOrder = new JButton("New Order") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(new Color(180, 0, 0));
                else g2.setColor(MC_RED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        newOrder.setFont(new Font("SansSerif", Font.BOLD, 14));
        newOrder.setForeground(MC_WHITE);
        newOrder.setContentAreaFilled(false);
        newOrder.setBorderPainted(false);
        newOrder.setFocusPainted(false);
        newOrder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        newOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
        newOrder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newOrder.addActionListener(e -> dialog.dispose());

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

        dialog.add(content, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    // ======================== MAIN ========================

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(GrabnGo::new);
    }
}
