package nlu.fit.leanhduc.view.component.fileChooser;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.controller.DigitalSignatureController;
import nlu.fit.leanhduc.view.section.DigitalSignatureSection;

import javax.swing.*;
import java.io.File;

public class FileChooserSaveKeySignature extends FileChooserButton implements FileChooserEvent {

    DigitalSignatureSection section;

    public FileChooserSaveKeySignature(DigitalSignatureSection section, String text) {
        super(text, MetadataConfig.getINSTANCE().getSaveIcon());
        this.section = section;
        this.setEvent(this);
    }

    @Override
    public void onFileSelected(File file) {
        try {
            DigitalSignatureController.getInstance().saveKey(
                    file.getAbsolutePath(),
                    section.getPublicKey(),
                    section.getPrivateKey(),
                    section.getSelectedHash(),
                    section.getKeySize());
            JOptionPane.showMessageDialog(this, "Đã lưu khóa thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onFileUnselected() {

    }

    @Override
    public void onError(String message) {

    }
}
