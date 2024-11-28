package nlu.fit.leanhduc.service.cipher.classic;

import lombok.Getter;
import nlu.fit.leanhduc.service.key.classic.AffineKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.ModularUtil;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.util.Random;

@Getter
public class AffineCipher extends AbsClassicCipher<AffineKeyClassic> {
    protected Random rd;
    protected int range;

    public AffineCipher(AlphabetUtil alphabetUtil) {
        super(alphabetUtil);
        this.rd = new Random();
        this.range = this.alphabetUtil.getLength();
    }

    @Override
    public void loadKey(AffineKeyClassic key) throws CipherException {
        super.loadKey(key);
        if (ModularUtil.findGCD(key.getA(), this.alphabetUtil.getLength()) != 1)
            throw new CipherException("Ước chung lớn nhất của a và b phải bằng 1");
        this.key = key;
    }

    @Override
    public AffineKeyClassic generateKey() {
        AffineKeyClassic key = new AffineKeyClassic();
        int a;
        do {
            a = rd.nextInt(1, this.range);
        } while (ModularUtil.findGCD(a, this.range) != 1);
        int b = rd.nextInt(1, this.range);
        key.setA(a);
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
