package nlu.fit.leanhduc.view.component.dialog;

import nlu.fit.leanhduc.service.key.ShiftKey;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.view.component.SwingComponentUtil;

import javax.swing.*;
import java.awt.*;

public class CreateSubstitutionDialog extends CreateKeyDialog {
    public CreateSubstitutionDialog(Frame owner) {
        super(owner);
    }

    @Override
    public void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gcb = new GridBagConstraints();
        gcb.insets = new Insets(5, 5, 5, 5);
        gcb.fill = GridBagConstraints.HORIZONTAL;

        gcb.gridx = 0;
        gcb.gridy = 0;
        gcb.gridwidth = 2;
        this.add(new JLabel("Bảng chữ cái:"), gcb);

        gcb.gridx = 2;
        gcb.gridy = 0;
        gcb.gridwidth = 3;
        JTextField inputAlphabet = new JTextField(20);
        inputAlphabet.setText(Constraint.ALPHABET);
        this.add(inputAlphabet, gcb);

        gcb.gridx = 0;
        gcb.gridy = 1;
        gcb.gridwidth = 2;
        this.add(new JLabel("Bảng chữ cái thay thế:"), gcb);

        gcb.gridx = 2;
        gcb.gridy = 1;
        gcb.gridwidth = 3;
        JTextField inputAlphabetSubstitution = new JTextField(20);
        this.add(inputAlphabetSubstitution, gcb);

        gcb.gridx = 0;
        gcb.gridy = 2;
        gcb.gridwidth = 4;
        JButton button = new JButton("Tạo khóa");
        this.add(button, gcb);

        button.addActionListener(e -> {
            this.key = new ShiftKey(Integer.parseInt(inputAlphabet.getText()));
            this.handleClose();
        });

        this.setSize(400, 200);
    }
}
