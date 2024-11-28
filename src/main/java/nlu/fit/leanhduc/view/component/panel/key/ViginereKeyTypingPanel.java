package nlu.fit.leanhduc.view.component.panel.key;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.controller.SubstitutionCipherController;
import nlu.fit.leanhduc.service.key.classic.ViginereKeyClassic;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;
import nlu.fit.leanhduc.util.constraint.Language;

import javax.swing.*;
import java.awt.*;

public class ViginereKeyTypingPanel extends KeyTypingPanel<ViginereKeyClassic> {
    JTextField inputKey;
    Language language;

    public ViginereKeyTypingPanel(MainController controller, Language language) {
        super(controller);
        this.language = language;
    }

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
        inputKey = new JTextField();
        this.add(inputKey, gcb);

    }

    @Override
    public ViginereKeyClassic getKey() {
        String keyString = inputKey.getText();
        return SubstitutionCipherController.getINSTANCE().generateVigenereKey(keyString, language);
    }

    @Override
    public void setKey(ViginereKeyClassic key) {
        AlphabetUtil alphabetUtil = Language.VIETNAMESE.equals(language) ? new VietnameseAlphabetUtil() : new EnglishAlphabetUtil();
        String keyString = key.getKey().stream().map(alphabetUtil::getChar).map(String::valueOf).reduce("", String::concat);
        inputKey.setText(keyString);
    }
}
