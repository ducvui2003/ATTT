package nlu.fit.leanhduc.service.cipher.vigenereCipher;

import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.VietnameseAlphabetUtil;

import java.util.ArrayList;
import java.util.List;

public class VigenereVietnameseCipher extends VigenereCipher {
    @Override
    public List<Integer> generateKey() {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < keyLength; i++)
            results.add(rd.nextInt(Constraint.VIET_NAME_ALPHA_BET_SIZE) * Constraint.VIET_NAME_ALPHA_BET_SIZE);
        return results;
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        if (plainText == null) throw new CipherException("Plain text is null");
        if (super.keys == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            char cShifted = encryptLetter(this.keys.get(i % keyLength), c);
            result += cShifted;
        }
        return result;
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        if (encryptText == null) throw new CipherException("Plain text is null");
        if (super.keys == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < encryptText.length(); i++) {
            char c = encryptText.charAt(i);
            char cShifted = decryptLetter(this.keys.get(i % keyLength), c);
            result += cShifted;
        }
        return result;
    }

    private char encryptLetter(int shift, char ch) {
        char result = ch;
        if (Character.isLetter(result)) {
            boolean isLower = Character.isLowerCase(ch);
            int index = VietnameseAlphabetUtil.indexOf(ch);
            int indexOfEncrypt = (index + shift) % Constraint.VIET_NAME_ALPHA_BET_SIZE;
            result = VietnameseAlphabetUtil.getChar(indexOfEncrypt);
            if (!isLower) result = Character.toUpperCase(result);
        }
        return result;
    }

    private char decryptLetter(int shift, char ch) {
        char result = ch;
        if (Character.isLetter(result)) {
            boolean isLower = Character.isLowerCase(ch);
            int index = VietnameseAlphabetUtil.indexOf(Character.toLowerCase(result));
            int indexOfDecrypt;
            if (index - shift < 0)
                indexOfDecrypt = (index - shift + Constraint.VIET_NAME_ALPHA_BET_SIZE) % Constraint.VIET_NAME_ALPHA_BET_SIZE;
            else
                indexOfDecrypt = (index - shift) % Constraint.VIET_NAME_ALPHA_BET_SIZE;
            result = VietnameseAlphabetUtil.getChar(indexOfDecrypt);
            if (!isLower) result = Character.toUpperCase(result);
        }
        return result;
    }
}
