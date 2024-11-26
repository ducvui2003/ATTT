package nlu.fit.leanhduc.service.digital;

import lombok.Data;
import nlu.fit.leanhduc.util.constraint.Cipher;
import nlu.fit.leanhduc.util.constraint.Hash;
import nlu.fit.leanhduc.util.constraint.Size;

import java.util.Set;

@Data
public class DigitalSignatureSpecification {
    Cipher cipher;
    Set<Hash> hashFunctions;
    Set<Size> keySize;

    private final static DigitalSignatureSpecification DSA = new DigitalSignatureSpecification(Cipher.DSA,
            Set.of(Hash.SHA_1, Hash.SHA_256, Hash.SHA_384, Hash.SHA_512),
            Set.of(Size.Size_128, Size.Size_256, Size.SIZE_384));

    public static DigitalSignatureSpecification findDigitalSignatureSpecification(Cipher cipher) {
        switch (cipher) {
            case DSA:
                return DSA;
            default:
                return null;
        }
    }

    public DigitalSignatureSpecification(Cipher cipher, Set<Hash> hashFunctions, Set<Size> keySize) {
        this.cipher = cipher;
        this.hashFunctions = hashFunctions;
        this.keySize = keySize;
    }
}
