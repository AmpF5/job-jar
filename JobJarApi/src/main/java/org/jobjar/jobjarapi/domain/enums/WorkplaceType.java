package org.jobjar.jobjarapi.domain.enums;


import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public enum WorkplaceType {
    OFFICE, HYBRID, REMOTE, UNKNOWN;

    private static final Set<String> OFFICE_VARIANTS = Set.of("office");
    private static final Set<String> HYBRID_VARIANTS = Set.of("hybrid");
    private static final Set<String> REMOTE_VARIANTS= Set.of("remote");

    private static final Map<String, WorkplaceType> LOOKUP;

    static {
        var values = new HashMap<String, WorkplaceType>();
        OFFICE_VARIANTS.forEach(x -> values.put(x, WorkplaceType.OFFICE));
        REMOTE_VARIANTS.forEach(x -> values.put(x, WorkplaceType.REMOTE));
        HYBRID_VARIANTS.forEach(x -> values.put(x, WorkplaceType.HYBRID));
        LOOKUP = Collections.unmodifiableMap(values);
    }

    public static WorkplaceType map(String value) {
        if (value == null || value.isEmpty()) {
            return WorkplaceType.UNKNOWN;
        }

        var workplaceType = LOOKUP.get(value);
        if (workplaceType == null) {
            log.warn("Unknown Workplace Type: {}", value);
            return WorkplaceType.UNKNOWN;
        }

        return workplaceType;
    }
}
