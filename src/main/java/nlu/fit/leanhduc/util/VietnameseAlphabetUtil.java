package nlu.fit.leanhduc.util;

import java.util.*;

public class VietnameseAlphabetUtil {
    private VietnameseAlphabetUtil() {
    }

    public static int indexOf(char c) {
        return Constraint.VIET_NAM_N.indexOf(c);
    }

    public static char getChar(int index) {
        return Constraint.VIET_NAM_N.charAt(index);
    }


    public static List<Character> generateAlphabet() {
        return generateAlphabet(true);
    }

    public static List<Character> generateAlphabet(boolean isRandom) {
        List<Character> alphabet = new ArrayList<>();
        for (char c : Constraint.VIET_NAME_ALPHA_BET) {
            alphabet.add(c);
        }
        for (char c : Constraint.VIET_NAME_ALPHA_BET) {
            alphabet.add(Character.toUpperCase(c));
        }
        if (isRandom) {
            Collections.shuffle(alphabet);
        }
        return alphabet;
    }
}
