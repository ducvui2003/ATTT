package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.cipher.AsymmetricCipherNative;
import nlu.fit.leanhduc.service.digital.DigitalSignature;
import nlu.fit.leanhduc.service.cipher.Algorithm;
import nlu.fit.leanhduc.service.key.KeySignature;
import nlu.fit.leanhduc.util.constraint.*;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class DigitalSignatureController {
    private static DigitalSignatureController INSTANCE;
    private final String DEFAULT_CIPHER = "DSA";

    private DigitalSignatureController() {
    }

    public static DigitalSignatureController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DigitalSignatureController();
        }
        return INSTANCE;
    }

    public Map<String, String> generateKey(Hash hashFunction, Size size) throws NoSuchAlgorithmException, NoSuchProviderException {
        DigitalSignature digitalSignature = new DigitalSignature(Algorithm.of(DEFAULT_CIPHER, size, hashFunction));
        KeySignature keySignature = digitalSignature.generateKey();
        ByteConversionStrategy byteConversionStrategy = new Base64ConversionStrategy();
        return Map.of("public-key", byteConversionStrategy.convert(keySignature.getPublicKey().getEncoded()),
                "private-key", byteConversionStrategy.convert(keySignature.getPrivateKey().getEncoded()));
    }


    public String sign(String plainText, String privateKey, Hash hashFunction, Size size) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        DigitalSignature digitalSignature = new DigitalSignature(Algorithm.of(DEFAULT_CIPHER, size, hashFunction));
        digitalSignature.setPrivateKey(privateKey);
        return digitalSignature.sign(plainText);
    }

    public boolean verify(String message, String signature, String publicKey, Hash hashFunction, Size size) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        DigitalSignature digitalSignature = new DigitalSignature(Algorithm.of(DEFAULT_CIPHER, size, hashFunction));
        digitalSignature.setPublicKey(publicKey);
        return digitalSignature.verify(message, signature);
    }

    public String signFile(String src, String privateKey, Hash hashFunction, Size size) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException, IOException {
        DigitalSignature digitalSignature = new DigitalSignature(Algorithm.of(DEFAULT_CIPHER, size, hashFunction));
        digitalSignature.setPrivateKey(privateKey);
        return digitalSignature.signFile(src);
    }

    public boolean verifyFile(String src, String signature, String publicKey, Hash hashFunction, Size size) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException, IOException {
        DigitalSignature digitalSignature = new DigitalSignature(Algorithm.of(DEFAULT_CIPHER, size, hashFunction));
        digitalSignature.setPublicKey(publicKey);
        return digitalSignature.verifyFile(src, signature);
    }

    public void saveKey(String dest,
                        String base64PublicKey,
                        String base64PrivateKey,
                        Hash hashFunction,
                        Size keySize) {
        try {
            DigitalSignature digitalSignature = new DigitalSignature(base64PublicKey,
                    base64PrivateKey,
                    Algorithm.of(DEFAULT_CIPHER, keySize, hashFunction)
            );
            digitalSignature.saveKey(dest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
