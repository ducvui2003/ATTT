package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.service.ITextKey;
import nlu.fit.leanhduc.service.KeyGeneratorFactory;
import nlu.fit.leanhduc.service.cipher.classic.*;
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

    public ICipher<ViginereKey> generateKey(Language language, int length) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        VigenereCipher vigenereCipher = new VigenereCipher(alphabetUtil);
        vigenereCipher.setKeyLength(length);
        return vigenereCipher;
    }

    public ICipher<?> generateKey(Cipher cipher, Language language) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        ICipher<?> key = switch (cipher) {
            case SHIFT -> new ShiftCipher(alphabetUtil);
            case SUBSTITUTION -> new ClassicCipher(alphabetUtil);
            case AFFINE -> new AffineCipher(alphabetUtil);
            case VIGENERE -> new VigenereCipher(alphabetUtil);
            case HILL -> new HillCipher(alphabetUtil);
            default -> null;
        };
        return key;
    }
}