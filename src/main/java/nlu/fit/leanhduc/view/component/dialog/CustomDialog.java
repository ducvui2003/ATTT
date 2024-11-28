package nlu.fit.leanhduc.view.component.dialog;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nlu.fit.leanhduc.util.Constraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class CustomDialog extends JDialog {
    Frame owner;

    public CustomDialog(Frame owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        this.owner = owner;
        this.init();
        this.handleClose();
        this.setMetadata();
    }

    public void handleClose() {
        Dialog dialog = this;
         
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    dialog.dispose();
            }
        });
    }

    public abstract void init();

    public void openDialog() {
        this.setVisible(true);
    }

    private void setMetadata() {
        this.setLocationRelativeTo(owner);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
