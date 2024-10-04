package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.util.CipherException;

import java.io.File;

public interface IAsymmetricEncrypt<T> {
    String encrypt(String plainText, T publicKey) throws CipherException;

    String decrypt(String plainText, T publicKey) throws CipherException;

    String encrypt(File file, T publicKey) throws CipherException;

    String decrypt(File file, T publicKey) throws CipherException;
}
