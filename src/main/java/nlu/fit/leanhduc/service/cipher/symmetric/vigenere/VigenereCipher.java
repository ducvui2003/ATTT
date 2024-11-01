package nlu.fit.leanhduc.service.cipher.symmetric.vigenere;

import lombok.Getter;
import lombok.Setter;
import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.ITextEncrypt;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public abstract class VigenereCipher implements ITextEncrypt, IKeyGenerator<List<Integer>> {
    protected List<Integer> keys;
    protected int keyLength;
    protected Random rd = new Random();
    protected AlphabetUtil alphabetUtil;

    @Override
    public void loadKey(List<Integer> key) throws CipherException {
        this.keys = key;
        this.keyLength = key.size();
    }

    @Override
    public List<Integer> generateKey() {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < keyLength; i++)
            results.add(rd.nextInt(alphabetUtil.getLength()) * alphabetUtil.getLength());
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
            boolean isLower = Character.isLowerCase(ch);
            int index = alphabetUtil.indexOf(Character.toLowerCase(ch));
            int indexOfEncrypt = index + shift;
            result = alphabetUtil.getChar(indexOfEncrypt);
            if (!isLower) result = Character.toUpperCase(result);
        }
        return result;
    }

    private char decryptLetter(int shift, char ch) {
        char result = ch;
        if (Character.isLetter(result)) {
            boolean isLower = Character.isLowerCase(ch);
            int index = alphabetUtil.indexOf(Character.toLowerCase(result));
            int indexOfDecrypt;
            if (index - shift < 0)
                indexOfDecrypt = (index - shift + alphabetUtil.getLength()) % alphabetUtil.getLength();
            else
                indexOfDecrypt = (index - shift) % alphabetUtil.getLength();
            result = alphabetUtil.getChar(indexOfDecrypt);
            if (!isLower) result = Character.toUpperCase(result);
        }
        return result;
    }
}
