package nlu.fit.leanhduc.service.vigenereCipher;

import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;

import java.util.ArrayList;
import java.util.List;

public class VigenereEnglishCipher extends VigenereCipher {


    @Override
    public List<Integer> generateKey() {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < keyLength; i++)
            results.add(rd.nextInt(Constraint.ALPHABET_SIZE) * Constraint.ALPHABET_SIZE);
        return results;
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        if (plainText == null) throw new CipherException("Plain text is null");
        if (this.keys == null) throw new CipherException("Invalid public key");
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
        if (this.keys == null) throw new CipherException("Invalid public key");
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
            char base = Character.isLowerCase(result) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
            result = (char) ((result - base + shift) % Constraint.ALPHABET_SIZE + base);
        }
        return result;
    }

    private char decryptLetter(int shift, char ch) {
        char result = ch;
        if (Character.isLetter(result)) {
            char base = Character.isLowerCase(result) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
            int dec = result - base - shift;
            if (dec < 0) dec += Constraint.ALPHABET_SIZE;
            result = (char) (dec % Constraint.ALPHABET_SIZE + base);
        }
        return result;
    }
}
