package nlu.fit.leanhduc.service;

public interface IByteEncrypt {
    byte[] encrypt(String data);

    String decrypt(byte[] data);
}
