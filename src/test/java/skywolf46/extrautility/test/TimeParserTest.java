package skywolf46.extrautility.test;

import skywolf46.extrautility.util.TimeParser;

public class TimeParserTest {
    public static void main(String[] args) {
        System.out.println(TimeParser.parseToMillisecond("450ms"));
        System.out.println(TimeParser.parseToMillisecond("2h1s450ms"));
    }
}
