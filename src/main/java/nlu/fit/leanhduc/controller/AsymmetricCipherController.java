package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.cipher.Algorithm;
import nlu.fit.leanhduc.service.cipher.AsymmetricCipherNative;
import nlu.fit.leanhduc.service.cipher.SymmetricCipherNative;
import nlu.fit.leanhduc.service.key.KeyAsymmetric;
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

    public Map<String, String> generateKey(Cipher cipher, Mode mode, Padding padding, Size keySize) throws Exception {
        if (Mode.NONE.equals(mode))
            mode = Constraint.DEFAULT_MODE;
        Algorithm algorithm = new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getBit());
        KeyAsymmetric key = new AsymmetricCipherNative(algorithm).generateKey();
        ByteConversionStrategy byteConversionStrategy = new Base64ConversionStrategy();
        return Map.of(
                "public-key", byteConversionStrategy.convert(key.getPublicKey().getEncoded()),
                "private-key", byteConversionStrategy.convert(key.getPrivateKey().getEncoded()));
    }

    public String encrypt(String base64SecretKey,
                          String base64PrivateKey,
                          String plainText,
                          Cipher cipher,
                          Mode mode,
                          Padding padding,
                          Size keySize,
                          boolean usePublicKeyEncryption
    ) throws Exception {
        AsymmetricCipherNative symmetricCipherNative = new AsymmetricCipherNative(base64SecretKey, base64PrivateKey,
                new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getByteFormat()));
        symmetricCipherNative.setEncryptByPublicKey(usePublicKeyEncryption);
        return symmetricCipherNative.encrypt(plainText);
    }

    public String decrypt(String base64SecretKey,
                          String base64PrivateKey,
                          String cipherText,
                          Cipher cipher,
                          Mode mode,
                          Padding padding,
                          Size keySize,
                          boolean usePublicKeyEncryption
    ) throws Exception {
        AsymmetricCipherNative symmetricCipherNative = new AsymmetricCipherNative(base64SecretKey, base64PrivateKey,
                new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getByteFormat()));
        symmetricCipherNative.setEncryptByPublicKey(usePublicKeyEncryption);
        return symmetricCipherNative.decrypt(cipherText);
    }

    public Map<String, String> loadKey(String src) {
        AsymmetricCipherNative symmetricCipherNative = new AsymmetricCipherNative();
        try {
            symmetricCipherNative.loadKey(src);
            ByteConversionStrategy byteConversionStrategy = new Base64ConversionStrategy();
            return Map.of(
                    "public-key", byteConversionStrategy.convert(symmetricCipherNative.getKey().getPublicKey().getEncoded()),
                    "private-key", byteConversionStrategy.convert(symmetricCipherNative.getKey().getPrivateKey().getEncoded()),
                    "cipher", symmetricCipherNative.getKey().getCipher(),
                    "mode", symmetricCipherNative.getKey().getMode(),
                    "padding", symmetricCipherNative.getKey().getPadding(),
                    "key-size", String.valueOf(symmetricCipherNative.getKey().getKeySize()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveKey(String dest,
                        String base64PublicKey,
                        String base64PrivateKey,
                        Cipher cipher,
                        Mode mode,
                        Padding padding,
                        Size keySize) {
        try {
            AsymmetricCipherNative symmetricCipherNative = new AsymmetricCipherNative(base64PublicKey,
                    base64PrivateKey,
                    new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getBit())
            );
            symmetricCipherNative.saveKey(dest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
