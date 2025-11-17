package org.jobjar.muncher.mappers;


import org.jobjar.muncher.models.dtos.OfferCreateDto;
import org.jobjar.muncher.models.entities.Offer;

import java.util.UUID;

public final class OfferMapper {
    public static Offer toEntity(OfferCreateDto offerCreateDto) {
        return new Offer(
                UUID.fromString(offerCreateDto.externalId()),
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

    public static void updateOffer(Offer offer, OfferCreateDto offerCreateDto) {
       offer.setTitle(offerCreateDto.title());
       offer.setMinimalWage(offerCreateDto.minimalWage());
       offer.setMaximalWage(offerCreateDto.maximalWage());
       offer.setPublishedAt(offerCreateDto.publishedAt());
       offer.setExpiredAt(offerCreateDto.expiredAt());
    }
}
