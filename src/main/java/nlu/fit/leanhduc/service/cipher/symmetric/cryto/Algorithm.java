package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

import lombok.Data;
import nlu.fit.leanhduc.util.Cipher;

import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

@Data
public class Algorithm {
    Cipher cipher;
    String mode;
    String padding;
    int keySize;
    int ivSize;

    public Algorithm(Cipher cipher, String mode, String padding, int keySize) {
        this.cipher = cipher;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
    }

    public Algorithm(Cipher cipher, String mode, String padding, int keySize, int ivSize) {
        this.cipher = cipher;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
        this.ivSize = ivSize;
    }

    IvParameterSpec getIvParameterSpec() {
        byte[] ivBytes = new byte[this.ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        return new IvParameterSpec(ivBytes);
    }

    @Override
    public String toString() {
        return cipher.name() + "/" + mode + "/" + padding;
    }
}
