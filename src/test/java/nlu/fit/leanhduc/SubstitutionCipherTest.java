package nlu.fit.leanhduc;


import nlu.fit.leanhduc.service.IAsymmetricEncrypt;
import nlu.fit.leanhduc.service.subsitutionCiper.SubstitutionEnglishCipher;
import nlu.fit.leanhduc.service.subsitutionCiper.SubstitutionVietnameseCipher;
import nlu.fit.leanhduc.util.CipherException;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class SubstitutionCipherTest {

    IAsymmetricEncrypt<Map<Character, Character>> substitutionCipherVietnamese = new SubstitutionVietnameseCipher();
    IAsymmetricEncrypt<Map<Character, Character>> substitutionCipherEnglish = new SubstitutionEnglishCipher();

    @Test
    public void testGenerateKey() {
        System.out.println(substitutionCipherVietnamese.generateKey());
        System.out.println(substitutionCipherEnglish.generateKey());
    }
}
