package nlu.fit.leanhduc.view.component.panel;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.ISubstitutionCipher;
import nlu.fit.leanhduc.service.key.IKeyDisplay;

import javax.swing.*;

public abstract class KeyTypingPanel<T> extends JPanel {
    MainController controller;
    protected T key;

    public KeyTypingPanel(MainController controller) {
        this.controller = controller;
        this.init();
    }

    public abstract void init();

    public abstract IKeyDisplay getKey();

    public abstract void setKey(T key);
}
