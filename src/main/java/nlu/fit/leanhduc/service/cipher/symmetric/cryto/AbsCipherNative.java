package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public abstract class AbsCipherNative implements ICipherNative {

    protected Algorithm algorithm;

    public AbsCipherNative(Algorithm algorithm) {
        this.algorithm = algorithm;
    }


}
