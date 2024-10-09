package nlu.fit.leanhduc.service.shiftCiper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nlu.fit.leanhduc.service.IAsymmetricEncrypt;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.Constraint;
import nlu.fit.leanhduc.util.Language;
import nlu.fit.leanhduc.util.VietnameseAlphabetUtil;

import java.io.File;
import java.util.Random;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ShiftCipher implements IAsymmetricEncrypt<Integer> {
    protected Integer shift;
    protected Random rd = new Random();

    public ShiftCipher(Integer shift) {
        this.shift = shift;
    }

    @Override
    public void loadKey(Integer key) throws CipherException {
        this.shift = key;
    }

    @Override
    public String encrypt(File file) throws CipherException {
        return null;
    }

    @Override
    public String decrypt(File file) throws CipherException {
        return null;
    }
}
