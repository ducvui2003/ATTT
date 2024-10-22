package nlu.fit.leanhduc.service.cipher.affineCipher;

import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;

public class AffineVietnameseCipher extends AffineCipher {
    public AffineVietnameseCipher() {
        super();
        this.range = Constraint.VIET_NAME_ALPHA_BET_SIZE;
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        return "";
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        return "";
    }
}
