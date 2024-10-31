package nlu.fit.leanhduc.service.cipher.affineCipher;

import nlu.fit.leanhduc.util.alphabet.VietnameseAlphabetUtil;

public class AffineVietnameseCipher extends AffineCipher {
    public AffineVietnameseCipher() {
        super();
        this.alphabetUtil = new VietnameseAlphabetUtil();
        this.range = this.alphabetUtil.getLength();
    }
}
