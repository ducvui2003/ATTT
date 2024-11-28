package nlu.fit.leanhduc.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
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
            InputStream regularStream = FontConfig.class.getResourceAsStream("/fonts/Roboto-Regular.ttf");
            InputStream mediumStream = FontConfig.class.getResourceAsStream("/fonts/Roboto-Medium.ttf");
            InputStream boldStream = FontConfig.class.getResourceAsStream("/fonts/Roboto-Bold.ttf");

            Font haslMultiRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            Font haslMultiMedium = Font.createFont(Font.TRUETYPE_FONT, mediumStream);
            Font haslMultiBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            ge.registerFont(haslMultiRegular);
            ge.registerFont(haslMultiMedium);
            ge.registerFont(haslMultiBold);

            fontRegular = haslMultiRegular.deriveFont(16f);
            fontBold = haslMultiBold.deriveFont(18f);
            fontLight = haslMultiMedium.deriveFont(14f);

            regularStream.close();
            mediumStream.close();
            boldStream.close();

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fontRegular = new Font("Roboto", Font.PLAIN, 14);
            fontBold = new Font("Roboto", Font.BOLD, 14);
            fontLight = new Font("Roboto", Font.PLAIN, 12);
        }
    }

}
