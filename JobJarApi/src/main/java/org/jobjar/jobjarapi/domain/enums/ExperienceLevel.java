package org.jobjar.jobjarapi.domain.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public enum ExperienceLevel {
    JUNIOR, MID, SENIOR, UNKNOWN;

    private static final Set<String> JUNIOR_VARIANTS = Set.of("junior");
    private static final Set<String> MID_VARIANTS = Set.of("mid");
    private static final Set<String> SENIOR_VARIANTS = Set.of("senior");
    private static final Map<String, ExperienceLevel> LOOKUP;

    static {
        var values = new HashMap<String, ExperienceLevel>();
        JUNIOR_VARIANTS.forEach(x -> values.put(x, ExperienceLevel.JUNIOR));
        MID_VARIANTS.forEach(x -> values.put(x, ExperienceLevel.MID));
        SENIOR_VARIANTS.forEach(x -> values.put(x, ExperienceLevel.SENIOR));
        LOOKUP = Collections.unmodifiableMap(values);
    }

    public static ExperienceLevel map(String value) {
        if (value == null || value.isEmpty()) {
            return UNKNOWN;
        }

        var experienceLevel = LOOKUP.get(value);
        if (experienceLevel == null) {
            log.warn("Unknown ExperienceLevel: {}", value);
            return UNKNOWN;
        }

        return experienceLevel;
    }
}
