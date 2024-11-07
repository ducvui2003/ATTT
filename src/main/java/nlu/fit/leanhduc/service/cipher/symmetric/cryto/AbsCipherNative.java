package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

import nlu.fit.leanhduc.util.CipherException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public abstract class AbsCipherNative implements ICipherNative {
    protected SecretKey secretKey;
    protected Algorithm algorithm;

    @Override
    public void loadKey(SecretKey secretKey) throws CipherException {
        this.secretKey = secretKey;
    }

    @Override
    public SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.toString());
            keyGenerator.init(algorithm.getKeySize());
            secretKey = keyGenerator.generateKey();
            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
