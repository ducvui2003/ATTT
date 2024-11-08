package nlu.fit.leanhduc.view.component.dialog.key;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.ISubstitutionCipher;
import nlu.fit.leanhduc.service.key.IKeyDisplay;

import javax.swing.*;

public abstract class KeyTypingPanel<T> extends JPanel {
    MainController controller;

    public KeyTypingPanel(MainController controller) {
        this.controller = controller;
        this.init();
    }

    public abstract void init();

    public abstract ISubstitutionCipher<T> getKey();

}
