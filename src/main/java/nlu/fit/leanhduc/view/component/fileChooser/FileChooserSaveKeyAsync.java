package nlu.fit.leanhduc.view.component.fileChooser;

import nlu.fit.leanhduc.controller.AsymmetricCipherController;
import nlu.fit.leanhduc.controller.SymmetricCipherNativeController;
import nlu.fit.leanhduc.view.section.AsymmetricCipherSection;
import nlu.fit.leanhduc.view.section.SymmetricCipherSection;

import javax.swing.*;
import java.io.File;

public class FileChooserSaveKeyAsync extends FileChooserButton implements FileChooserEvent {
    AsymmetricCipherSection section;

    public FileChooserSaveKeyAsync(AsymmetricCipherSection section, String text, ImageIcon icon) {
        super(text, icon);
        this.section = section;
        this.setEvent(this);
    }

    @Override
    public void onFileSelected(File file) {
        try {
            AsymmetricCipherController.getInstance().saveKey(
                    file.getAbsolutePath(),
                    section.getPublicKey(),
                    section.getPrivateKey(),
                    section.getSelectedCipher(),
                    section.getSelectedMode(),
                    section.getSelectedPadding(),
                    section.getSelectedKeySize());
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
        JOptionPane.showMessageDialog(section, "Lưu khóa không thành công", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
    }
}
