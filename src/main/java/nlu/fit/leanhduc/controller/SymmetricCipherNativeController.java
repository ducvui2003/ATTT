package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.cipher.symmetric.Algorithm;
import nlu.fit.leanhduc.service.cipher.symmetric.SymmetricCipherNative;
import nlu.fit.leanhduc.service.key.KeySymmetric;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

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

    public SymmetricCipherNative getAlgorithm(Cipher cipher, Mode mode, Padding padding, Size keySize, Size ivSize) throws Exception {
        Algorithm algorithm = new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getByteFormat(), ivSize.getByteFormat());
        return new SymmetricCipherNative(algorithm);
    }

    public String encrypt(SymmetricCipherNative cipher, String plainText) throws Exception {
        return cipher.encrypt(plainText);
    }

    public String generateKey(SymmetricCipherNative cipher) {
        KeySymmetric key = cipher.generateKey();
        ByteConversionStrategy byteConversionStrategy = new Base64ConversionStrategy();
        return byteConversionStrategy.convert(key.getSecretKey().getEncoded()) + "_" + byteConversionStrategy.convert(iv.getIV())
    }
}
