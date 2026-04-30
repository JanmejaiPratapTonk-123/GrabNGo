package frontend.ui;

import java.awt.Color;
import java.awt.Font;

/**
 * Centralised theme constants for the application.
 */
public class AppTheme {
    public static final Color MC_RED       = new Color(219, 0, 0);
    public static final Color MC_YELLOW    = new Color(255, 188, 0);
    public static final Color MC_DARK      = new Color(39, 39, 39);
    public static final Color MC_GRAY      = new Color(245, 245, 245);
    public static final Color MC_WHITE     = Color.WHITE;
    public static final Color MC_LIGHT_RED = new Color(255, 235, 235);
    
    public static final Color TEXT_DARK    = new Color(28, 28, 26);
    public static final Color TEXT_MUTED   = new Color(110, 108, 100);
    public static final Color BORDER       = new Color(220, 218, 212);

    public static final String FONT_FAMILY = "SansSerif";

    public static Font getFontBold(int size) {
        return new Font(FONT_FAMILY, Font.BOLD, size);
    }

    public static Font getFontPlain(int size) {
        return new Font(FONT_FAMILY, Font.PLAIN, size);
    }
}
