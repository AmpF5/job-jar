package org.jobjar.feeder.adapters;

import lombok.RequiredArgsConstructor;
import models.dtos.OfferCreateDto;
import models.responses.TheProtocolResponse;
import org.jobjar.feeder.infrastructure.clients.BaseHttpClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheProtocolAdapter implements BaseAdapter {
    private final BaseHttpClient<TheProtocolResponse.TheProtocolOffer> theProtocolHttpClient;

    @Override
    public List<OfferCreateDto> getOffers() {
        var theProtocolJobOffers = theProtocolHttpClient.getRequest();
        return List.of();
    }
}
