package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.shiftCiper.ShiftCipher;
import nlu.fit.leanhduc.service.shiftCiper.ShiftCipherEnglish;
import nlu.fit.leanhduc.service.shiftCiper.ShiftCipherVietnamese;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShiftCipherTest {
    int shift = 3;
    private final ShiftCipher shiftCipher = new ShiftCipherEnglish(shift);
    private final ShiftCipher shiftCipherVietnamese = new ShiftCipherVietnamese(shift);

    @Test
    void testVariable() {
        System.out.println(Constraint.VIET_NAME_ALPHA_BET);
        System.out.println(-3 % 2);
        System.out.println((int) 'A');
        System.out.println((int) 'Â');
    }

    @Test
    void testDivisionThrowsException() {
        String plaintext = null;
        assertThrows(CipherException.class, () -> {
            shiftCipher.encrypt(plaintext);
            shiftCipher.decrypt(plaintext);
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
        assertEquals(expected, actual);
    }

    @Test
    void testDecryptWithNegative() throws CipherException {
        String plaintext = "ABC";
        String expected = "XYZ";
        String actual = shiftCipher.decrypt(plaintext);
        assertEquals(expected, actual);
    }

    @Test
    void testEncryptWithVietnamese() throws CipherException {
        String plaintext = "Âaz";
        String expected = "Ậạà";
        String actual = shiftCipherVietnamese.encrypt(plaintext);
        assertEquals(expected, actual);
    }

    @Test
    void testDecryptWithVietnamese() throws CipherException {
        String plaintext = "Âaz";
        String expected = "Ặỵỳ";
        String actual = shiftCipherVietnamese.decrypt(plaintext);
        assertEquals(expected, actual);
    }
}
