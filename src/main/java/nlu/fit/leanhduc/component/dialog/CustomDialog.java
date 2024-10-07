package nlu.fit.leanhduc.component.dialog;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomDialog extends JDialog {
     Frame owner;
     String title;
     boolean modal;

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
