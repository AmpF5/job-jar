package org.jobjar.jobjarapi.adapters.listeners;

import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;

public interface EventListener {
    void update(OfferCreateDto offer);
}
