package nlu.fit.leanhduc.service.cipher.symmetric.shift;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.ITextEncrypt;
import nlu.fit.leanhduc.service.key.ShiftKey;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.util.Random;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShiftCipher implements ITextEncrypt, IKeyGenerator<ShiftKey> {
    protected Integer shift;
    protected Random rd = new Random();
    protected AlphabetUtil alphabet;

    public ShiftCipher(AlphabetUtil alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public ShiftKey generateKey() {
        return new ShiftKey(rd.nextInt(alphabet.getLength()) + 1);
    }

    @Override
    public void loadKey(ShiftKey key) throws CipherException {
        this.shift = key.getKey();
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
                boolean isLower = Character.isLowerCase(c);
                int index = alphabet.indexOf(Character.toLowerCase(c));
                int indexOfEncrypt = index + this.shift;
                ce = alphabet.getChar(indexOfEncrypt);
                if (!isLower) ce = Character.toUpperCase(ce);
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
            char ce = c;
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                int index = alphabet.indexOf(Character.toLowerCase(c));
                int indexOfDecrypt;
                if (index - this.shift < 0)
                    indexOfDecrypt = (index - this.shift + alphabet.getLength()) % alphabet.getLength();
                else
                    indexOfDecrypt = (index - this.shift) % alphabet.getLength();
                ce = alphabet.getChar(indexOfDecrypt);
                if (!isLower) ce = Character.toUpperCase(ce);
            }
            result += ce;
        }
        return result;
    }
}
