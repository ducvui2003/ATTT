package nlu.fit.leanhduc.service.cipher.symmetric;

import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

import javax.crypto.SecretKey;

public abstract class AbsCipherNative<T> implements ICipher<T> {
    T t;
    protected Algorithm algorithm;
    protected ByteConversionStrategy conversionStrategy;

    public AbsCipherNative() {
        conversionStrategy = new Base64ConversionStrategy();
    }

    public AbsCipherNative(Algorithm algorithm) {
        this.algorithm = algorithm;
        conversionStrategy = new Base64ConversionStrategy();
    
    }

}
