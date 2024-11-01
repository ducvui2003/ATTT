package nlu.fit.leanhduc.service.cipher.shift;

import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;

public class ShiftCipherEnglish extends ShiftCipher {
    public ShiftCipherEnglish(Integer shift) {
        super(shift);
        this.alphabet = new EnglishAlphabetUtil();
    }
}
