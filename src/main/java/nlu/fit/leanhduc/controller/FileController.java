package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.cipher.AbsCipherNative;
import nlu.fit.leanhduc.service.cipher.SymmetricCipherNative;
import nlu.fit.leanhduc.util.FileUtil;

import javax.crypto.SecretKey;

public class FileController {
    public static FileController INSTANCE;

    private FileController() {
    }

    public static FileController getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new FileController();
        }
        return INSTANCE;
    }

    public AbsCipherNative loadKeySync(String fileName, String algorithm) throws Exception {
        AbsCipherNative cipherNative = new SymmetricCipherNative();
        SecretKey secretKey = FileUtil.loadKeyFromFile(fileName, algorithm);
        cipherNative.loadKey(secretKey);
        return cipherNative;
    }

    public boolean saveKeySync() {
        return false;
    }
}
