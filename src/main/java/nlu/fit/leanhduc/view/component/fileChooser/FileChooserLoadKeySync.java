package nlu.fit.leanhduc.view.component.fileChooser;

import nlu.fit.leanhduc.controller.SymmetricCipherNativeController;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.view.section.SymmetricCipherSection;

import javax.swing.*;
import java.io.File;
import java.util.Map;

public class FileChooserLoadKeySync extends FileChooserButton implements FileChooserEvent {
    SymmetricCipherSection section;

    public FileChooserLoadKeySync(SymmetricCipherSection section, String text, ImageIcon icon) {
        super(text, icon);
        this.section = section;
        this.setEvent(this);
    }

    @Override
    public void onFileSelected(File file) {
        Map<String, String> keyInfo = SymmetricCipherNativeController.getInstance().loadKey(file.getAbsolutePath());
        Cipher cipher = Cipher.valueOf(keyInfo.get("cipher"));
        Mode mode = Mode.valueOf(keyInfo.get("mode"));
        Padding padding = Padding.valueOf(keyInfo.get("padding"));
        Size keySize = Size.valueOfBit(Integer.parseInt(keyInfo.get("key-size")));
        Size ivSize = Size.valueOfBit(Integer.parseInt(keyInfo.get("iv-size")));
        String base64SecretKey = keyInfo.get("secret-key");
        String base64Iv = keyInfo.get("iv");
        section.updateComboBox(cipher, mode, padding, keySize, ivSize);
        section.updateKeyBase64(base64SecretKey, base64Iv);
        JOptionPane.showMessageDialog(section, "Tải khóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onFileUnselected() {

    }

    @Override
    public void onError(String message) {

    }

}
