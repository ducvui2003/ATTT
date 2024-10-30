package nlu.fit.leanhduc.util;

import java.util.*;

public class VietnameseAlphabetUtil {
    private VietnameseAlphabetUtil() {
    }

    /**
     * Trả về vị trí của ký tự trong mảng chữ cái Tiếng Việt
     * @param c ký tự Tiếng Việt
     * @return int vị trí của ký tự trong bảng chữ cái
     */
    public static int indexOf(char c) {
        return Constraint.VIET_NAM_N.indexOf(Character.toLowerCase(c));
    }

    /**
     * Trả về ký tự trong mảng chữ cái Tiếng Việt dựa theo vị trí
     * @param index ký tự Tiếng Việt
     * @return int vị trí của ký tự trong bảng chữ cái
     */
    public static char getChar(int index) {
        return Constraint.VIET_NAM_N.charAt(index % Constraint.VIET_NAME_ALPHA_BET_SIZE);
    }

    /**
     * Trả về ký tự trong mảng chữ cái Tiếng Việt dựa theo vị trí
     * @param index ký tự Tiếng Việt
     * @return vị trí của ký tự trong bảng chữ cái
     */
    public static char getChar(int index, boolean isLowerCase) {
        char c = Constraint.VIET_NAM_N.charAt(index);
        if (isLowerCase)
            return Character.toLowerCase(c);
        else
            return Character.toUpperCase(c);
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
