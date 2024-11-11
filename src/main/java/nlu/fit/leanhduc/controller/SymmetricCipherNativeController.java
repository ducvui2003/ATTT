package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.cipher.symmetric.cryto.Algorithm;
import nlu.fit.leanhduc.service.cipher.symmetric.cryto.SymmetricCipherNative;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class SymmetricCipherNativeController {
    private static SymmetricCipherNativeController INSTANCE;

    public static SymmetricCipherNativeController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SymmetricCipherNativeController();
        }
        return INSTANCE;
    }

    private SymmetricCipherNativeController() {
    }

    public SymmetricCipherNative getAlgorithm(String cipher, String mode, String padding, int keySize, int ivSize) throws Exception {
        Algorithm algorithm = new Algorithm(cipher, mode, padding, keySize, ivSize);
        return new SymmetricCipherNative(algorithm);
    }
}
