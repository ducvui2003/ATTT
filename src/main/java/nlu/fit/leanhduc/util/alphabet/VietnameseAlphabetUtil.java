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
        int index = ((oct % Constraint.VIET_NAME_ALPHA_BET_SIZE) + Constraint.VIET_NAME_ALPHA_BET_SIZE) % Constraint.VIET_NAME_ALPHA_BET_SIZE;

        return isLowerCase ?
                Constraint.ALPHABET_ARRAY[index] :
                Character.toUpperCase(Constraint.ALPHABET_ARRAY[index]);
    }


    @Override
    public List<Character> generateAlphabet(boolean isRandom) {
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
