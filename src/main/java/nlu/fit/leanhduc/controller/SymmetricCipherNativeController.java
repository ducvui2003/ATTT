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

import java.util.Map;

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
        Algorithm algorithm = new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getBit(), ivSize.getByteFormat());
        return new SymmetricCipherNative(algorithm);
    }

    public SymmetricCipherNative getAlgorithm(Cipher cipher, Mode mode, Padding padding, Size keySize) throws Exception {
        Algorithm algorithm = new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getBit());
        return new SymmetricCipherNative(algorithm);
    }

    public String encrypt(SymmetricCipherNative cipher, String plainText) throws Exception {
        return cipher.encrypt(plainText);
    }

    public Map<String, String> generateKey(Cipher cipher, Mode mode, Padding padding, Size keySize, Size ivSize) throws Exception {
        Algorithm algorithm = new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getBit(), ivSize.getByteFormat());
        KeySymmetric key = new SymmetricCipherNative(algorithm).generateKey();
        ByteConversionStrategy byteConversionStrategy = new Base64ConversionStrategy();
        return Map.of(
                "secret-key", byteConversionStrategy.convert(key.getSecretKey().getEncoded()),
                "iv", byteConversionStrategy.convert(key.getIv().getIV()),
                "algorithm", algorithm.toString(),
                "cipher", algorithm.getCipher());
    }

    public String encrypt(String base64SecretKey, String base64Iv, String plainText, String cipher, String algorithm) throws Exception {
        SymmetricCipherNative symmetricCipherNative = new SymmetricCipherNative(base64SecretKey, base64Iv, cipher, algorithm);
        return symmetricCipherNative.encrypt(plainText);
    }

    public String decrypt(String base64SecretKey, String base64Iv, String cipherText, String cipher, String algorithm) throws Exception {
        SymmetricCipherNative symmetricCipherNative = new SymmetricCipherNative(base64SecretKey, base64Iv, cipher, algorithm);
        return symmetricCipherNative.decrypt(cipherText);
    }

    private Map<String, String> convertAlgorithm(String algorithm) {
        String cipher = algorithm.split("/")[0];
        String mode = algorithm.split("/")[1];
        String padding = algorithm.split("/")[2];
        return Map.of("cipher", cipher, "mode", mode, "padding", padding);
    }
}
