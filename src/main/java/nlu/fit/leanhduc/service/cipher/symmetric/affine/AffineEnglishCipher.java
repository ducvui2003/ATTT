package nlu.fit.leanhduc.service.cipher.symmetric.affine;

import nlu.fit.leanhduc.util.alphabet.EnglishAlphabetUtil;

public class AffineEnglishCipher extends AffineCipher {

    public AffineEnglishCipher() {
        super();
        this.alphabetUtil = new EnglishAlphabetUtil();
        this.range = this.alphabetUtil.getLength();
    }
}
