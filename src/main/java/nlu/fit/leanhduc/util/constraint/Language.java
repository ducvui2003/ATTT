package nlu.fit.leanhduc.util.constraint;

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
