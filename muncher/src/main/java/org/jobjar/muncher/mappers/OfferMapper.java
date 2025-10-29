package org.jobjar.muncher.mappers;


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


}
