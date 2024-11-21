package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.cipher.Algorithm;
import nlu.fit.leanhduc.service.cipher.SymmetricCipherNative;
import nlu.fit.leanhduc.service.key.KeySymmetric;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

import java.io.IOException;
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

    public String encrypt(SymmetricCipherNative cipher, String plainText) throws Exception {
        return cipher.encrypt(plainText);
    }

    public Map<String, String> generateKey(Cipher cipher, Mode mode, Padding padding, Size keySize, Size ivSize) throws Exception {
        if (Mode.NONE.equals(mode))
            mode = Constraint.DEFAULT_MODE;
        Algorithm algorithm = new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getBit(), ivSize.getBit());
        KeySymmetric key = new SymmetricCipherNative(algorithm).generateKey();
        ByteConversionStrategy byteConversionStrategy = new Base64ConversionStrategy();
        return Map.of(
                "secret-key", byteConversionStrategy.convert(key.getSecretKey().getEncoded()),
                "iv", byteConversionStrategy.convert(key.getIv().getIV()),
                "cipher", algorithm.getCipher());
    }

    public String encrypt(String base64SecretKey,
                          String base64Iv,
                          String plainText,
                          Cipher cipher,
                          Mode mode,
                          Padding padding,
                          Size keySize,
                          Size ivSize
    ) throws Exception {
        SymmetricCipherNative symmetricCipherNative = new SymmetricCipherNative(base64SecretKey,
                base64Iv,
                new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getByteFormat(), ivSize.getBit()));
        return symmetricCipherNative.encrypt(plainText);
    }

    public boolean encryptFile(String base64SecretKey,
                               String base64Iv,
                               String src,
                               String dest,
                               Cipher cipher,
                               Mode mode,
                               Padding padding,
                               Size keySize,
                               Size ivSize
    ) throws Exception {
        SymmetricCipherNative symmetricCipherNative = new SymmetricCipherNative(base64SecretKey,
                base64Iv,
                new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getByteFormat(), ivSize.getBit()));
        return symmetricCipherNative.encrypt(src, dest);
    }

    public String decrypt(String base64SecretKey,
                          String base64Iv,
                          String cipherText,
                          Cipher cipher,
                          Mode mode,
                          Padding padding,
                          Size keySize,
                          Size ivSize) throws Exception {
        SymmetricCipherNative symmetricCipherNative = new SymmetricCipherNative(base64SecretKey,
                base64Iv,
                new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getByteFormat(), ivSize.getBit()));
        return symmetricCipherNative.decrypt(cipherText);
    }

    public boolean decryptFile(String base64SecretKey,
                               String base64Iv,
                               String src,
                               String dest,
                               Cipher cipher,
                               Mode mode,
                               Padding padding,
                               Size keySize,
                               Size ivSize) throws Exception {
        SymmetricCipherNative symmetricCipherNative = new SymmetricCipherNative(base64SecretKey,
                base64Iv,
                new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getByteFormat(), ivSize.getBit()));
        return symmetricCipherNative.decrypt(src, dest);
    }

    public Map<String, String> loadKey(String src) {
        SymmetricCipherNative symmetricCipherNative = new SymmetricCipherNative();
        try {
            symmetricCipherNative.loadKey(src);
            ByteConversionStrategy byteConversionStrategy = new Base64ConversionStrategy();
            return Map.of(
                    "secret-key", byteConversionStrategy.convert(symmetricCipherNative.getKey().getSecretKey().getEncoded()),
                    "iv", byteConversionStrategy.convert(symmetricCipherNative.getKey().getIv().getIV()),
                    "cipher", symmetricCipherNative.getKey().getCipher(),
                    "mode", symmetricCipherNative.getKey().getMode(),
                    "padding", symmetricCipherNative.getKey().getPadding(),
                    "key-size", String.valueOf(symmetricCipherNative.getKey().getKeySize()),
                    "iv-size", String.valueOf(symmetricCipherNative.getKey().getIvSize()));
        } catch (IOException | CipherException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveKey(String dest,
                        String base64SecretKey,
                        String base64Iv,
                        Cipher cipher,
                        Mode mode,
                        Padding padding,
                        Size keySize,
                        Size ivSize) {
        try {
            SymmetricCipherNative symmetricCipherNative = new SymmetricCipherNative(base64SecretKey,
                    base64Iv,
                    new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getBit(), ivSize.getBit())
            );
            symmetricCipherNative.saveKey(dest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
