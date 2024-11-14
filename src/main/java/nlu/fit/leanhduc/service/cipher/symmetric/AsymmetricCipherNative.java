package nlu.fit.leanhduc.service.cipher.symmetric;

import nlu.fit.leanhduc.service.key.KeyAsymmetric;
import nlu.fit.leanhduc.util.CipherException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;

public class AsymmetricCipherNative extends AbsCipherNative<KeyAsymmetric> {
    KeyPair keyPair;
    PublicKey publicKey;
    PrivateKey privateKey;

    @Override
    public void loadKey(KeyAsymmetric key) throws CipherException {

    }

    @Override
    public KeyAsymmetric generateKey() {
        return null;
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
        return "";
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
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
