package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

import nlu.fit.leanhduc.service.IFileEncrypt;
import nlu.fit.leanhduc.service.IKeyGenerator;

import javax.crypto.SecretKey;

public interface ICipherNative extends IKeyGenerator<SecretKey>, IFileEncrypt {
    byte[] encrypt(String data) throws Exception; // Change from byte[] to String

    String decrypt(byte[] data) throws Exception; // Change return type to String
}
