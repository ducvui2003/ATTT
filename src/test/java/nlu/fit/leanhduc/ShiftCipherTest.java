package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.IAsymmetricEncrypt;
import nlu.fit.leanhduc.service.ShiftCipher;
import nlu.fit.leanhduc.util.CipherException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShiftCipherTest {
    private final IAsymmetricEncrypt<Integer> shiftCipher = new ShiftCipher();

    @Test
    void testDivisionThrowsException() {
        String plaintext = "";
        int key = 3;
        assertThrows(CipherException.class, () -> {
            shiftCipher.encrypt(plaintext, key);
        });
    }

    @Test
    void testEncrypt() throws CipherException {
        String plaintext = "HELLO";
        int key = 3;
        String expected = "KHOOR";
        String actual = shiftCipher.encrypt(plaintext, key);
        assertEquals(expected, actual);
    }

    @Test
    void testEncryptWithSpecialChar() throws CipherException {
        String plaintext = "HEL*LO";
        int key = 3;
        String expected = "KHO*OR";
        String actual = shiftCipher.encrypt(plaintext, key);
        assertEquals(expected, actual);
    }

    @Test
    void testDecrypt() throws CipherException {
        String plaintext = "KHOOR";
        int key = 3;
        String expected = "HELLO";
        String actual = shiftCipher.decrypt(plaintext, key);
        assertEquals(expected, actual);
    }

    @Test
    void testDecryptWithSpecialChar() throws CipherException {
        String plaintext = "KHO*O_R";
        int key = 3;
        String expected = "HEL*L_O";
        String actual = shiftCipher.decrypt(plaintext, key);
        assertEquals(expected, actual);
    }
}
