package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

import lombok.Data;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;

import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

@Data
public class Algorithm {
    String cipher;
    String mode;
    String padding;
    int keySize;
    int ivSize;

    public Algorithm(String cipher, String mode, String padding, int keySize, int ivSize) {
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
        StringBuilder result = new StringBuilder();
        if (this.cipher != null) {
            result.append(this.cipher);
        }
        if (this.mode != null) {
            result.append("/").append(this.mode);
        }
        if (this.padding != null) {
            result.append("/").append(this.padding);
        }

        return result.toString();
    }
}
