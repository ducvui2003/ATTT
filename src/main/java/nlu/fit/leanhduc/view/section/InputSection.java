package nlu.fit.leanhduc.view.section;

import nlu.fit.leanhduc.controller.EncryptPublisher;
import nlu.fit.leanhduc.controller.TaskPublisher;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooser;
import nlu.fit.leanhduc.view.component.fileChooser.FileChooserEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;

public class InputSection extends JPanel implements FileChooserEvent {
    FileChooser fileChooser;
    TaskPublisher encryptPublisher;

    public InputSection() {
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        fileChooser = new FileChooser();
        fileChooser.setEvent(this);
        this.add(fileChooser);
        Border border = BorderFactory.createTitledBorder("Input");
        this.setBorder(border);
        encryptPublisher = new EncryptPublisher();
    }

    @Override
    public void onFileSelected(File file) {
        encryptPublisher.doTask();
    }

    @Override
    public void onFileUnselected() {
        fileChooser.cancel();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public boolean onBeforeFileSelected() {
        return false;
    }
}
