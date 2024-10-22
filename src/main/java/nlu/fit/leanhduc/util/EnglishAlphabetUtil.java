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

    public static char getBaseChar(char c) {
        return Character.isLowerCase(c) ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
    }

    public static char shiftChar(char c, int shift) {
        char base = getBaseChar(c);
        return (char) ((c - base + shift) % Constraint.ALPHABET_SIZE + base);
    }

    public static char getChar(int oct) {
        return getChar(oct, true);
    }

    public static char getChar(int oct, boolean isLowerCase) {
        char base = isLowerCase ? Constraint.FIRST_CHAR : Constraint.FIRST_CHAR_UPPER;
        char result = (char) ((oct - base) % Constraint.ALPHABET_SIZE + base);
        return result;
    }

    public static char getChar(char c, int shift) {
        return (char) ((c - 'A' + shift) % 26 + 'A');
    }
}
