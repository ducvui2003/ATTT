package nlu.fit.leanhduc.service.cipher;

import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.util.convert.Base64ConversionStrategy;
import nlu.fit.leanhduc.util.convert.ByteConversionStrategy;

public abstract class AbsCipherNative<T> implements ICipher<T> {
    protected T key;
    protected Algorithm algorithm;
    protected ByteConversionStrategy conversionStrategy;

    public AbsCipherNative() {
        conversionStrategy = new Base64ConversionStrategy();
    }

    public AbsCipherNative(Algorithm algorithm) {
        this.algorithm = algorithm;
        conversionStrategy = new Base64ConversionStrategy();

    }

    public T getKey() {
        return key;
    }
}
