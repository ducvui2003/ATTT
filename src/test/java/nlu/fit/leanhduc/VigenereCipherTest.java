/**
 * Class: VigenereCipher
 *
 * @author ducvui2003
 * @created 9/10/24
 */
package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.cipher.vigenereCipher.VigenereCipher;
import nlu.fit.leanhduc.service.cipher.vigenereCipher.VigenereEnglishCipher;
import nlu.fit.leanhduc.service.cipher.vigenereCipher.VigenereVietnameseCipher;
import nlu.fit.leanhduc.util.CipherException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class VigenereCipherTest {
    private VigenereCipher englishCipher;
    private VigenereCipher vietnameseCipher;

    private List<Integer> keys;

    @BeforeEach
    void setup() {
        englishCipher = new VigenereEnglishCipher();
        vietnameseCipher = new VigenereVietnameseCipher();
        keys = List.of(1, 5, 10, 2);
    }

    @Test
    void testEncryptEnglish() throws CipherException {
        String plainText = "Hello";
        englishCipher.loadKey(keys);
        String encryptText = englishCipher.encrypt(plainText);
        Assertions.assertEquals("Ijvnp", encryptText);
    }

    @Test
    void testDecryptEnglish() throws CipherException {
        String encryptText = "Ijvnp";
        englishCipher.loadKey(keys);
        String plainText = englishCipher.decrypt(encryptText);
        Assertions.assertEquals("Hello", plainText);
    }

    @Test
    void testEncryptVietnamese() throws CipherException {
        String plainText = "Đức";
        vietnameseCipher.loadKey(keys);
        String encryptText = vietnameseCipher.encrypt(plainText);
        Assertions.assertEquals("Evề", encryptText);
    }

    @Test
    void testDecryptVietnamese() throws CipherException {
        String encryptText = "Evề";
        vietnameseCipher.loadKey(keys);
        String plainText = vietnameseCipher.decrypt(encryptText);
        Assertions.assertEquals("Đức", plainText);
    }
}
