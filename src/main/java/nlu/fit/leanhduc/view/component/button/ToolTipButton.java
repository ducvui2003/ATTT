package nlu.fit.leanhduc.view.component.button;

import nlu.fit.leanhduc.config.MetadataConfig;

import javax.swing.*;
import java.awt.*;

public class ToolTipButton extends JButton {
    public ToolTipButton(String text, String toolTipText) {
        super(text);

        this.setIcon(MetadataConfig.getINSTANCE().getTooltipIcon());
        this.setToolTipText(toolTipText);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
