package nlu.fit.leanhduc.service.base64;

import nlu.fit.leanhduc.service.ITextEncrypt;

import java.util.Base64;

public class Base64Encoding implements ITextEncrypt {
    public String encrypt(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public String decrypt(String input) {
        return new String(Base64.getDecoder().decode(input.toLowerCase()));
    }
}
