package nlu.fit.leanhduc.view.component.panel.key;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.key.SubstitutionKey;
import nlu.fit.leanhduc.util.Constraint;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.Collectors;

public class SubstitutionTypingPanel extends KeyTypingPanel<SubstitutionKey> {


    JTextField inputAlphabet, inputAlphabetSubstitution;


    public SubstitutionTypingPanel(MainController controller) {

        super(controller);
    }

    @Override
    public void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        int leftMargin = 10;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, leftMargin, 0, 0);
        this.add(new JLabel("Bảng chữ cái:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, leftMargin, 0, 0);

        inputAlphabet = new JTextField(20);
        inputAlphabet.setText(Constraint.ALPHABET);
        inputAlphabet.setSize(200, 20);
        this.add(inputAlphabet, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, leftMargin, 0, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(new JLabel("Bảng chữ cái thay thế:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, leftMargin, 0, 0);
        inputAlphabetSubstitution = new JTextField(20);
        this.add(inputAlphabetSubstitution, gbc);
    }

    @Override
    public SubstitutionKey getKey() {
        Map<Character, Character> key = inputAlphabet.getText().chars().mapToObj(c -> (char) c).collect(
                Collectors.toMap(c -> c, c -> inputAlphabetSubstitution.getText().charAt(inputAlphabet.getText().indexOf(c))));
        return new SubstitutionKey(key);
    }

    public void setKey(SubstitutionKey key) {
        this.key = key;
        inputAlphabet.setText(key.getKey().keySet().stream().map(Object::toString).reduce("", String::concat));
        inputAlphabetSubstitution.setText(key.getKey().values().stream().map(Object::toString).reduce("", String::concat));
    }
}
