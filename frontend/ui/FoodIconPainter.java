package frontend.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

import javax.swing.JPanel;

/**
 * Custom component to draw vector representations of food items.
 */
public class FoodIconPainter extends JPanel {
    private final String key;

    public FoodIconPainter(String key, int size) {
        this.key = key;
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        drawFood(g2, key, w, h);
        g2.dispose();
    }

    public static void drawFood(Graphics2D g2, String key, int w, int h) {
        if (key == null) return;
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

    private static void drawBurger(Graphics2D g, int w, int h) {
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

    private static void drawPizza(Graphics2D g, int w, int h) {
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

    private static void drawSandwich(Graphics2D g, int w, int h) {
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

    private static void drawSamosa(Graphics2D g, int w, int h) {
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

    private static void drawFries(Graphics2D g, int w, int h) {
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

    private static void drawNoodles(Graphics2D g, int w, int h) {
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

    private static void drawChai(Graphics2D g, int w, int h) {
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

    private static void drawCoffee(Graphics2D g, int w, int h) {
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

    private static void drawJuice(Graphics2D g, int w, int h) {
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

    private static void drawLassi(Graphics2D g, int w, int h) {
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

    private static void drawSoda(Graphics2D g, int w, int h) {
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

    private static void drawWater(Graphics2D g, int w, int h) {
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
