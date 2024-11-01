package nlu.fit.leanhduc.service.cipher.vigenere;

import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;

public class VigenereEnglishCipher extends VigenereCipher {
    public VigenereEnglishCipher() {
        super();
        this.alphabetUtil = new EnglishAlphabetUtil();
    }
}
