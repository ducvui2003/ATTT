package nlu.fit.leanhduc.view.component.fileChooser;

import nlu.fit.leanhduc.controller.SymmetricCipherNativeController;
import nlu.fit.leanhduc.view.section.SymmetricCipherSection;

import javax.swing.*;
import java.io.File;

public class FileChooserSaveKeySync extends FileChooserButton implements FileChooserEvent {
    SymmetricCipherSection section;

    public FileChooserSaveKeySync(SymmetricCipherSection section, String text, ImageIcon icon) {
        super(text, icon);
        this.section = section;
        this.setEvent(this);
    }

    @Override
    public void onFileSelected(File file) {
        SymmetricCipherNativeController.getInstance().saveKey(file.getAbsolutePath(),
                section.getBase64SecretKey(),
                section.getBase64Iv(),
                section.getSelectedCipher(),
                section.getSelectedMode(),
                section.getSelectedPadding(),
                section.getSelectedKeySize(),
                section.getSelectedIVSize());
        JOptionPane.showMessageDialog(section, "Lưu khóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onFileUnselected() {

    }

    @Override
    public void onError(String message) {
        JOptionPane.showMessageDialog(section, "Lưu khóa không thành công", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
    }
}
