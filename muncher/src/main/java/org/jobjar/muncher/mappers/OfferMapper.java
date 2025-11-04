package org.jobjar.muncher.mappers;


import org.jobjar.muncher.models.dtos.OfferCreateDto;
import org.jobjar.muncher.models.entities.Offer;


public final class OfferMapper {
    public static Offer toEntity(OfferCreateDto offerCreateDto) {
        return new Offer(
                offerCreateDto.externalId(),
                offerCreateDto.title(),
                offerCreateDto.slug(),
                offerCreateDto.workplaceType(),
                offerCreateDto.experienceLevel(),
                offerCreateDto.minimalWage(),
                offerCreateDto.maximalWage(),
                offerCreateDto.publishedAt(),
                offerCreateDto.expiredAt(),
                offerCreateDto.jobSite()
        );
    }


}
