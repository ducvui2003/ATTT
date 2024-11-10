package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.service.cipher.symmetric.affine.AffineCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.hill.HillCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.shift.ShiftCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.subsitution.SubstitutionCipher;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Language;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;

public class KeyGeneratorFactory {
    public static IKeyGenerator<?> getKeyGenerator(Cipher cipher, Language language) {
        AlphabetUtil alphabetUtil = language == Language.ENGLISH ? new EnglishAlphabetUtil() : new VietnameseAlphabetUtil();
        return switch (cipher) {
            case SHIFT -> new ShiftCipher(alphabetUtil);
            case SUBSTITUTION -> new SubstitutionCipher(alphabetUtil);
            case AFFINE -> new AffineCipher(alphabetUtil);
            case HILL -> new HillCipher(alphabetUtil);
            default -> null;
        };
    }
}
