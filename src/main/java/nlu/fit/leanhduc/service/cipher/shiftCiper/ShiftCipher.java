package nlu.fit.leanhduc.service.cipher.shiftCiper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.ITextEncrypt;
import nlu.fit.leanhduc.util.CipherException;

import java.io.File;
import java.util.Random;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ShiftCipher implements ITextEncrypt, IKeyGenerator<Integer> {
    protected Integer shift;
    protected Random rd = new Random();

    public ShiftCipher(Integer shift) {
        this.shift = shift;
    }

    @Override
    public void loadKey(Integer key) throws CipherException {
        this.shift = key;
    }
}
