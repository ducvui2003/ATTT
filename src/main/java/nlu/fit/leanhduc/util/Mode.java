package nlu.fit.leanhduc.util;

public enum Mode {
    CBC("CBC", "Cipher Block Chaining"),
    ECB("ECB", "Electronic Codebook"),
    CFB("CFB", "Cipher Feedback"),
    OFB("OFB", "Output Feedback"),
    CTR("CTR", "Counter"),
    GCM("GCM", "Galois/Counter Mode"),
    ;

    private final String name;
    private final String displayName;

    Mode(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
