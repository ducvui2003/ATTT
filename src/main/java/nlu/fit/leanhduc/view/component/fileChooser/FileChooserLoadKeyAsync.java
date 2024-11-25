package nlu.fit.leanhduc.view.component.fileChooser;

import nlu.fit.leanhduc.controller.AsymmetricCipherController;
import nlu.fit.leanhduc.controller.SymmetricCipherNativeController;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.view.section.AsymmetricCipherSection;
import nlu.fit.leanhduc.view.section.SymmetricCipherSection;

import javax.swing.*;
import java.io.File;
import java.util.Map;

public class FileChooserLoadKeyAsync extends FileChooserButton implements FileChooserEvent {
    AsymmetricCipherSection section;

    public FileChooserLoadKeyAsync(AsymmetricCipherSection section, String text, ImageIcon icon) {
        super(text, icon);
        this.section = section;
        this.setEvent(this);
    }

    @Override
    public void onFileSelected(File file) {
        Map<String, String> keyInfo = AsymmetricCipherController.getInstance().loadKey(file.getAbsolutePath());
        Cipher cipher = Cipher.valueOf(keyInfo.get("cipher"));
        Mode mode = Mode.valueOf(keyInfo.get("mode"));
        Padding padding = Padding.valueOf(keyInfo.get("padding"));
        Size keySize = Size.valueOfBit(Integer.parseInt(keyInfo.get("key-size")));
        String base64PublicKey = keyInfo.get("public-key");
        String base64PrivateKey = keyInfo.get("private-key");
        section.updateComboBox(cipher, mode, padding, keySize);
        section.updateKey(base64PublicKey, base64PrivateKey);
        JOptionPane.showMessageDialog(section, "Tải khóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    public void onFileUnselected() {

    }

    @Override
    public void onError(String message) {

    }

}
