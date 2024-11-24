package nlu.fit.leanhduc.service.cipher;

import lombok.Data;

@Data
public class Algorithm {
    String cipher;
    String mode;
    String padding;
    //  đơn vị: bit
    int keySize;
    //  đơn vị: bit
    int ivSize;
    String hashFunctions;

    public Algorithm(String cipher, int keySize) {
        this.cipher = cipher;
        this.keySize = keySize;
    }

    public Algorithm(String cipher, int keySize, String hashFunctions) {
        this.cipher = cipher;
        this.keySize = keySize;
        this.hashFunctions = hashFunctions;
    }

    public Algorithm(String cipher, String mode, String padding, int keySize, int ivSize) {
        this.cipher = cipher;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
        this.ivSize = ivSize;
    }

    public Algorithm(String cipher, String mode, String padding, int keySize) {
        this.cipher = cipher;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
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
        return this.hashFunctions + "with" + this.cipher;
    }
}
