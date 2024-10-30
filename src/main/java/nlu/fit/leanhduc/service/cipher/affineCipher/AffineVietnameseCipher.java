package nlu.fit.leanhduc.service.cipher.affineCipher;

import nlu.fit.leanhduc.util.*;

public class AffineVietnameseCipher extends AffineCipher {
    public AffineVietnameseCipher() {
        super();
        this.range = Constraint.VIET_NAME_ALPHA_BET_SIZE;
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        char[] plainTextArray = plainText.toCharArray();
        String result = "";
        for (char c : plainTextArray) {
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                //Công thức: Encrypt (x) = (a * x + b) mod m
                int charEncrypt = this.key.getA() * VietnameseAlphabetUtil.indexOf(c) + this.key.getB();
                result += VietnameseAlphabetUtil.getChar(charEncrypt, isLower);
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
                int charDecrypt = modularInverse * (VietnameseAlphabetUtil.indexOf(c) - this.key.getB());
                result += VietnameseAlphabetUtil.getChar(charDecrypt, isLower);
            } else
                result += c;
        }
        return result;
    }
}
