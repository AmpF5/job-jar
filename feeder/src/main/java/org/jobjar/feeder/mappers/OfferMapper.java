package org.jobjar.feeder.mappers;

import org.jobjar.feeder.models.generics.OfferCreateDto;
import org.jobjar.feeder.models.responses.JustJoinItResponse;

import java.util.HashSet;

public class OfferMapper {
    public static OfferCreateDto toOfferCreateDto(JustJoinItResponse.JustJoinItJob job) {
        var employmentType = job.getEmploymentTypes().stream().findFirst().orElse(new JustJoinItResponse.EmploymentType());
        return new OfferCreateDto(
                job.getGuid(),
                job.getTitle(),
                job.getSlug(),
                job.getCompanyName(),
                job.getWorkplaceType(),
                job.getExperienceLevel(),
                job.getExperienceLevel(),
                employmentType.getFrom(),
                employmentType.getTo(),
                job.getPublishedAt(),
                job.getExpiredAt(),
                new HashSet<>(job.getRequiredSkills())
        );
    }
}
