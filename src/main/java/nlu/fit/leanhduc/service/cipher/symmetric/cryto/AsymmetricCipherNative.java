package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

import nlu.fit.leanhduc.util.CipherException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.FileNotFoundException;
import java.security.*;

public class AsymmetricCipherNative extends AbsCipherNative {
    KeyPair keyPair;
    PublicKey publicKey;
    PrivateKey privateKey;

    public AsymmetricCipherNative(Algorithm algorithm) {
        super(algorithm);
    }

    @Override
    public byte[] encrypt(String data) throws Exception {
        return new byte[0];
    }

    @Override
    public String decrypt(byte[] data) throws Exception {
        return "";
    }

    @Override
    public boolean encrypt(String src, String dest) throws CipherException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, FileNotFoundException {
        return false;
    }

    @Override
    public boolean decrypt(String src, String dest) throws CipherException {
        return false;
    }

    @Override
    public void loadKey(SecretKey key) throws CipherException {

    }

    @Override
    public SecretKey generateKey() {
        return null;
    }
}
