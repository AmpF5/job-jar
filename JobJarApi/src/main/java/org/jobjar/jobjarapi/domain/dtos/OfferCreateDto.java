package org.jobjar.jobjarapi.domain.dtos;

import lombok.Data;
import org.jobjar.jobjarapi.domain.enums.ExperienceLevel;
import org.jobjar.jobjarapi.domain.enums.WorkplaceType;

import java.time.Instant;
import java.util.List;

@Data
public final class OfferCreateDto {
    // Should be filled after being inserted into database
    private long offerId;
    private final String guid;
    private final String title;
    private final String slug;
    private final WorkplaceType workplaceType;
    private final ExperienceLevel experienceLevel;
    private final Float minimalWage;
    private final Float maximalWage;
    private final Instant publishedAt;
    private final Instant expiredAt;

    public OfferCreateDto(
            String guid,
            String title,
            String slug,
            WorkplaceType workplaceType,
            ExperienceLevel experienceLevel,
            Float minimalWage,
            Float maximalWage,
            Instant publishedAt,
            Instant expiredAt
    ) {
        this.guid = guid;
        this.title = title;
        this.slug = slug;
        this.workplaceType = workplaceType;
        this.experienceLevel = experienceLevel;
        this.minimalWage = minimalWage;
        this.maximalWage = maximalWage;
        this.publishedAt = publishedAt;
        this.expiredAt = expiredAt;
    }
}
