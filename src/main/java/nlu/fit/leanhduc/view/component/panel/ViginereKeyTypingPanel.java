package nlu.fit.leanhduc.view.component.panel;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.key.ViginereKey;

import javax.swing.*;
import java.awt.*;

public class ViginereKeyTypingPanel extends KeyTypingPanel<ViginereKey> {

    public ViginereKeyTypingPanel(MainController controller) {
        super(controller);
    }

    @Override
    public void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gcb = new GridBagConstraints();
        gcb.gridx = 0;
        gcb.gridy = 0;
        gcb.weightx = 0.25;
        gcb.fill = GridBagConstraints.HORIZONTAL;
        gcb.insets = new Insets(0, 10, 0, 0);
        this.add(new JLabel("Khóa"), gcb);
        gcb.gridx = 1;
        gcb.gridy = 0;
        gcb.weightx = 1.0;
        gcb.fill = GridBagConstraints.HORIZONTAL;
        JTextField inputKey = new JTextField();
        this.add(inputKey, gcb);

    }

    @Override
    public ViginereKey getKey() {
        return null;
    }

    @Override
    public void setKey(ViginereKey key) {

    }
}
