package nlu.fit.leanhduc.util.constraint;

/**
 * Enum {@code Language}
 * <p>
 * Enum định nghĩa ngôn ngữ hỗ trợ
 * </p>
 */
public enum Language {
    VIETNAMESE, ENGLISH;

    @Override
    public String toString() {
        return switch (this) {
            case VIETNAMESE -> "Tiếng Việt";
            case ENGLISH -> "English";
        };
    }
}
