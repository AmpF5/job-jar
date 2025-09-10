package org.jobjar.jobjarapi.domain.enums;

import org.jobjar.jobjarapi.domain.models.responses.JustJoinItResponse;

import java.util.*;

public enum WorkplaceType {
    OFFICE, HYBRID, REMOTE;

    private static final Set<String> officeVariants = Set.of("office");
    private static final Set<String> hybridVariants = Set.of("hybrid");
    private static final Set<String> remoteVariants = Set.of("remote");

    private static final Map<String, WorkplaceType> LOOKUP;
    static {
        var values = new HashMap<String, WorkplaceType>();
        officeVariants.forEach(x -> values.put(x, WorkplaceType.OFFICE));
        remoteVariants.forEach(x -> values.put(x, WorkplaceType.REMOTE));
        hybridVariants.forEach(x -> values.put(x, WorkplaceType.HYBRID));
        LOOKUP = Collections.unmodifiableMap(values);
    }

    public static Optional<WorkplaceType> fromJob(JustJoinItResponse.JustJoinItJob job) {
        return Optional.ofNullable(LOOKUP.get(job.getWorkplaceType().toLowerCase()));
    }
}
