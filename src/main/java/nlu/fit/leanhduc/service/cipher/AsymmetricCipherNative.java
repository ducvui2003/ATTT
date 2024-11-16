package nlu.fit.leanhduc.service.cipher;

import nlu.fit.leanhduc.service.key.KeyAsymmetric;
import nlu.fit.leanhduc.util.CipherException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class AsymmetricCipherNative extends AbsCipherNative<KeyAsymmetric> {
    KeyPair keyPair;
    PublicKey publicKey;
    PrivateKey privateKey;
    Cipher cipher;

    public AsymmetricCipherNative(String base64PublicKey, String base64PrivateKey, String cipher, String algorithm) throws Exception {
        super();

        this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

    }

    @Override
    public void loadKey(KeyAsymmetric key) throws CipherException {
        this.publicKey = key.getPublicKey();
        this.privateKey = key.getPrivateKey();

    }

    @Override
    public KeyAsymmetric generateKey() {
        KeyAsymmetric key = new KeyAsymmetric();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
            key.setPrivateKey(privateKey);
            key.setPublicKey(publicKey);
            return key;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean loadKey(String src) throws IOException {
        return false;
    }

    @Override
    public boolean saveKey(String dest) throws IOException {
        return false;
    }

    @Override
    public String encrypt(String plainText) throws CipherException, Exception {

        byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return conversionStrategy.convert(cipher.doFinal(plainText.getBytes()));
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
//        Cipher cipher = null;
//        try {
//            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//            cipher.init(Cipher.DECRYPT_MODE, privateKey);
//            return new String(cipher.doFinal(encryptText), StandardCharsets.UTF_8);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchPaddingException e) {
//            throw new RuntimeException(e);
//        }
        return "";
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
