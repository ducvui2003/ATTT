package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.service.cipher.classic.AffineCipher;
import nlu.fit.leanhduc.service.cipher.classic.HillCipher;
import nlu.fit.leanhduc.service.cipher.classic.ShiftCipher;
import nlu.fit.leanhduc.service.cipher.classic.ClassicCipher;
import nlu.fit.leanhduc.service.cipher.classic.VigenereCipher;
import nlu.fit.leanhduc.service.key.classic.*;
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

    public String encrypt(String plainText, IKeyClassic key, Cipher cipher, Language language) throws CipherException {
        ICipher textEncrypt = null;
        AlphabetUtil alphabetUtil;
        if (language == Language.ENGLISH)
            alphabetUtil = new EnglishAlphabetUtil();
        else
            alphabetUtil = new VietnameseAlphabetUtil();

        textEncrypt = switch (cipher) {
            case SHIFT -> new ShiftCipher(alphabetUtil);
            case SUBSTITUTION -> new ClassicCipher(alphabetUtil);
            case AFFINE -> new AffineCipher(alphabetUtil);
            case VIGINERE -> new VigenereCipher(alphabetUtil);
            case HILL -> new HillCipher(alphabetUtil);
            default -> throw new IllegalStateException("Unexpected value: " + cipher);
        };
        textEncrypt.loadKey(key);
        return textEncrypt.encrypt(plainText);
    }

    public String decrypt(String encrypt, IKeyClassic key, Cipher cipher, Language language) throws CipherException {
        ICipher textEncrypt = null;
        AlphabetUtil alphabetUtil;
        if (language == Language.ENGLISH)
            alphabetUtil = new EnglishAlphabetUtil();
        else
            alphabetUtil = new VietnameseAlphabetUtil();

        textEncrypt = switch (cipher) {
            case SHIFT -> new ShiftCipher(alphabetUtil);
            case SUBSTITUTION -> new ClassicCipher(alphabetUtil);
            case AFFINE -> new AffineCipher(alphabetUtil);
            case VIGINERE -> new VigenereCipher(alphabetUtil);
            case HILL -> new HillCipher(alphabetUtil);
            default -> throw new IllegalStateException("Unexpected value: " + cipher);
        };
        textEncrypt.loadKey(key);
        return textEncrypt.decrypt(encrypt);
    }


    public SubstitutionKeyClassic generateSubstitutionKey(List<Character> character, List<Character> mappingCharacter) {
        Map<Character, Character> key = new HashMap<>();
        for (int i = 0; i < character.size(); i++)
            key.put(character.get(i), mappingCharacter.get(i));
        return new SubstitutionKeyClassic(key);
    }

    public ViginereKeyClassic generateVigenereKey(String keys, Language language) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        List<Integer> list = new ArrayList<>();
        for (Character key : keys.toCharArray()) {
            list.add(alphabetUtil.indexOf(key));
        }
        return new ViginereKeyClassic(list);
    }


    public ShiftKeyClassic generateShiftCipher(int shift) {
        return new ShiftKeyClassic(shift);
    }

    public AffineKeyClassic generateAffineKey(int a, int b) {
        return new AffineKeyClassic(a, b);
    }

    public HillKeyClassic generateHillKey(String[][] matrix) {
        int[][] key = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                key[i][j] = Integer.parseInt(matrix[i][j]);
        return new HillKeyClassic(key);
    }

    public <T> T loadKey(String src, Cipher cipher) throws IOException {
        switch (cipher) {
            case SHIFT:
                return (T) new ShiftKeyClassic(0);
            case SUBSTITUTION:
                return (T) new SubstitutionKeyClassic(new HashMap<>());
            case AFFINE:
                return (T) new AffineKeyClassic(0, 0);
            case VIGINERE:
                return (T) new ViginereKeyClassic(new ArrayList<>());
            case HILL:
                return (T) new HillKeyClassic(new int[0][0]);
        }
        return null;
    }

}