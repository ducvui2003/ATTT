package nlu.fit.leanhduc.view.component.panel;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.ISubstitutionCipher;
import nlu.fit.leanhduc.service.key.AffineKey;
import nlu.fit.leanhduc.service.key.ViginereKey;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;
import nlu.fit.leanhduc.view.component.button.ToolTipButton;

import javax.swing.*;
import java.awt.*;

public class AffineKeyTypingPanel extends KeyTypingPanel<AffineKey> {
    JFormattedTextField inputA, inputB;

    public AffineKeyTypingPanel(MainController controller) {

        super(controller);
    }

    @Override
    public void init() {
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(panel, BorderLayout.CENTER);
        panel.add(new Label("a"));
        inputA = SwingComponentUtil.createFormatTextFieldNumber(3, 1, 26, 1, false, true);
        panel.add(inputA);

        panel.add(new Label("b"));
        inputB = SwingComponentUtil.createFormatTextFieldNumber(3, 1, 26, 1, false, true);
        panel.add(inputB);


        String tooltipText = "<html>" +
                "<b>Biểu thức thuật toán</b><br>" +
                "<i>c = (ax + b) mod 26</i><br>" +
                "<br>" +
                "<p><b>Where:</b></p>" +
                "<ul>" +
                "<li><b>a</b> and <b>b</b> are the key values.</li>" +
                "<li><b>x</b> is the plaintext letter.</li>" +
                "<li><b>c</b> is the ciphertext letter.</li>" +
                "</ul>" +
                "</html>";

        JButton btnToolTip = new ToolTipButton(null, tooltipText);
        panel.add(btnToolTip, BorderLayout.EAST);

    }

    @Override
    public AffineKey getKey() {
        int a = Integer.parseInt(inputA.getText());
        int b = Integer.parseInt(inputB.getText());
        this.key = controller.generateAffineKey(a, b);
        return this.key;
    }

    @Override
    public void setKey(AffineKey key) {
        inputA.setText(String.valueOf(key.getA()));
        inputB.setText(String.valueOf(key.getB()));
    }
}
