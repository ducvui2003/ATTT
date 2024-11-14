/**
 * Class: DESTest
 *
 * @author ducvui2003
 * @created 12/10/24
 */
package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.cipher.symmetric.Algorithm;
import nlu.fit.leanhduc.service.cipher.symmetric.ITextCipherNative;
import nlu.fit.leanhduc.service.cipher.symmetric.SymmetricCipherNative;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class DESTest {
    @Test
    public void TestDes() throws Exception {
        ITextCipherNative des = new SymmetricCipherNative(new Algorithm("DES", null, null, 56, 0));
        SecretKey secretKey = des.generateKey();
        des.loadKey(secretKey);
        des.encrypt("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\des\\des-plaintext.txt", "D:\\university\\ATTT\\security-tool\\src\\test\\resources\\des\\dec-plaintext-encrypt.txt");
        des.decrypt("D:\\university\\ATTT\\security-tool\\src\\test\\resources\\des\\dec-plaintext-encrypt.txt", "D:\\university\\ATTT\\security-tool\\src\\test\\resources\\des\\dec-plaintext-decrypt.txt");
    }


}
