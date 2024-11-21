package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.cipher.Algorithm;
import nlu.fit.leanhduc.service.cipher.AsymmetricCipherNative;
import nlu.fit.leanhduc.service.cipher.SymmetricCipherNative;
import nlu.fit.leanhduc.service.key.KeyAsymmetric;
import nlu.fit.leanhduc.service.key.KeySymmetric;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

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
                          Size keySize
    ) throws Exception {
        AsymmetricCipherNative symmetricCipherNative = new AsymmetricCipherNative(base64SecretKey, base64PrivateKey,
                new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getByteFormat()));
        return symmetricCipherNative.encrypt(plainText);
    }

    public String decrypt(String base64SecretKey,
                          String base64PrivateKey,
                          String cipherText,
                          Cipher cipher,
                          Mode mode,
                          Padding padding,
                          Size keySize
    ) throws Exception {
        AsymmetricCipherNative symmetricCipherNative = new AsymmetricCipherNative(base64SecretKey, base64PrivateKey,
                new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getByteFormat()));
        return symmetricCipherNative.decrypt(cipherText);
    }
}
