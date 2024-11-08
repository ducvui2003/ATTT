package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.ISubstitutionCipher;
import nlu.fit.leanhduc.service.KeyGeneratorFactory;
import nlu.fit.leanhduc.service.cipher.symmetric.affine.AffineCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.hill.HillCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.shift.ShiftCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.subsitution.SubstitutionCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.vigenere.VigenereCipher;
import nlu.fit.leanhduc.service.key.*;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Language;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;
import nlu.fit.leanhduc.view.MainView;

import java.util.*;

public class MainController {

    public MainController() {
        MainView view = new MainView(this);
        view.createUIComponents();
    }

    public IKeyGenerator<VigenereKey> generateKey(Language language, int length) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        VigenereCipher vigenereCipher = new VigenereCipher(alphabetUtil);
        vigenereCipher.setKeyLength(length);
        return vigenereCipher;
    }

    public IKeyGenerator<?> generateKey(Cipher cipher, Language language) {
        IKeyGenerator<?> key = Objects.requireNonNull(KeyGeneratorFactory.getKeyGenerator(cipher, language));
        return (IKeyGenerator<?>) key;
    }

    public ISubstitutionCipher<?> getSubstitutionCipher(Cipher cipher, Language language) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        return switch (cipher) {
            case Cipher.SHIFT -> new ShiftCipher(alphabetUtil);
            case Cipher.SUBSTITUTION -> new SubstitutionCipher(alphabetUtil);
            case Cipher.AFFINE -> new AffineCipher(alphabetUtil);
            case Cipher.VIGENERE -> new VigenereCipher(alphabetUtil);
            case Cipher.HILL -> new HillCipher(alphabetUtil);
            default -> throw new IllegalStateException("Unexpected value: " + cipher);
        };
    }

    public ShiftKey generateShiftCipher(int shift) {
        return new ShiftKey(shift);
    }

    public AffineKey generateAffineKey(int a, int b) {
        return new AffineKey(a, b);
    }

    public SubstitutionKey generateSubstitutionKey(List<Character> character, List<Character> mappingCharacter) {
        Map<Character, Character> key = new HashMap<>();
        for (int i = 0; i < character.size(); i++)
            key.put(character.get(i), mappingCharacter.get(i));
        return new SubstitutionKey(key);
    }

    public VigenereKey generateVigenereKey(List<Character> keys, Language language) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        List<Integer> list = new ArrayList<>();
        for (Character key : keys) {
            list.add(alphabetUtil.indexOf(key));
        }
        return new VigenereKey(list);
    }


    public HillKey generateHillKey(int[][] key) {
        return new HillKey(key);
    }
}