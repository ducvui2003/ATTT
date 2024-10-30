package nlu.fit.leanhduc.util.alphabet;

import nlu.fit.leanhduc.util.Constraint;

import java.util.*;

public class VietnameseAlphabetUtil implements AlphabetUtil {
    @Override
    public int indexOf(char c) {
        return Constraint.VIET_NAM_N.indexOf(c);
    }

    @Override
    public char getChar(int index) {
        return Constraint.VIET_NAM_N.charAt(index);
    }

    @Override
    public char getChar(int oct, boolean isLowerCase) {
        return isLowerCase ?
                Constraint.VIET_NAME_ALPHA_ARRAY[oct % Constraint.VIET_NAME_ALPHA_BET_SIZE] :
                Character.toUpperCase(Constraint.VIET_NAME_ALPHA_ARRAY[oct % Constraint.VIET_NAME_ALPHA_BET_SIZE]);
    }


    public static List<Character> generateAlphabet() {
        return generateAlphabet(true);
    }

    public static List<Character> generateAlphabet(boolean isRandom) {
        List<Character> alphabet = new ArrayList<>();
        for (char c : Constraint.VIET_NAME_ALPHA_ARRAY) {
            alphabet.add(c);
        }
        for (char c : Constraint.VIET_NAME_ALPHA_ARRAY) {
            alphabet.add(Character.toUpperCase(c));
        }
        if (isRandom) {
            Collections.shuffle(alphabet);
        }
        return alphabet;
    }

    @Override
    public int getLength() {
        return Constraint.VIET_NAME_ALPHA_BET_SIZE;
    }

    @Override
    public char[] getAlphabet() {
        return Constraint.VIET_NAME_ALPHA_ARRAY;
    }
}
