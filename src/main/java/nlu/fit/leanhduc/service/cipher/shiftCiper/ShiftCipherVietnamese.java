package nlu.fit.leanhduc.service.cipher.shiftCiper;

import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.VietnameseAlphabetUtil;

public class ShiftCipherVietnamese extends ShiftCipher {
    public ShiftCipherVietnamese(Integer shift) {
        super(shift);
    }

    @Override
    public Integer generateKey() {
        return rd.nextInt(Constraint.VIET_NAME_ALPHA_BET_SIZE) + 1;
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        if (plainText == null) throw new CipherException("Plain text is null");
        if (super.shift == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            char ce = c;
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                int index = VietnameseAlphabetUtil.indexOf(Character.toLowerCase(c));
                int indexOfEncrypt = (index + this.shift) % Constraint.VIET_NAME_ALPHA_BET_SIZE;
                ce = VietnameseAlphabetUtil.getChar(indexOfEncrypt);
                if (!isLower) ce = Character.toUpperCase(ce);
            }
            result += ce;
        }
        return result;
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        if (encryptText == null) throw new CipherException("Plain text is null");
        if (this.shift == null) throw new CipherException("Invalid public key");
        String result = "";
        for (int i = 0; i < encryptText.length(); i++) {
            char c = encryptText.charAt(i);
            char ce = c;
            if (Character.isLetter(c)) {
                boolean isLower = Character.isLowerCase(c);
                int index = VietnameseAlphabetUtil.indexOf(Character.toLowerCase(c));
                int indexOfDecrypt;
                if (index - this.shift < 0)
                    indexOfDecrypt = (index - this.shift + Constraint.VIET_NAME_ALPHA_BET_SIZE) % Constraint.VIET_NAME_ALPHA_BET_SIZE;
                else
                    indexOfDecrypt = (index - this.shift) % Constraint.VIET_NAME_ALPHA_BET_SIZE;
                ce = VietnameseAlphabetUtil.getChar(indexOfDecrypt);
                if (!isLower) ce = Character.toUpperCase(ce);
            }
            result += ce;
        }
        return result;
    }
}