package nlu.fit.leanhduc.service.cipher;

import nlu.fit.leanhduc.service.key.KeyAsymmetric;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.FileUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class AsymmetricCipherNative extends AbsCipherNative<KeyAsymmetric> {
    KeyPair keyPair;
    Cipher cipher;
    boolean encryptByPublicKey = true;

    public AsymmetricCipherNative() {
    }

    public AsymmetricCipherNative(Algorithm algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        super(algorithm);
        this.key = new KeyAsymmetric();
        this.cipher = Cipher.getInstance(algorithm.toString(), Constraint.DEFAULT_PROVIDER);
    }

    public AsymmetricCipherNative(String base64PublicKey, String base64PrivateKey, Algorithm algorithm) throws Exception {
        super(algorithm);
        this.key = new KeyAsymmetric();
        KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm.getCipher());
        this.cipher = Cipher.getInstance(this.algorithm.toString(), Constraint.DEFAULT_PROVIDER);
        this.key.setCipher(algorithm.getCipher());
        this.key.setPublicKey(keyFactory, base64PublicKey);
        this.key.setPrivateKey(keyFactory, base64PrivateKey);
        this.key.setMode(algorithm.getMode());
        this.key.setPadding(algorithm.getPadding());
        this.key.setKeySize(algorithm.getKeySize());
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
        this.key = FileUtil.loadKeyAsymmetric(src);
    }

    @Override
    public void saveKey(String dest) throws IOException {
        FileUtil.saveKey(key, dest);
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        try {
            byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
            initCipherEncrypt();
            return conversionStrategy.convert(cipher.doFinal(bytes));
        } catch (IllegalBlockSizeException |
                 BadPaddingException ignored) {
            throw new RuntimeException(ignored);
        }
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        try {
            byte[] bytes = Base64.getDecoder().decode(encryptText);
            initCipherDecrypt();
            return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean encrypt(String src, String dest) throws CipherException {
        return super.encrypt(src, dest);
    }

    @Override
    public boolean decrypt(String src, String dest) throws CipherException {
        return super.decrypt(src, dest);
    }

    private void initCipherEncrypt() {
        try {
            if (encryptByPublicKey) {
                cipher.init(Cipher.ENCRYPT_MODE, key.getPublicKey());
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key.getPrivateKey());
            }
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private void initCipherDecrypt() {
        try {
            if (encryptByPublicKey) {
                cipher.init(Cipher.DECRYPT_MODE, key.getPrivateKey());
            } else {
                cipher.init(Cipher.DECRYPT_MODE, key.getPublicKey());
            }
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEncryptByPublicKey(boolean encryptByPublicKey) {
        this.encryptByPublicKey = encryptByPublicKey;
    }
}
