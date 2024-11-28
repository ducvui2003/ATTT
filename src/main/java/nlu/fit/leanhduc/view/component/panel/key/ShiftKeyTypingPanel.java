package nlu.fit.leanhduc.view.component.panel.key;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.key.classic.ShiftKeyClassic;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;


public class ShiftKeyTypingPanel extends KeyTypingPanel<ShiftKeyClassic> {
    JFormattedTextField inputKeyLength;

    public ShiftKeyTypingPanel(MainController controller) {
        super(controller);
    }

    @Override
    public void init() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputKeyLength = SwingComponentUtil.createFormatTextFieldNumber(
                3, 1, null, 1,
                false, true);
        this.add(new JLabel("Độ dài khóa:"));
        this.add(inputKeyLength);
        this.setSize(400, 200);
    }

    @Override
    public ShiftKeyClassic getKey() {
        return new ShiftKeyClassic(Integer.parseInt(inputKeyLength.getText()));
    }

    @Override
    public void setKey(ShiftKeyClassic key) {
        inputKeyLength.setValue(key.getKey());
    }
}
