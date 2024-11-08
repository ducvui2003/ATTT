package nlu.fit.leanhduc.view.component.dialog.key;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.ISubstitutionCipher;

import javax.swing.*;
import java.awt.*;

public class ViginereKeyTypingPanel extends KeyTypingPanel {

    public ViginereKeyTypingPanel(MainController controller) {
        super(controller);
    }

    @Override
    public void init() {
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        this.add(new JLabel("Khóa"));
        JTextField inputKey = new JTextField();
        this.add(inputKey);

        this.setSize(400, 200);
    }

    @Override
    public ISubstitutionCipher getKey() {
        return null;
    }
}
