package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.cipher.symmetric.AbsCipherNative;
import nlu.fit.leanhduc.service.cipher.symmetric.SymmetricCipherNative;
import nlu.fit.leanhduc.util.FileUtil;
import nlu.fit.leanhduc.util.constraint.Cipher;

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
