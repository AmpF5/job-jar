package org.jobjar.jobjarapi.domain.dtos;

import lombok.Data;
import org.jobjar.jobjarapi.domain.enums.ExperienceLevel;
import org.jobjar.jobjarapi.domain.enums.JobSite;
import org.jobjar.jobjarapi.domain.enums.WorkplaceType;
import org.jobjar.jobjarapi.domain.models.entities.Skill;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
public final class OfferCreateDto {
    private final String guid;
    private final String title;
    private final String slug;
    private final WorkplaceType workplaceType;
    private final ExperienceLevel experienceLevel;
    private final JobSite jobSite;
    private final Float minimalWage;
    private final Float maximalWage;
    private final Instant publishedAt;
    private final Instant expiredAt;
    private Set<String> requiredSkills;

    public OfferCreateDto(
            String guid,
            String title,
            String slug,
            WorkplaceType workplaceType,
            ExperienceLevel experienceLevel,
            JobSite jobSite,
            Float minimalWage,
            Float maximalWage,
            Instant publishedAt,
            Instant expiredAt,
            Set<String> requiredSkills
    ) {
        this.guid = guid;
        this.title = title;
        this.slug = slug;
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
