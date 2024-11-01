package nlu.fit.leanhduc.service.cipher.hill;

import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;

public class HillCipherEnglish extends HillCipher {
    public HillCipherEnglish() {
        super();
        this.alphabetUtil = new EnglishAlphabetUtil();
    }
}
