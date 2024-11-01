package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.cipher.symmetric.cryto.AESCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.cryto.DESCipher;
import nlu.fit.leanhduc.service.cipher.symmetric.cryto.ICipherNative;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class AESTest {

    @Test
    public void testEnAndDeFile() throws Exception {
        ICipherNative aes = new AESCipher();
        SecretKey secretKey = aes.generateKey();
        aes.loadKey(secretKey);
        aes.encrypt("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\aes\\plaintext.txt", "D:\\university\\ATTT\\security-tool\\src\\test\\resources\\aes\\plaintext-encrypt.txt");
        aes.decrypt("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\aes\\plaintext-encrypt.txt", "D:\\university\\ATTT\\security-tool\\src\\test\\resources\\aes\\plaintext-decrypt.txt");
    }
}
