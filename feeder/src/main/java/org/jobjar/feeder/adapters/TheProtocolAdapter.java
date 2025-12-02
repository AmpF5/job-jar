package org.jobjar.feeder.adapters;

import lombok.RequiredArgsConstructor;
import org.jobjar.feeder.infrastructure.clients.BaseHttpClient;
import org.jobjar.feeder.mappers.OfferMapper;
import org.jobjar.feeder.models.generics.OfferCreateDto;
import org.jobjar.feeder.models.responses.TheProtocolResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheProtocolAdapter implements BaseAdapter {
    private final BaseHttpClient<TheProtocolResponse.TheProtocolOffer> theProtocolHttpClient;

    @Override
    public List<OfferCreateDto> getOffers() {
        var theProtocolJobOffers = theProtocolHttpClient.getRequest();
        return theProtocolJobOffers.stream()
                .map(OfferMapper::toOfferCreateDto)
                .toList();
    }
}
