package nlu.fit.leanhduc.view.component.dialog.key;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.util.Constraint;

import javax.swing.*;
import java.awt.*;

public class SubstitutionTypingPanel extends KeyTypingPanel {
    public SubstitutionTypingPanel(MainController controller) {

        super(controller);
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


        this.setSize(400, 200);
    }
}
