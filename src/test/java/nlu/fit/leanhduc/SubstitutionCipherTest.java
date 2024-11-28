package nlu.fit.leanhduc;


import nlu.fit.leanhduc.service.cipher.classic.ClassicCipher;
import nlu.fit.leanhduc.service.key.classic.SubstitutionKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

public class SubstitutionCipherTest {

    ClassicCipher substitutionCipherVietnamese = new ClassicCipher(new VietnameseAlphabetUtil());
    ClassicCipher substitutionCipherEnglish = new ClassicCipher(new EnglishAlphabetUtil());
    SubstitutionKeyClassic keyEnglish = new SubstitutionKeyClassic(Map.of('a', 'b', 'c', 'd'));
    SubstitutionKeyClassic keyVietnamese = new SubstitutionKeyClassic(Map.of('â', 'b', 'đ', 'd'));

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

    @Test
    public void testLoadAndSaveFile() {
        try {
            this.substitutionCipherEnglish.loadKey(keyEnglish);
            substitutionCipherEnglish.saveKey("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\substitution\\key.txt");
            substitutionCipherEnglish.loadKey("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\substitution\\key.txt");
            System.out.println(this.keyEnglish.name());
            System.out.println(substitutionCipherEnglish.getKey().name());
        } catch (CipherException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
