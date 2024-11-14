package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.service.cipher.classic.AffineCipher;
import nlu.fit.leanhduc.service.cipher.classic.HillCipher;
import nlu.fit.leanhduc.service.cipher.classic.ShiftCipher;
import nlu.fit.leanhduc.service.cipher.classic.ClassicCipher;
import nlu.fit.leanhduc.service.cipher.classic.VigenereCipher;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Language;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;

public class KeyGeneratorFactory {
    public static ICipher<?> getKeyGenerator(Cipher cipher, Language language) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        return switch (cipher) {
            case SHIFT -> new ShiftCipher(alphabetUtil);
            case SUBSTITUTION -> new ClassicCipher(alphabetUtil);
            case AFFINE -> new AffineCipher(alphabetUtil);
            case VIGENERE -> new VigenereCipher(alphabetUtil);
            case HILL -> new HillCipher(alphabetUtil);
            default -> null;
        };
    }
}
