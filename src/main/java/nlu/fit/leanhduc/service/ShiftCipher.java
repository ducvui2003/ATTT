package nlu.fit.leanhduc.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.Language;

import java.io.File;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShiftCipher implements IAsymmetricEncrypt {
    Integer shift;
    Language language;

    public ShiftCipher(Integer shift) {
        this.shift = shift;
        this.language = Language.ENGLISH;
    }

    public ShiftCipher(Integer shift, Language language) {
        this.shift = shift;
        this.language = language;
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        if (plainText == null) throw new CipherException("Plain text is null");
        if (this.shift == null) throw new CipherException("Invalid public key");
        String result = "";
        if (this.language == Language.VIETNAMESE) {
//            for (int i = 0; i < plainText.length(); i++) {
//                char c = plainText.charAt(i);
//                if (Character.isLetter(c)) {
//                    char base = Character.isLowerCase(c) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
//                    char ce = (char) ((c - base + this.shift) % Constraint.ALPHABET_SIZE + base);
//                    result += ce;
//                } else {
//                    result += c;
//                }
//            }
        } else {
            for (int i = 0; i < plainText.length(); i++) {
                char c = plainText.charAt(i);
                if (Character.isLetter(c)) {
                    char base = Character.isLowerCase(c) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
                    char ce = (char) ((c - base + shift) % Constraint.ALPHABET_SIZE + base);
                    result += ce;
                } else {
                    result += c;
                }
            }
        }
        return result;
    }


    @Override
    public String decrypt(String encryptText) throws CipherException {
        if (encryptText == null) throw new CipherException("Plain text is null");
        if (this.shift == null) throw new CipherException("Invalid public key");
        String result = "";
        if (this.language == Language.VIETNAMESE) {
        } else {
            for (int i = 0; i < encryptText.length(); i++) {
                char c = encryptText.charAt(i);
                if (Character.isLetter(c)) {
                    char base = Character.isLowerCase(c) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
                    char ce = (char) ((c - base - this.shift) % Constraint.ALPHABET_SIZE + base);
                    result += ce;
                } else {
                    result += c;
                }
            }
        }
        return result;
    }

    @Override
    public String encrypt(File file) throws CipherException {
        return null;
    }

    @Override
    public String decrypt(File file) throws CipherException {
        return null;
    }
}
