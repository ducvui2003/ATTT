package nlu.fit.leanhduc.service.vigenereCipher;

import lombok.Getter;
import lombok.Setter;
import nlu.fit.leanhduc.service.IAsymmetricEncrypt;
import nlu.fit.leanhduc.util.CipherException;

import java.io.File;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public abstract class VigenereCipher implements IAsymmetricEncrypt<List<Integer>> {
    protected List<Integer> keys;
    protected int keyLength;
    protected Random rd = new Random();

    @Override
    public void loadKey(List<Integer> key) throws CipherException {
        this.keys = key;
        this.keyLength = key.size();
    }

    @Override
    public String encrypt(File file) throws CipherException {
        return null;
    }

    @Override
    public String decrypt(File cryptFile) throws CipherException {
        return null;
    }
}
