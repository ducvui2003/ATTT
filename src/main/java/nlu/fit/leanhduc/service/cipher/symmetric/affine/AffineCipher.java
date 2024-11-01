package nlu.fit.leanhduc.service.cipher.symmetric.affine;

import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.ITextEncrypt;
import nlu.fit.leanhduc.service.key.AffineKey;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.ModularUtil;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.util.Random;

public abstract class AffineCipher implements IKeyGenerator<AffineKey>, ITextEncrypt {
    protected AffineKey key;
    protected Random rd;
    protected int range;
    protected AlphabetUtil alphabetUtil;

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

    @Override
    public String encrypt(String plainText) throws CipherException {
        char[] plainTextArray = plainText.toCharArray();
        String result = "";
        for (char c : plainTextArray) {
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                //Công thức: Encrypt (x) = (a * x + b) mod m
                int index = this.alphabetUtil.indexOf(c);
                int charEncrypt = this.key.getA() * index + this.key.getB();
                result += alphabetUtil.getChar(charEncrypt, isLower);
            } else
                result += c;
        }
        return result;
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        char[] plainTextArray = encryptText.toCharArray();
        String result = "";
        int modularInverse = ModularUtil.modularInverse(this.key.getA(), this.range);
        for (char c : plainTextArray) {
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                //Công thức: Decrypt (x) = a^(-1) * (x - b)
                int index = this.alphabetUtil.indexOf(c);
                int charDecrypt = modularInverse * (index - this.key.getB());
                result += alphabetUtil.getChar(charDecrypt, isLower);
            } else
                result += c;
        }
        return result;
    }
}
