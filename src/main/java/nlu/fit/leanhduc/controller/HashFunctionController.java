package nlu.fit.leanhduc.controller;

import nlu.fit.leanhduc.service.hash.AbsHash;
import nlu.fit.leanhduc.service.hash.HashFunction;
import nlu.fit.leanhduc.util.constraint.Hash;

import java.util.List;

public class HashFunctionController {
    public static HashFunctionController INSTANCE;

    private HashFunctionController() {
    }

    public static HashFunctionController getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new HashFunctionController();
        }
        return INSTANCE;
    }


    public List<Hash> getHash() {
        return List.of(Hash.values());
    }


    public String hash(Hash hash, String key, String plainText) {
        AbsHash hashFunction = new HashFunction(hash);
        try {
            if (key == null || key.isEmpty()) {
                return hashFunction.hash(plainText);
            }
            return hashFunction.hashWithHMAC(plainText, key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String hashFile(Hash hash, String key, String filename) {
        AbsHash hashFunction = new HashFunction(hash);
        try {
            if (key == null || key.isEmpty()) {
                return hashFunction.hashFile(filename);
            }
            return hashFunction.hashFileWithHMAC(filename, key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
