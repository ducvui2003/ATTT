package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.cipher.classic.HillCipher;
import nlu.fit.leanhduc.service.key.classic.HillKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HillCipherTest {
    private HillCipher englishCipher;

    private HillKeyClassic key;

    @BeforeEach
    void setup() {
        englishCipher = new HillCipher(new EnglishAlphabetUtil());
    }

    @Test
    public void testEncryptHill() throws CipherException {
        key = new HillKeyClassic(new int[][]{
                {11, 8},
                {3, 7},
        });

        englishCipher.loadKey(key);
        Assertions.assertEquals(englishCipher.encrypt("hello"), "lgyjyi=");
    }

    @Test
    public void testDecryptHill() throws CipherException {
        key = new HillKeyClassic(new int[][]{
                {11, 8},
                {3, 7},
        });

        englishCipher.loadKey(key);
        Assertions.assertEquals(englishCipher.decrypt("lgyjyi="), "hello");
    }

    @Test
    public void testLoadAndSaveFile() {
        key = new HillKeyClassic(new int[][]{
                {11, 8},
                {3, 7},
        });
        try {
            this.englishCipher.loadKey(key);
            englishCipher.saveKey("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\hill\\key.txt");
            englishCipher.loadKey("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\hill\\key.txt");
            System.out.println(this.key.name());
            System.out.println(englishCipher.getKey().name());
        } catch (CipherException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEncryptHillWithMatrix3() throws CipherException {
        key = new HillKeyClassic(new int[][]{
                {2, 14, 6},
                {1, 13, 23},
                {16, 14, 11}
        });

        englishCipher.loadKey(key);
        Assertions.assertEquals(englishCipher.encrypt("hello"), "msvkyy=");
    }

    @Test
    public void testDecryptHillWithMatrix3() throws CipherException {
        key = new HillKeyClassic(new int[][]{
                {2, 14, 6},
                {1, 13, 23},
                {16, 14, 11}
        });

        englishCipher.loadKey(key);
        Assertions.assertEquals(englishCipher.decrypt("msvkyy="), "hello");
    }
}
