package org.jobjar.feeder.models.generics;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public final class OfferCreateDto {
    private final String externalId;
    private final String title;
    private final String slug;
    private final String companyName;
    private final String workplaceType;
    private final String experienceLevel;
    private final String jobSite;
    private final Float minimalWage;
    private final Float maximalWage;
    private final Date publishedAt;
    private final Date expiredAt;
    private final Set<String> requiredSkills;

    public OfferCreateDto(
            String externalId,
            String title,
            String slug,
            String companyName,
            String workplaceType,
            String experienceLevel,
            String jobSite,
            Float minimalWage,
            Float maximalWage,
            Date publishedAt,
            Date expiredAt,
            Set<String> requiredSkills
    ) {
        this.externalId = externalId;
        this.title = title;
        this.slug = slug;
        this.companyName = companyName;
        this.workplaceType = workplaceType;
        this.experienceLevel = experienceLevel;
        this.jobSite = jobSite;
        this.minimalWage = minimalWage;
        this.maximalWage = maximalWage;
        this.publishedAt = publishedAt;
        this.expiredAt = expiredAt;
        this.requiredSkills = requiredSkills;
    }
}
