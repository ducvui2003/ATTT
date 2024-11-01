package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

import nlu.fit.leanhduc.util.CipherException;

import javax.crypto.SecretKey;

public abstract class AbsCipherNative implements ICipherNative {
    protected SecretKey key;

    @Override
    public void loadKey(SecretKey key) throws CipherException {
        this.key = key;
    }
}
