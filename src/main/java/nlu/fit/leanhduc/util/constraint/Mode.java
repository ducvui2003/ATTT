package nlu.fit.leanhduc.util.constraint;

import lombok.Getter;

/**
 * Enum {@code Mode}
 * <p>
 * Enum định nghĩa các chế độ mã hóa hỗ trợ
 * </p>
 */
public enum Mode {
    NONE("None", "None", false),
    CBC("CBC", "Cipher Block Chaining", true),
    PCBC("PCBC", "Propagating Cipher Block Chaining", true),
    ECB("ECB", "Electronic Codebook", false),
    CFB("CFB", "Cipher Feedback Block", true),
    CFB8("CFB8", "Cipher Feedback Block 8", true),
    CFB16("CFB16", "Cipher Feedback Block 16", true),
    CFB48("CFB48", "Cipher Feedback Block 48", true),
    CFB64("CFB64", "Cipher Feedback Block 64", true),
    CFB128("CFB128", "Cipher Feedback Block 128", true),
    OFB("OFB", "Output Feedback Block", true),
    OFB8("OFB8", "Output Feedback Block 8", true),
    OFB16("OFB16", "Output Feedback Block 16", true),
    OFB48("OFB48", "Output Feedback Block 48", true),
    OFB64("OFB64", "Output Feedback Block 64", true),
    OFB128("OFB128", "Output Feedback Block 128", true),
    CTR("CTR", "Counter Block", true),
    CTS("CTS", "Cipher text stealing", true);


    @Getter
    private final String name;
    @Getter
    private final String displayName;
    private final boolean requireIV;

    Mode(String name, String displayName, boolean requireIV) {
        this.name = name;
        this.displayName = displayName;
        this.requireIV = requireIV;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
