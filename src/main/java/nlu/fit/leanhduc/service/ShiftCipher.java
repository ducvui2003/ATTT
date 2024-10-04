package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;

import java.io.File;

public class ShiftCipher implements IAsymmetricEncrypt<Integer> {

    @Override
    public String encrypt(String plainText, Integer publicKey) throws CipherException {
        if (plainText == null || plainText.isEmpty()) throw new CipherException("Plain text is empty");
        if (publicKey == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            if (Character.isLetter(plainText.charAt(i))) {
                char base = Character.isLowerCase(c) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
                char ce = (char) ((c - base + publicKey) % Constraint.ALPHABET_SIZE + base);
                result += ce;
            } else {
                result += c;
            }
        }
        return result;
    }

    @Override
    public String decrypt(String plainText, Integer publicKey) throws CipherException {
        if (plainText == null) throw new CipherException("Plain text is null");
        if (publicKey == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            if (Character.isLetter(plainText.charAt(i))) {
                char base = Character.isLowerCase(c) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
                char ce = (char) ((c - base - publicKey) % Constraint.ALPHABET_SIZE + base);
                result += ce;
            } else {
                result += c;
            }
        }
        return result;
    }

    @Override
    public String encrypt(File file, Integer publicKey) throws CipherException {
        return null;
    }

    @Override
    public String decrypt(File file, Integer publicKey) throws CipherException {
        return null;
    }
}
