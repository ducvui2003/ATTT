package nlu.fit.leanhduc.service.cipher;

import nlu.fit.leanhduc.service.IFileEncrypt;
import nlu.fit.leanhduc.service.IFileKey;
import nlu.fit.leanhduc.service.ITextKey;

import javax.crypto.SecretKey;

public interface ITextCipherNative extends ITextKey<SecretKey>, IFileEncrypt, IFileKey {
    byte[] encrypt(String data) throws Exception;

    String decrypt(byte[] data) throws Exception;
}
