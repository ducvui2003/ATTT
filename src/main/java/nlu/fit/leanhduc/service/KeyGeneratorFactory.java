package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.service.cipher.symmetric.shift.ShiftCipher;
import nlu.fit.leanhduc.util.Cipher;
import nlu.fit.leanhduc.util.Language;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;

public class KeyGeneratorFactory {
    public static IKeyGenerator getKeyGenerator(Cipher cipher, Language language) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        return switch (cipher) {
            case SHIFT -> new ShiftCipher(alphabetUtil);
            default -> null;
        };
    }
}
