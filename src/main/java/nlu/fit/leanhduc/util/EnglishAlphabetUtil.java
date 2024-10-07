package nlu.fit.leanhduc.util;

import java.util.*;
import java.util.stream.IntStream;

public class EnglishAlphabetUtil {
    public static List<Character> generateAlphabet() {
        return generateAlphabet(true);
    }

    public static List<Character> generateAlphabet(boolean isRandom) {
        List<Character> alphabet = new ArrayList<>();
        IntStream.range(Constraint.FIRST_CHAR, Constraint.LAST_CHAR + 1).forEach(value -> alphabet.add((char) value));
        IntStream.range(Constraint.FIRST_CHAR_UPPER, Constraint.LAST_CHAR_UPPER + 1).forEach(value -> alphabet.add((char) value));
        if (isRandom) {
            Collections.shuffle(alphabet);
        }
        return alphabet;
    }
}
