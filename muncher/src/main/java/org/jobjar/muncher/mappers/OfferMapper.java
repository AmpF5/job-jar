package org.jobjar.muncher.mappers;


import org.jobjar.muncher.models.dtos.OfferCreateDto;
import org.jobjar.muncher.models.entities.Offer;


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
