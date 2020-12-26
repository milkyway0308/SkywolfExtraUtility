package skywolf46.extrautility.util;

import skywolf46.extrautility.collections.lists.CharacterList;

public class CharacterUtil {
    private static CharacterList WHITESPACE_IGNORE_LIST =
            new CharacterList().append(' ');

    public static boolean isDigit(String x) {
        if (x.length() <= 0)
            return false;
        for (int i = 0; i < x.length(); i++) {
            if (!isDigit(x.charAt(i)))
                return false;
        }
        return true;
    }


    public static boolean containsDigit(String x) {
        if (x.length() <= 0)
            return false;
        for (int i = 0; i < x.length(); i++) {
            if (isDigit(x.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean isKorean(String x) {
        if (x.length() <= 0)
            return false;
        for (int i = 0; i < x.length(); i++) {
            if (!isKorean(x.charAt(i)))
                return false;
        }
        return true;
    }


    public static boolean containsKorean(String x) {
        if (x.length() <= 0)
            return false;
        for (int i = 0; i < x.length(); i++) {
            if (isKorean(x.charAt(i)))
                return true;
        }
        return false;
    }


    public static boolean isEnglish(String x, boolean containsUppercase) {
        if (x.length() <= 0)
            return false;
        for (int i = 0; i < x.length(); i++) {
            if (!isEnglish(x.charAt(i), containsUppercase))
                return false;
        }
        return true;
    }

    public static boolean isEnglish(String x) {
        return isEnglish(x, true);
    }

    public static boolean containsEnglish(String x, boolean containsUppercase) {
        if (x.length() <= 0)
            return false;
        for (int i = 0; i < x.length(); i++) {
            if (isEnglish(x.charAt(i), containsUppercase))
                return true;
        }
        return false;
    }

    public static boolean containsEnglish(String x) {
        return containsEnglish(x, true);
    }

    public static boolean isEnglishUppercase(String x) {
        if (x.length() <= 0)
            return false;
        for (int i = 0; i < x.length(); i++) {
            if (!isEnglishUppercase(x.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean containsEnglishUppercase(String x) {
        for (int i = 0; i < x.length(); i++) {
            if (isEnglishUppercase(x.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean isSpecialCharacter(String x) {
        for (int i = 0; i < x.length(); i++) {
            if (!isSpecialCharacter(x.charAt(i)))
                return false;
        }
        return true;
    }


    public static boolean isSpecialCharacter(String x, CharacterList ignoreList) {
        for (int i = 0; i < x.length(); i++) {
            if (!isSpecialCharacter(x.charAt(i), ignoreList))
                return false;
        }
        return true;
    }


    public static boolean containsSpecialCharacter(String x) {
        for (int i = 0; i < x.length(); i++) {
            if (isSpecialCharacter(x.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean containsSpecialCharacter(String x, boolean ignoreSpace) {
        for (int i = 0; i < x.length(); i++) {
            if (!ignoreSpace) {
                if (isSpecialCharacter(x.charAt(i)))
                    return true;
            } else {
                if (isSpecialCharacter(x.charAt(i), WHITESPACE_IGNORE_LIST))
                    return true;
            }
        }
        return false;
    }


    public static boolean containsSpecialCharacter(String x, CharacterList ignoreList) {
        for (int i = 0; i < x.length(); i++) {
            if (isSpecialCharacter(x.charAt(i), ignoreList))
                return true;
        }
        return false;
    }


    public static boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }

    public static boolean isKorean(char character) {
        return character >= '가' && character <= '힣';
    }

    public static boolean isEnglish(char character, boolean containsUppercase) {
        return (character >= 'a' && character <= 'z') || (containsUppercase && isEnglishUppercase(character));
    }


    public static boolean isEnglish(char character) {
        return isEnglish(character, true);
    }

    public static boolean isEnglishUppercase(char character) {
        return character >= 'A' && character <= 'Z';
    }

    public static boolean isSpecialCharacter(char c) {
        return !isEnglish(c) && !isKorean(c);
    }

    public static boolean isSpecialCharacter(char c, CharacterList ignoreList) {
        if (ignoreList.contains(c))
            return false;
        return isSpecialCharacter(c);
    }

}
