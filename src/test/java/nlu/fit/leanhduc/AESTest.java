package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.service.cipher.Algorithm;
import nlu.fit.leanhduc.service.cipher.SymmetricCipherNative;
import nlu.fit.leanhduc.service.key.KeySymmetric;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class AESTest {

    @Test
    public void testEnAndDeFile() throws Exception {
        ICipher<KeySymmetric> aes = new SymmetricCipherNative(new Algorithm("AES", Mode.CBC.getName(), Padding.PKCS5Padding.getName(), Size.Size_128.getByteFormat(), Size.Size_128.getByteFormat()));
        KeySymmetric secretKey = aes.generateKey();
        aes.loadKey(secretKey);
        aes.encrypt("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\aes\\plaintext.txt", "D:\\university\\ATTT\\security-tool\\src\\test\\resources\\aes\\plaintext-encrypt.txt");
        aes.decrypt("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\aes\\plaintext-encrypt.txt", "D:\\university\\ATTT\\security-tool\\src\\test\\resources\\aes\\plaintext-decrypt.txt");
    }
}
