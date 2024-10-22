package nlu.fit.leanhduc.service.vigenereCipher;

import lombok.Getter;
import lombok.Setter;
import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.ITextEncrypt;
import nlu.fit.leanhduc.util.CipherException;

import java.io.File;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public abstract class VigenereCipher implements ITextEncrypt, IKeyGenerator<List<Integer>> {
    protected List<Integer> keys;
    protected int keyLength;
    protected Random rd = new Random();

    @Override
    public void loadKey(List<Integer> key) throws CipherException {
        this.keys = key;
        this.keyLength = key.size();
    }
}
