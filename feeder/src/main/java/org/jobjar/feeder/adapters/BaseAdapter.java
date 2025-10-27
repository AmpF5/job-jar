package org.jobjar.feeder.adapters;


import models.dtos.OfferCreateDto;

import java.util.List;

public interface BaseAdapter {
    List<OfferCreateDto> getOffers();
}
