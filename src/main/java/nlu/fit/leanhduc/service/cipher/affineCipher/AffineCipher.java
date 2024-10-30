package nlu.fit.leanhduc.service.cipher.affineCipher;

import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.ITextEncrypt;
import nlu.fit.leanhduc.service.key.AffineKey;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.ModularUtil;

import java.util.Random;

public abstract class AffineCipher implements IKeyGenerator<AffineKey>, ITextEncrypt {
    protected AffineKey key;
    protected Random rd;
    protected int range;

    public AffineCipher() {
        this.rd = new Random();
    }

    @Override
    public void loadKey(AffineKey key) throws CipherException {
        if (ModularUtil.findGCD(key.getA(), Constraint.ALPHABET_SIZE) != 1)
            throw new CipherException("Key is not a greatest common divisor");
        this.key = key;
    }

    @Override
    public AffineKey generateKey() {
        AffineKey key = new AffineKey();
        int a;
        do {
            a = rd.nextInt(1, this.range);
        } while (ModularUtil.findGCD(a, this.range) != 1);
        int b = rd.nextInt(this.range);
        key.setB(b);
        return key;
    }
}
