package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.cipher.classic.ShiftCipher;
import nlu.fit.leanhduc.service.key.classic.ShiftKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;
import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShiftCipherTest {
    int shift = 3;
    private final ShiftKeyClassic key = new ShiftKeyClassic(shift);
    private final ShiftCipher shiftCipher = new ShiftCipher(new EnglishAlphabetUtil());
    private final ShiftCipher shiftCipherVietnamese = new ShiftCipher(new VietnameseAlphabetUtil());

    @BeforeEach
    void setup() throws CipherException {
        shiftCipher.loadKey(key);
        shiftCipherVietnamese.loadKey(key);
    }

    @Test
    void testVariable() {
        System.out.println(Constraint.VIET_NAME_ALPHA_ARRAY);
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

    @Test
    public void testLoadAndSaveFile() {
        try {
            this.shiftCipher.loadKey(key);
            shiftCipher.saveKey("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\shift\\key.txt");
            shiftCipher.loadKey("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\shift\\key.txt");
            System.out.println(this.key.name());
            System.out.println(shiftCipher.getKey().name());
        } catch (CipherException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
