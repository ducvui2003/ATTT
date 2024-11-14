package nlu.fit.leanhduc.service;

import nlu.fit.leanhduc.util.CipherException;

public interface ITextKey<T> {
    void loadKey(T key) throws CipherException;

    T generateKey();


}
