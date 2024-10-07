package nlu.fit.leanhduc.service.shiftCiper;

import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.VietnameseAlphabetUtil;

import java.io.File;
import java.util.Random;

public class ShiftCipherEnglish extends ShiftCipher {
    public ShiftCipherEnglish(Integer shift) {
        super(shift);
    }

    @Override
    public Integer generateKey() {
        Random rd = new Random();
        return rd.nextInt(Constraint.ALPHABET_SIZE) + 1;
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        if (plainText == null) throw new CipherException("Plain text is null");
        if (this.shift == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            char ce = c;
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
                ce = (char) ((c - base + shift) % Constraint.ALPHABET_SIZE + base);
            }
            result += ce;
        }
        return result;
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        if (encryptText == null) throw new CipherException("Plain text is null");
        if (this.shift == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < encryptText.length(); i++) {
            char c = encryptText.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
                int dec = c - base - this.shift;
                if (dec < 0) dec += Constraint.ALPHABET_SIZE;
                char ce = (char) (dec % Constraint.ALPHABET_SIZE + base);
                result += ce;
            } else {
                result += c;
            }
        }
        return result;
    }
}
