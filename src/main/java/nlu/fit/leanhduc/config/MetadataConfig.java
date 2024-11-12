package nlu.fit.leanhduc.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.awt.*;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MetadataConfig {
    public static MetadataConfig INSTANCE;
    Image logo;
    ImageIcon tooltipIcon;
    ImageIcon successIcon;
    ImageIcon warningIcon;

    private MetadataConfig() {
        logo = Toolkit.getDefaultToolkit().getImage(MetadataConfig.class.getResource("/icons/app.png"));
        tooltipIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MetadataConfig.class.getResource("/icons/tool-tip.png")));
        successIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MetadataConfig.class.getResource
                ("/icons/success.png")));
        warningIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MetadataConfig.class.getResource
                ("/icons/warning.png")));
    }

    public static MetadataConfig getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new MetadataConfig();
        }
        return INSTANCE;
    }
}
