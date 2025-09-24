package org.jobjar.jobjarapi.utils;

import java.util.List;

public abstract class StringNormalizer {
    private static final List<Character> COMMON_PUNCTUATIONS = List.of('-', '.', '_');
    public static String removePunctuations(String string) {
        return string
                .chars()
                .map(x -> {
                    if(COMMON_PUNCTUATIONS.contains((char)x)) {
                        return 0;
                    } else {
                        return x;
                    }
                })
                .toString();

    }
}
