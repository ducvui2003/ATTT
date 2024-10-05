package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.IAsymmetricEncrypt;
import nlu.fit.leanhduc.service.ShiftCipher;
import nlu.fit.leanhduc.util.CipherException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShiftCipherTest {
    int shift = 3;
    private final IAsymmetricEncrypt shiftCipher = new ShiftCipher(shift);

    @Test
    void testDivisionThrowsException() {
        String plaintext = null;
        assertThrows(CipherException.class, () -> {
            shiftCipher.encrypt(plaintext);
        });
    }

    @Test
    void testEncrypt() throws CipherException {
        String plaintext = "HELLO";
        String expected = "KHOOR";
        String actual = shiftCipher.encrypt(plaintext);
        assertEquals(expected, actual);
    }

    @Test
    void testEncryptWithSpecialChar() throws CipherException {
        String plaintext = "HEL*LO";
        String expected = "KHO*OR";
        String actual = shiftCipher.encrypt(plaintext);
        assertEquals(expected, actual);
    }

    @Test
    void testDecrypt() throws CipherException {
        String plaintext = "KHOOR";
        String expected = "HELLO";
        String actual = shiftCipher.decrypt(plaintext);
        assertEquals(expected, actual);
    }

    @Test
    void testDecryptWithSpecialChar() throws CipherException {
        String plaintext = "KHO*O_R";
        String expected = "HEL*L_O";
        String actual = shiftCipher.decrypt(plaintext);

        System.out.println((int)'A');
        System.out.println((int)'Â');
        assertEquals(expected, actual);
    }
}
