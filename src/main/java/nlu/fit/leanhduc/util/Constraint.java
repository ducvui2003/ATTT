package nlu.fit.leanhduc.util;


import nlu.fit.leanhduc.config.FontConfig;
import nlu.fit.leanhduc.config.MetadataConfig;

import java.awt.*;

public class Constraint {
    public static final String APP_NAME = "Leanhduc";
    public static final String APP_VERSION = "1.0";
    public static final String APP_AUTHOR = "Leanhduc";
    public static final String APP_DESCRIPTION = "This is a simple Java Swing application";
    public static final int APP_FONT_SIZE = 14;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    public static final Font FONT_REGULAR = FontConfig.getInstance().getFontRegular();
    public static final Font FONT_MEDIUM = FontConfig.getInstance().getFontRegular();
    public static final Font FONT_BOLD = FontConfig.getInstance().getFontRegular();

    public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    public static final Image APP_ICON_IMAGE = MetadataConfig.getINSTANCE().getImage();

    public static final char FIRST_CHAR = 'a';
    public static final char FIRST_CHAR_UPPER = 'A';
    public static final char LAST_CHAR = 'z';
    public static final char LAST_CHAR_UPPER = 'Z';
    public static final int ALPHABET_SIZE = 26;
    public static final String VIET_NAM_N = "aáàạảãăắằặẳẵâấầậẩẫbcdđeéẹẻẽêếềệểễfghiíìịỉĩjklmnoóòọỏõôốồộổỗơớờợởỡpqrstuúùụủũưứừựửữvxyýỳỵỷỹ";
    public static final char[] VIET_NAME_ALPHA_BET = VIET_NAM_N.toCharArray();
    public static final int VIET_NAME_ALPHA_BET_SIZE = VIET_NAME_ALPHA_BET.length;

    public static Language identifyLanguage(String text) {
        for (int i = 0; i < text.length(); i++)
            if (VIET_NAM_N.indexOf(i) != -1)
                return Language.VIETNAMESE;
        return Language.ENGLISH;
    }
}
