package org.jobjar.muncher.utils;

import java.util.List;

public abstract class StringNormalizer {
    private static final List<Character> COMMON_PUNCTUATIONS = List.of('-', '.', '_');

    public static String normalizeSkillName(String skillName) {
        return StringNormalizer.removePunctuations(skillName.toLowerCase());
    }

    public static String removePunctuations(String string) {
        return string
                .chars()
                .map(x -> {
                    if (COMMON_PUNCTUATIONS.contains((char) x)) {
                        return 0;
                    } else {
                        return x;
                    }
                })
                .toString();
    }
}
