package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.ISubstitutionCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.affine.AffineCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.hill.HillCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.shift.ShiftCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.subsitution.SubstitutionCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.vigenere.VigenereCipher;
import nlu.fit.leanhduc.service.key.IKeyDisplay;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Language;

public class SubstitutionCipherController {
    public static SubstitutionCipherController INSTANCE;

    private SubstitutionCipherController() {
    }

    public static SubstitutionCipherController getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new SubstitutionCipherController();
        }
        return INSTANCE;
    }

    public String encrypt(String plainText, IKeyDisplay key, Cipher cipher, Language language) throws CipherException {
        ISubstitutionCipher textEncrypt = null;
        AlphabetUtil alphabetUtil;
        if (language == Language.ENGLISH)
            alphabetUtil = new EnglishAlphabetUtil();
        else
            alphabetUtil = new VietnameseAlphabetUtil();

        switch (cipher) {
            case SHIFT:
                textEncrypt = new ShiftCipher(alphabetUtil);
                break;
            case SUBSTITUTION:
                textEncrypt = new SubstitutionCipher(alphabetUtil);
                break;
            case AFFINE:
                textEncrypt = new AffineCipher(alphabetUtil);
                break;
            case VIGENERE:
                textEncrypt = new VigenereCipher(alphabetUtil);
                break;
            case HILL:
                textEncrypt = new HillCipher(alphabetUtil);
                break;
        }
        textEncrypt.loadKey(key);
        return textEncrypt.encrypt(plainText);
    }

    public String decrypt(String encrypt, IKeyDisplay key, Cipher cipher, Language language) throws CipherException {
        ISubstitutionCipher textEncrypt = null;
        AlphabetUtil alphabetUtil;
        if (language == Language.ENGLISH)
            alphabetUtil = new EnglishAlphabetUtil();
        else
            alphabetUtil = new VietnameseAlphabetUtil();

        switch (cipher) {
            case SHIFT:
                textEncrypt = new ShiftCipher(alphabetUtil);
                break;
            case SUBSTITUTION:
                textEncrypt = new SubstitutionCipher(alphabetUtil);
                break;
            case AFFINE:
                textEncrypt = new AffineCipher(alphabetUtil);
                break;
            case VIGENERE:
                textEncrypt = new VigenereCipher(alphabetUtil);
                break;
            case HILL:
                textEncrypt = new HillCipher(alphabetUtil);
                break;
        }
        textEncrypt.loadKey(key);
        return textEncrypt.decrypt(encrypt);
    }
}
