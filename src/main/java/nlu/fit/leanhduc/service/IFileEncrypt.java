package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.util.CipherException;


public interface IFileEncrypt {
    boolean encrypt(String src, String dest) throws CipherException;

    boolean decrypt(String src, String dest) throws CipherException;
}
