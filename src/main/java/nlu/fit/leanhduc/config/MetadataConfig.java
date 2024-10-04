package nlu.fit.leanhduc.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.awt.*;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MetadataConfig {
    public static MetadataConfig INSTANCE;
    Image image;

    private MetadataConfig() {
        image = Toolkit.getDefaultToolkit().getImage(MetadataConfig.class.getResource("/icons/app.png"));
    }

    public static MetadataConfig getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new MetadataConfig();
        }
        return INSTANCE;
    }
}
