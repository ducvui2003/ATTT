package nlu.fit.leanhduc.view.component.dialog;

import nlu.fit.leanhduc.service.key.ShiftKey;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;


public class CreateShiftKeyDialog extends CreateKeyDialog {

    public CreateShiftKeyDialog(Frame owner) {
        super(owner);
    }

    @Override
    public void init() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        JTextField inputKeyLength = SwingComponentUtil.createFormatTextFieldNumber(
                3, 1, 26, 1,
                false, true);
        this.add(new JLabel("Độ dài khóa:"));
        this.add(inputKeyLength);
        JButton button = new JButton("Tạo khóa");
        this.add(button);

        button.addActionListener(e -> {
            this.key = new ShiftKey(Integer.parseInt(inputKeyLength.getText()));
            this.handleClose();
        });

        this.setSize(400, 200);
    }

}
