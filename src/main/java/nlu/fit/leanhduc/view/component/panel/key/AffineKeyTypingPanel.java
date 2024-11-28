package nlu.fit.leanhduc.view.component.panel.key;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.controller.SubstitutionCipherController;
import nlu.fit.leanhduc.service.key.classic.AffineKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;

public class AffineKeyTypingPanel extends KeyTypingPanel<AffineKeyClassic> {
    JFormattedTextField inputA, inputB;

    public AffineKeyTypingPanel(MainController controller) {

        super(controller);
    }

    @Override
    public void init() {
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.add(panel, BorderLayout.CENTER);

        panel.add(new Label("a"));
        inputA = SwingComponentUtil.createFormatTextFieldNumber(3, 1, 26, 1, false, true);
        panel.add(inputA);

        panel.add(new Label("b"));
        inputB = SwingComponentUtil.createFormatTextFieldNumber(3, 1, 26, 1, false, true);
        panel.add(inputB);

    }

    @Override
    public AffineKeyClassic getKey() throws CipherException {
        try {
            int a = Integer.parseInt(inputA.getText());
            int b = Integer.parseInt(inputB.getText());
            this.key = SubstitutionCipherController.getINSTANCE().generateAffineKey(a, b);
            return this.key;
        } catch (Exception e) {
            throw new CipherException("Khóa không hợp lệ");
        }
    }

    @Override
    public void setKey(AffineKeyClassic key) {
        inputA.setText(String.valueOf(key.getA()));
        inputB.setText(String.valueOf(key.getB()));
    }
}
