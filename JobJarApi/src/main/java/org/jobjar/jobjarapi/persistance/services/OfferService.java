package org.jobjar.jobjarapi.persistance.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jobjar.jobjarapi.adapters.publishers.OfferEventManager;
import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;
import org.jobjar.jobjarapi.persistance.mappers.OfferMapper;
import org.jobjar.jobjarapi.persistance.repositories.OfferRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final ApplicationEventPublisher events;

//    public final OfferEventManager offerEventManager = new OfferEventManager();

    @Transactional
    public void bulkSaveOffers(List<OfferCreateDto> offers) {
        offerRepository.saveAndFlush(OfferMapper.toEntity(offers.get(1)));
        events.publishEvent(2L);
//       offerEventManager.notify();
    }
}
