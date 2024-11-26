package nlu.fit.leanhduc.service.cipher;

import lombok.Data;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Mode;
import nlu.fit.leanhduc.util.constraint.Padding;
import nlu.fit.leanhduc.util.constraint.Size;

@Data
public class Algorithm {
    String cipher;
    int blockSize;
    String mode;
    String padding;
    //  đơn vị: bit
    int keySize;
    //  đơn vị: bit
    int ivSize;
    String hashFunction;
    String provider;


    public Algorithm(String cipher, int keySize, String hashFunction) {
        this.cipher = cipher;
        this.keySize = keySize;
        this.hashFunction = hashFunction;
    }

    public Algorithm(String cipher, String mode, String padding, int keySize, int ivSize) {
        this.cipher = cipher;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
        this.ivSize = ivSize;
    }


    private Algorithm(String cipher, String mode, String padding, int keySize) {
        this.cipher = cipher;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
    }

    private Algorithm(String cipher, int blockSize, String mode, String padding, int keySize, int ivSize, String provider) {
        this.cipher = cipher;
        this.blockSize = blockSize;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
        this.ivSize = ivSize;
        this.provider = provider;
    }

    public static Algorithm of(Cipher cipher, Mode mode, Padding padding, Size keySize, Size ivSize) {
        return new Algorithm(cipher.getName(), cipher.getBlockSize(), mode.getName(), padding.getName(), keySize.getBit(), ivSize.getBit(), cipher.getProvider());
    }

    public static Algorithm of(Cipher cipher, Mode mode, Padding padding, Size keySize) {
        return new Algorithm(cipher.getName(), mode.getName(), padding.getName(), keySize.getBit());
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

    public String toSignature() {
        return this.hashFunction + "with" + this.cipher;
    }
}
