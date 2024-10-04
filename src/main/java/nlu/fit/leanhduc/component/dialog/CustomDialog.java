package nlu.fit.leanhduc.component.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CustomDialog extends JDialog {
    public CustomDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        this.setLocationRelativeTo(owner);
        this.handleClose();
    }

    void handleClose() {
        Dialog dialog = this;
        // Add a KeyListener to handle key events
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    dialog.dispose();
            }
        });
    }
}
