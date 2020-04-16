package com.gfg.encodedid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EncodedId {
    private static final int SHORT = 5;
    private static final int MEDIUM = 7;

    private static final Character[] DICTIONARY =
            new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final int BASE = DICTIONARY.length;

    public static String encodeShort(Long value) {
        return encode(value, SHORT);
    }

    public static String encodeMedium(Long value) {
        return encode(value, MEDIUM);
    }

    public static Long decodeShort(String value) {
        return decode(value, SHORT);
    }

    public static Long decodeMedium(String value) {
        return decode(value, MEDIUM);
    }

    public static String encode(Long value, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length of encoded id should be bigger then 1");
        }

        length--;
        double number = value.doubleValue();

        if (length > 0) {
            number += Math.pow(BASE, length);
        }

        List<Character> result = new ArrayList<>();

        for (double t = (number != 0 ? Math.floor(log(number)) : 0); t >= 0; t--) {
            double bcp = Math.pow(BASE, t);
            int a = (int) (Math.floor(number / bcp) % BASE);
            result.add(DICTIONARY[a]);
            number = number - (a * bcp);
        }

        StringBuilder sb = new StringBuilder();

        for (Character character : result) {
            sb.append(character);
        }

        return sb.toString();
    }

    public static Long decode(String encoded, int length) {
        long out = 0L;

        List<Character> dictionaryList = Arrays.asList(DICTIONARY);

        for (int t = encoded.length() - 1; t >= 0; t--) {
            int bcp = bcpow(encoded.length() - 1 - t);
            out += dictionaryList.indexOf(encoded.charAt(t)) * bcp;
        }

        out -= (int) Math.pow(BASE, length - 1);

        return out;
    }

    private static double log(double number) {
        return Math.log(number) / Math.log(BASE);
    }

    private static int bcpow(int exp) {
        if (exp == 0) {
            return 1;
        }

        int result = 0;

        while (exp > 0) {
            int expSub = Math.min(exp, Integer.MAX_VALUE);
            exp -= expSub;
            result += Math.pow(BASE, expSub);
        }

        return result;
    }
}
