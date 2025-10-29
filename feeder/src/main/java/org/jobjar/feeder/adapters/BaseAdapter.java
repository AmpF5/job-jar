package org.jobjar.feeder.adapters;



import org.jobjar.feeder.models.generics.OfferCreateDto;

import java.util.List;

public interface BaseAdapter {
    List<OfferCreateDto> getOffers();
}
