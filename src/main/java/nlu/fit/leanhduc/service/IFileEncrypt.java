package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.util.CipherException;

import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public interface IFileEncrypt {
    boolean encrypt(String src, String dest) throws CipherException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, FileNotFoundException;

    boolean decrypt(String src, String dest) throws CipherException;
}
