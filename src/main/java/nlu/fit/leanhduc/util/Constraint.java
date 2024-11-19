package nlu.fit.leanhduc.util;


import nlu.fit.leanhduc.config.FontConfig;
import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.util.constraint.Mode;

import java.awt.*;

public class Constraint {
    public static final String APP_NAME = "Leanhduc";
    public static final String APP_VERSION = "1.0";
    public static final String APP_AUTHOR = "Leanhduc";
    public static final String APP_DESCRIPTION = "This is a simple Java Swing application";
    public static final int APP_FONT_SIZE = 14;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    public static final Font FONT_REGULAR = FontConfig.getInstance().getFontRegular();
    public static final Font FONT_MEDIUM = FontConfig.getInstance().getFontRegular();
    public static final Font FONT_BOLD = FontConfig.getInstance().getFontRegular();

    public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    public static final Image APP_ICON_IMAGE = MetadataConfig.getINSTANCE().getLogo();

    public static final char FIRST_CHAR = 'a';
    public static final char FIRST_CHAR_UPPER = 'A';
    public static final char LAST_CHAR = 'z';
    public static final char LAST_CHAR_UPPER = 'Z';
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static final char[] ALPHABET_ARRAY = ALPHABET.toCharArray();
    public static final int ALPHABET_SIZE = ALPHABET_ARRAY.length;
    public static final String VIET_NAM_N = "aáàạảãăắằặẳẵâấầậẩẫbcdđeéẹẻẽêếềệểễfghiíìịỉĩjklmnoóòọỏõôốồộổỗơớờợởỡpqrstuúùụủũưứừựửữvxyýỳỵỷỹ";
    public static final char[] VIET_NAME_ALPHA_ARRAY = VIET_NAM_N.toCharArray();
    public static final int VIET_NAME_ALPHA_BET_SIZE = VIET_NAME_ALPHA_ARRAY.length;

    public static final String DEFAULT_PROVIDER = "SunJCE";
    public static final Mode DEFAULT_MODE = Mode.CBC;

}
