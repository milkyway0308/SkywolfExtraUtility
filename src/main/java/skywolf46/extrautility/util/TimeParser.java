package skywolf46.extrautility.util;

import java.util.HashMap;
import java.util.function.Function;

public class TimeParser {
    private static ParsingType parser;

    static {
        parser = new ParsingType();
        parser.append("ms", Integer::parseInt);
        parser.append("s", x -> Integer.parseInt(x) * 1000);
        parser.append("m", x -> Integer.parseInt(x) * 60 * 1000);
        parser.append("h", x -> Integer.parseInt(x) * 60 * 60 * 1000);
        parser.append("d", x -> Integer.parseInt(x) * 24 * 60 * 60 * 1000);
    }

    public static long parseToMillisecond(String x, ParsingType parser) {
        long total = 0;
        int toParse = 0;
        int lastParsed = 0;
        for (int i = 0; i < x.length(); i++) {
            char cx = x.charAt(i);
            if (CharacterUtil.isDigit(cx)) {
                if (toParse != lastParsed) {
                    long am = parse(x, x.substring(lastParsed, toParse + 1), parser);
                    if (am != Integer.MIN_VALUE) {
                        total += am;
                        toParse = i;
                        lastParsed = i;
                    }
                }
            } else {
                toParse = i;
            }
        }
        if (toParse != lastParsed) {
            long am = parse(x, x.substring(lastParsed), parser);
            if (am != Integer.MIN_VALUE)
                total += am;
        }
        return total;
    }

    public static long parseToMillisecond(String x) {
        return parseToMillisecond(x, parser);
    }

    private static long parse(String original, String toParse, ParsingType parser) {
        String target = null;
        for (int i = 0; i < toParse.length(); i++) {
            if (!CharacterUtil.isDigit(toParse.charAt(i))) {
                target = toParse.substring(0, i);
                toParse = toParse.substring(i);
                break;
            }
        }
        if (target == null)
            throw new IllegalStateException("Time is not number");
        if (!parser.canAccept(toParse))
            throw new IllegalStateException("Cannot parse \"" + toParse + "\" at \"" + original + "\" : Not a parsing target");
        Function<String, Integer> fi = parser.parser(toParse);
        if (fi == null)
            return Integer.MIN_VALUE;
        return fi.apply(target);
    }


    public static class ParsingType {
        private HashMap<String, Function<String, Integer>> parsers = new HashMap<>();

        private int maxLength = 0;

        public ParsingType append(String type, Function<String, Integer> funct) {
            parsers.put(type, funct);
            maxLength = Math.max(type.length(), maxLength);
            return this;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public Function<String, Integer> parser(String x) {
            return parsers.get(x);
        }

        public boolean canAccept(String toParse) {
            return toParse.length() <= maxLength;
        }
    }

}
