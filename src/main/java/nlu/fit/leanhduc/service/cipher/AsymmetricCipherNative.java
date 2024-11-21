package nlu.fit.leanhduc.service.cipher;

import nlu.fit.leanhduc.service.key.KeyAsymmetric;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AsymmetricCipherNative extends AbsCipherNative<KeyAsymmetric> {
    KeyPair keyPair;
    Cipher cipher;

    public AsymmetricCipherNative(Algorithm algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        super(algorithm);
        this.key = new KeyAsymmetric();
        this.cipher = Cipher.getInstance(algorithm.toString(), Constraint.DEFAULT_PROVIDER);
    }

    public AsymmetricCipherNative(String base64PublicKey, String base64PrivateKey, Algorithm algorithm) throws Exception {
        super(algorithm);
        this.key = new KeyAsymmetric();
        X509EncodedKeySpec keySpecPublicKey = new X509EncodedKeySpec(this.conversionStrategy.convert(base64PublicKey));
        PKCS8EncodedKeySpec keySpecPrivateKey = new PKCS8EncodedKeySpec(this.conversionStrategy.convert(base64PrivateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm.getCipher());
        this.key.setPublicKey(keyFactory.generatePublic(keySpecPublicKey));
        this.key.setPrivateKey(keyFactory.generatePrivate(keySpecPrivateKey));
        this.cipher = Cipher.getInstance(this.algorithm.toString(), Constraint.DEFAULT_PROVIDER);
    }


    @Override
    public void loadKey(KeyAsymmetric key) throws CipherException {
        this.key = key;
    }

    @Override
    public KeyAsymmetric generateKey() {
        KeyAsymmetric key = new KeyAsymmetric();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm.getCipher());
            keyPairGenerator.initialize(algorithm.getKeySize());
            keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            key.setPrivateKey(privateKey);
            key.setPublicKey(publicKey);
            return key;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadKey(String src) throws IOException {
    }

    @Override
    public void saveKey(String dest) throws IOException {
    }

    @Override
    public String encrypt(String plainText) throws CipherException, Exception {
        byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, this.key.getPublicKey());
        return conversionStrategy.convert(cipher.doFinal(bytes));
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        try {
            byte[] bytes = Base64.getDecoder().decode(encryptText);
            cipher.init(Cipher.DECRYPT_MODE, this.key.getPrivateKey());
            return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | InvalidKeyException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean encrypt(String src, String dest) throws CipherException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, FileNotFoundException {
        return super.encrypt(src, dest);
    }

    @Override
    public boolean decrypt(String src, String dest) throws CipherException {
        return super.decrypt(src, dest);
    }
}
