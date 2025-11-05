package org.jobjar.muncher.models.dtos;

import org.jobjar.muncher.models.enums.ExperienceLevel;
import org.jobjar.muncher.models.enums.JobSite;
import org.jobjar.muncher.models.enums.WorkplaceType;

import java.util.Date;
import java.util.Set;

public record OfferCreateDto(
        String externalId,
        String title,
        String slug,
        String companyName,
        WorkplaceType workplaceType,
        ExperienceLevel experienceLevel,
        JobSite jobSite,
        Float minimalWage,
        Float maximalWage,
        Date publishedAt,
        Date expiredAt,
        Set<String> requiredSkills
) {
}
