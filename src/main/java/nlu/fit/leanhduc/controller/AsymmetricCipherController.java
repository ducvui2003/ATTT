package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.cipher.Algorithm;
import nlu.fit.leanhduc.service.cipher.SymmetricCipherNative;

public class AsymmetricCipherController {
    private static AsymmetricCipherController INSTANCE;

    public static AsymmetricCipherController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AsymmetricCipherController();
        }
        return INSTANCE;
    }

    private AsymmetricCipherController() {
    }

    public SymmetricCipherNative getAlgorithm(String cipher, String mode, String padding, int keySize, int ivSize) throws Exception {
        Algorithm algorithm = new Algorithm(cipher, mode, padding, keySize, ivSize);
        return new SymmetricCipherNative(algorithm);
    }
}
