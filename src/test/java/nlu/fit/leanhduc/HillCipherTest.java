package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.cipher.hill.HillCipherEnglish;
import nlu.fit.leanhduc.service.key.HillKey;
import nlu.fit.leanhduc.util.CipherException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HillCipherTest {
    private HillCipherEnglish englishCipher;

    private HillKey key;

    @BeforeEach
    void setup() {
        englishCipher = new HillCipherEnglish();
    }

    @Test
    public void testEncryptHill() throws CipherException {
        key = new HillKey(new int[][]{
                {11, 8},
                {3, 7},
        });

        englishCipher.loadKey(key);
        Assertions.assertEquals(englishCipher.encrypt("DHNONGLA"), "CVDUFQRK");
    }

    @Test
    public void testDecryptHill() throws CipherException {
        key = new HillKey(new int[][]{
                {11, 8},
                {3, 7},
        });

        englishCipher.loadKey(key);
        Assertions.assertEquals(englishCipher.decrypt("CVDUFQRK"), "DHNONGLA");
    }


}
