package nlu.fit.leanhduc.service.cipher.vigenere;

import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;

public class VigenereVietnameseCipher extends VigenereCipher {
    public VigenereVietnameseCipher() {
        super();
        this.alphabetUtil = new VietnameseAlphabetUtil();
    }
}
