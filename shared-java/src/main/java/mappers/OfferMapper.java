package mappers;


import models.dtos.OfferCreateDto;
import models.entities.Offer;
import models.enums.ExperienceLevel;
import models.enums.JobSite;
import models.enums.WorkplaceType;
import models.responses.JustJoinItResponse;

import java.util.HashSet;

public final class OfferMapper {
    public static Offer toEntity(OfferCreateDto offerCreateDto) {
        return new Offer(
                offerCreateDto.getExternalId(),
                offerCreateDto.getTitle(),
                offerCreateDto.getSlug(),
                offerCreateDto.getWorkplaceType(),
                offerCreateDto.getExperienceLevel(),
                offerCreateDto.getMinimalWage(),
                offerCreateDto.getMaximalWage(),
                offerCreateDto.getPublishedAt(),
                offerCreateDto.getExpiredAt(),
                offerCreateDto.getJobSite()
        );
    }

    public static OfferCreateDto toOfferCreateDto(JustJoinItResponse.JustJoinItJob job) {
        var employmentType = job.getEmploymentTypes().stream().findFirst().orElse(new JustJoinItResponse.EmploymentType());
        return new OfferCreateDto(
                job.getGuid(),
                job.getTitle(),
                job.getSlug(),
                job.getCompanyName(),
                WorkplaceType.map(job.getWorkplaceType()),
                ExperienceLevel.map(job.getExperienceLevel()),
                JobSite.JUST_JOIN_IT,
                employmentType.getFrom(),
                employmentType.getTo(),
                job.getPublishedAt(),
                job.getExpiredAt(),
                new HashSet<>(job.getRequiredSkills())
        );
    }
}
