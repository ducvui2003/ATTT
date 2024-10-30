package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.util.CipherException;

public interface ITextEncrypt {
    String encrypt(String plainText) throws CipherException;

    String decrypt(String encryptText) throws CipherException;
}
