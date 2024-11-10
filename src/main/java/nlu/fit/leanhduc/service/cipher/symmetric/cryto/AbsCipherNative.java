package nlu.fit.leanhduc.service.cipher.symmetric.cryto;

public abstract class AbsCipherNative implements ICipherNative {

    protected Algorithm algorithm;

    public AbsCipherNative(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}
