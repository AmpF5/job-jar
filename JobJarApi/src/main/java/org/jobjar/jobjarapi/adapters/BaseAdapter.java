package org.jobjar.jobjarapi.adapters;

import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;

import java.util.List;

public interface BaseAdapter {
    List<OfferCreateDto> getOffers();
}
