package nlu.fit.leanhduc.service.key;

import lombok.Data;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Data
public class KeySignature {

    PublicKey publicKey;
    PrivateKey privateKey;
    int size; // bit
    String algorithm;
    String hashFunction;

    public void setPublicKey(KeyFactory keyFactory, byte[] bytes) throws InvalidKeySpecException {
        X509EncodedKeySpec keySpecPublicKey = new X509EncodedKeySpec(bytes);
        this.publicKey = keyFactory.generatePublic(keySpecPublicKey);
    }

    public void setPrivateKey(KeyFactory keyFactory, byte[] bytes) throws InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpecPrivateKey = new PKCS8EncodedKeySpec(bytes);
        this.privateKey = keyFactory.generatePrivate(keySpecPrivateKey);
    }

}
