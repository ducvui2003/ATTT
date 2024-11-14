package nlu.fit.leanhduc;


import nlu.fit.leanhduc.service.cipher.symmetric.SubstitutionCipher;
import nlu.fit.leanhduc.service.key.SubstitutionKey;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class SubstitutionCipherTest {

    SubstitutionCipher substitutionCipherVietnamese = new SubstitutionCipher(new VietnameseAlphabetUtil());
    SubstitutionCipher substitutionCipherEnglish = new SubstitutionCipher(new EnglishAlphabetUtil());
    SubstitutionKey keyEnglish = new SubstitutionKey(Map.of('a', 'b', 'c', 'd'));
    SubstitutionKey keyVietnamese = new SubstitutionKey(Map.of('â', 'b', 'đ', 'd'));

    @Test
    void testGenerateKey() {
        System.out.println(substitutionCipherVietnamese.generateKey());
        System.out.println(substitutionCipherEnglish.generateKey());
    }

    @Test
    void encryptEnglish() throws CipherException {
        String plainText = "abdz";
        substitutionCipherEnglish.loadKey(keyEnglish);
        String cipherText = substitutionCipherEnglish.encrypt(plainText);
        Assertions.assertEquals("bbdz", cipherText);
    }

    @Test
    void encryptVietnamese() throws CipherException {
        String plainText = "âđdz";
        substitutionCipherVietnamese.loadKey(keyVietnamese);
        String cipherText = substitutionCipherVietnamese.encrypt(plainText);
        Assertions.assertEquals("bddz", cipherText);
    }
}
