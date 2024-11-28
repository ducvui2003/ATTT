package nlu.fit.leanhduc.view.component.panel.key;

import nlu.fit.leanhduc.controller.MainController;
import nlu.fit.leanhduc.service.key.classic.IKeyClassic;
import nlu.fit.leanhduc.util.CipherException;

import javax.swing.*;

public abstract class KeyTypingPanel<T> extends JPanel {
    MainController controller;
    protected T key;

    public KeyTypingPanel(MainController controller) {
        this.controller = controller;
        this.init();
    }

    public abstract void init();

    public abstract IKeyClassic getKey() throws CipherException;

    public abstract void setKey(T key);
}
