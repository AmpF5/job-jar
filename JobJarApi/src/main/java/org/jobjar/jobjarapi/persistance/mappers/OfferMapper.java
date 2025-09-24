package org.jobjar.jobjarapi.persistance.mappers;

import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;
import org.jobjar.jobjarapi.domain.enums.ExperienceLevel;
import org.jobjar.jobjarapi.domain.enums.JobSite;
import org.jobjar.jobjarapi.domain.enums.WorkplaceType;
import org.jobjar.jobjarapi.domain.models.entities.Offer;
import org.jobjar.jobjarapi.domain.models.responses.JustJoinItResponse;

import java.util.HashSet;

public final class OfferMapper {
    public static Offer toEntity(OfferCreateDto offerCreateDto) {
        return new Offer(
                offerCreateDto.getGuid(),
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
        return new OfferCreateDto(
                job.getGuid(),
                job.getTitle(),
                job.getSlug(),
                WorkplaceType.map(job.getWorkplaceType()),
                ExperienceLevel.map(job.getExperienceLevel()),
                JobSite.JUSTJOINIT,
                1000f,
                2000f,
                job.getPublishedAt(),
                job.getExpiredAt(),
                new HashSet<>(job.getRequiredSkills())
        );
    }
}
