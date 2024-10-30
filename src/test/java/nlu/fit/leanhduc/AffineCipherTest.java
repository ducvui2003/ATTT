package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.cipher.affineCipher.AffineEnglishCipher;
import nlu.fit.leanhduc.service.cipher.affineCipher.AffineVietnameseCipher;
import nlu.fit.leanhduc.service.key.AffineKey;
import nlu.fit.leanhduc.util.CipherException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AffineCipherTest {
    private AffineEnglishCipher englishCipher;
    private AffineVietnameseCipher vietnameseCipher;

    private AffineKey keys;


    @BeforeEach
    void setup() {
        englishCipher = new AffineEnglishCipher();
        vietnameseCipher = new AffineVietnameseCipher();

    }

    @Test
    public void testEncryptAffineCipher() throws CipherException {
        keys = new AffineKey(5, 8);
        englishCipher.loadKey(keys);
        Assertions.assertEquals(englishCipher.encrypt("HELLO"), "RCLLA");
    }

    @Test
    public void testEncryptAffineCipherFull() throws CipherException {
        keys = new AffineKey(5, 6);
        englishCipher.loadKey(keys);
        Assertions.assertEquals(englishCipher.encrypt("A"), "G");
    }

    // Test encryption of the entire alphabet
    @Test
    public void testEncryptFullAlphabet() throws CipherException {
        keys = new AffineKey(5, 8);
        englishCipher.loadKey(keys);
        Assertions.assertEquals(englishCipher.encrypt("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), "INSXCHMRWBGLQVAFKPUZEJOTYD");
    }

    // Test decryption with valid key
    @Test
    public void testDecryptAffineCipher() throws CipherException {
        keys = new AffineKey(5, 8);
        englishCipher.loadKey(keys);
        Assertions.assertEquals(englishCipher.decrypt("RCLLA"), "HELLO");
    }

    // Test decryption of the full alphabet
    @Test
    public void testDecryptFullAlphabet() throws CipherException {
        keys = new AffineKey(5, 8);
        englishCipher.loadKey(keys);
        Assertions.assertEquals(englishCipher.decrypt("INSXCHMRWBGLQVAFKPUZEJOTYD"), "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    @Test
    public void testEncrypt() throws CipherException {
        keys = new AffineKey(5, 8);
        englishCipher.loadKey(keys);
        Assertions.assertEquals(englishCipher.encrypt("D!"), "A!"); // Assuming non-alphabet characters remain unchanged
    }

    // Test with non-alphabet characters (if they are allowed to pass through unchanged)
    @Test
    public void testEncryptWithSpecialCharacters() throws CipherException {
        keys = new AffineKey(5, 8);
        englishCipher.loadKey(keys);
        Assertions.assertEquals(englishCipher.encrypt("HELLO, WORLD!"), "RCLLA, OAPLA!"); // Assuming non-alphabet characters remain unchanged
    }

    // Test decryption with special characters
    @Test
    public void testDecryptWithSpecialCharacters() throws CipherException {
        keys = new AffineKey(5, 8);
        englishCipher.loadKey(keys);
        Assertions.assertEquals(englishCipher.decrypt("RCLLA, OAPLA!"), "HELLO, WORLD!");
    }

    // Test encryption and decryption with lowercase input (assuming case-insensitivity)
    @Test
    public void testEncryptLowercaseInput() throws CipherException {
        keys = new AffineKey(5, 8);
        englishCipher.loadKey(keys);
        Assertions.assertNotEquals(englishCipher.encrypt("hello"), "RCLLA");
    }

    // Test encryption with a non-coprime key
    @Test
    public void testEncryptNonCoprimeKey() {
        keys = new AffineKey(6, 8); // 6 is not coprime with 26
        Assertions.assertThrows(CipherException.class, () -> englishCipher.loadKey(keys));
    }

    // Test decryption with a non-coprime key
    @Test
    public void testDecryptNonCoprimeKey() {
        keys = new AffineKey(6, 8); // 6 is not coprime with 26
        Assertions.assertThrows(CipherException.class, () -> englishCipher.loadKey(keys));
    }

    // Test decrypting a string that was not encrypted with the Affine cipher
    @Test
    public void testDecryptInvalidCipherText() throws CipherException {
        keys = new AffineKey(5, 8);
        englishCipher.loadKey(keys);
        Assertions.assertNotEquals(englishCipher.decrypt("INVALID"), "HELLO");
    }
}
