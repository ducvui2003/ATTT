package nlu.fit.leanhduc.service.cipher.hillCipher;

import nlu.fit.leanhduc.service.IKeyGenerator;
import nlu.fit.leanhduc.service.ITextEncrypt;
import nlu.fit.leanhduc.service.key.HillKey;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.MatrixUtil;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

public abstract class HillCipher implements IKeyGenerator<HillKey>, ITextEncrypt {
    protected HillKey key;
    protected AlphabetUtil alphabetUtil;

    @Override
    public void loadKey(HillKey key) throws CipherException {
        this.key = key;
    }

    @Override
    public HillKey generateKey() {
        return null;
    }

    @Override
    public String encrypt(String plainText) throws CipherException {
        String result = "";
        int lengthText = plainText.length();
        int lengthKey = this.key.getKey().length;
        int start = 0;
        while (start < lengthText) {
            int[] plainTextArr = new int[lengthKey];
            boolean[] isLower = new boolean[lengthKey];
            int lengthKeyTemp = 0;
            while (lengthKeyTemp < lengthKey) {
                char text = plainText.charAt(start);
                if (Character.isLetter(text)) {
                    isLower[lengthKeyTemp] = Character.isLowerCase(text);
                    plainTextArr[lengthKeyTemp] = Character.toLowerCase(text);
                    lengthKeyTemp++;
                }
                start++;
            }
            int[] encryptArr = MatrixUtil.multiMatrix(plainTextArr, this.key.getKey());
            lengthKeyTemp = 0;
            for (int i = 0; i < lengthKey; i++) {
                result += alphabetUtil.getChar(encryptArr[i], isLower[lengthKeyTemp]);
            }
        }

        return result;
    }

    @Override
    public String decrypt(String encryptText) throws CipherException {
        return "";
    }
}
