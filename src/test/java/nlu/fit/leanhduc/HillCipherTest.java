package nlu.fit.leanhduc;

import nlu.fit.leanhduc.service.cipher.affineCipher.AffineEnglishCipher;
import nlu.fit.leanhduc.service.cipher.affineCipher.AffineVietnameseCipher;
import nlu.fit.leanhduc.service.cipher.hillCipher.HillCipherEnglish;
import nlu.fit.leanhduc.service.key.AffineKey;
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
    public void testHill() throws CipherException {
        key = new HillKey(new int[][]{
                {11, 8},
                {3, 7},
        });

        englishCipher.loadKey(key);
        Assertions.assertEquals(englishCipher.encrypt("DHNONGLA"), "CVDUFQRK");
    }

}
