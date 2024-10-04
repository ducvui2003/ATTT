package nlu.fit.leanhduc.config;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontConfig {
    public static FontConfig INSTANCE;
    Font fontRegular;
    Font fontBold;
    Font fontLight;

    private FontConfig() {
        setFont();
    }

    public static FontConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FontConfig();
        }
        return INSTANCE;
    }

    public void setFont() {
        try {
            // Load fonts from the classpath using getResourceAsStream
            InputStream regularStream = FontConfig.class.getResourceAsStream("/fonts/Roboto-Regular.ttf");
            InputStream mediumStream = FontConfig.class.getResourceAsStream("/fonts/Roboto-Medium.ttf");
            InputStream boldStream = FontConfig.class.getResourceAsStream("/fonts/Roboto-Bold.ttf");

            // Create fonts from the InputStream
            Font haslMultiRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            Font haslMultiMedium = Font.createFont(Font.TRUETYPE_FONT, mediumStream);
            Font haslMultiBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            // Register the fonts
            ge.registerFont(haslMultiRegular);
            ge.registerFont(haslMultiMedium);
            ge.registerFont(haslMultiBold);

            // Derive different sizes if necessary
            fontRegular = haslMultiRegular.deriveFont(16f);
            fontBold = haslMultiBold.deriveFont(18f);
            fontLight = haslMultiMedium.deriveFont(14f);

            // Close streams
            regularStream.close();
            mediumStream.close();
            boldStream.close();

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fontRegular = new Font("Arial", Font.PLAIN, 14);
            fontBold = new Font("Arial", Font.BOLD, 14);
            fontLight = new Font("Arial", Font.PLAIN, 12);
        }
    }

    public Font getFontRegular() {
        return fontRegular;
    }

    public Font getFontBold() {
        return fontBold;
    }

    public Font getFontLight() {
        return fontLight;
    }
}
