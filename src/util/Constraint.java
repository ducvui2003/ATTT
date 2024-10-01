package util;


import config.FontConfig;

import java.awt.*;

public class Constraint {
    public static final String APP_NAME = "Leanhduc";
    public static final String APP_VERSION = "1.0";
    public static final String APP_AUTHOR = "Leanhduc";
    public static final String APP_DESCRIPTION = "This is a simple Java Swing application";
    public static final String APP_FONT = "Arial";
    public static final int APP_FONT_SIZE = 14;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    public static final Font FONT_REGULAR = FontConfig.getInstance().getFontRegular();
    public static final Font FONT_MEDIUM = FontConfig.getInstance().getFontRegular();
    public static final Font FONT_BOLD = FontConfig.getInstance().getFontRegular();

    public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    public static final Image APP_ICON_IMAGE = Toolkit.getDefaultToolkit().getImage("resources/icons/app.jpeg");

}
