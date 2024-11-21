package nlu.fit.leanhduc.service.key;

import lombok.Data;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
public class KeyAsymmetric {
    PublicKey publicKey;
    PrivateKey privateKey;
    String cipher;
    String mode;
    String padding;
    //  đơn vị: bit
    int keySize;
}
