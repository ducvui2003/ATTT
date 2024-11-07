package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.KeyGeneratorFactory;
import nlu.fit.leanhduc.service.cipher.symmetric.vigenere.VigenereCipher;
import nlu.fit.leanhduc.service.key.VigenereKey;
import nlu.fit.leanhduc.util.Cipher;
import nlu.fit.leanhduc.util.Language;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;
import nlu.fit.leanhduc.view.MainView;

import java.util.Objects;

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
}
