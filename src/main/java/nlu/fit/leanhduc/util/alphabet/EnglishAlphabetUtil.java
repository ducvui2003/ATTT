package nlu.fit.leanhduc.util.alphabet;

import nlu.fit.leanhduc.util.Constraint;

import java.util.*;
import java.util.stream.IntStream;

public class EnglishAlphabetUtil implements AlphabetUtil {
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

    @Override
    public char getChar(int index) {
        return getChar(index, true);
    }

    @Override
    public char getChar(int oct, boolean isLowerCase) {
        int index = ((oct % Constraint.ALPHABET_SIZE) + Constraint.ALPHABET_SIZE) % Constraint.ALPHABET_SIZE;

        return isLowerCase ?
                Constraint.ALPHABET_ARRAY[index] :
                Character.toUpperCase(Constraint.ALPHABET_ARRAY[index]);
    }

    @Override
    public int indexOf(char c) {
        return Constraint.ALPHABET.indexOf(Character.toLowerCase(c));
    }

    @Override
    public int getLength() {
        return Constraint.ALPHABET_SIZE;
    }

    @Override
    public char[] getAlphabet() {
        return Constraint.ALPHABET_ARRAY;
    }
}
