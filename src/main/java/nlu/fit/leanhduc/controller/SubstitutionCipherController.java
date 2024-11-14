package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.service.ITextSubstitutionCipher;
import nlu.fit.leanhduc.service.cipher.classic.AffineCipher;
import nlu.fit.leanhduc.service.cipher.classic.HillCipher;
import nlu.fit.leanhduc.service.cipher.classic.ShiftCipher;
import nlu.fit.leanhduc.service.cipher.classic.ClassicCipher;
import nlu.fit.leanhduc.service.cipher.classic.VigenereCipher;
import nlu.fit.leanhduc.service.key.*;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Language;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String encrypt(String plainText, IKeyDisplay key, Cipher cipher, Language language) throws Exception {
        ICipher textEncrypt = null;
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
                textEncrypt = new ClassicCipher(alphabetUtil);
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
        ICipher textEncrypt = null;
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
                textEncrypt = new ClassicCipher(alphabetUtil);
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


    public SubstitutionKey generateSubstitutionKey(List<Character> character, List<Character> mappingCharacter) {
        Map<Character, Character> key = new HashMap<>();
        for (int i = 0; i < character.size(); i++)
            key.put(character.get(i), mappingCharacter.get(i));
        return new SubstitutionKey(key);
    }

    public ViginereKey generateVigenereKey(String keys, Language language) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        List<Integer> list = new ArrayList<>();
        for (Character key : keys.toCharArray()) {
            list.add(alphabetUtil.indexOf(key));
        }
        return new ViginereKey(list);
    }


    public ShiftKey generateShiftCipher(int shift) {
        return new ShiftKey(shift);
    }

    public AffineKey generateAffineKey(int a, int b) {
        return new AffineKey(a, b);
    }

    public HillKey generateHillKey(String[][] matrix) {
        int[][] key = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                key[i][j] = Integer.parseInt(matrix[i][j]);
        return new HillKey(key);
    }

    public <T> T loadKey(String src, Cipher cipher) throws IOException {
        switch (cipher) {
            case SHIFT:
                return (T) new ShiftKey(0);
            case SUBSTITUTION:
                return (T) new SubstitutionKey(new HashMap<>());
            case AFFINE:
                return (T) new AffineKey(0, 0);
            case VIGENERE:
                return (T) new ViginereKey(new ArrayList<>());
            case HILL:
                return (T) new HillKey(new int[0][0]);
        }
        return null;
    }

}