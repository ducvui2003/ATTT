package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.util.CipherException;

import java.io.File;

public interface IAsymmetricEncrypt {
    String encrypt(String plainText) throws CipherException;

    String decrypt(String encryptText) throws CipherException;

    String encrypt(File file) throws CipherException;

    String decrypt(File cryptFile) throws CipherException;
}
