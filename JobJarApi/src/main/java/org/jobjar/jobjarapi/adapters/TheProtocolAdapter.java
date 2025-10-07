package org.jobjar.jobjarapi.adapters;

import lombok.RequiredArgsConstructor;
import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;
import org.jobjar.jobjarapi.domain.models.responses.TheProtocolResponse;
import org.jobjar.jobjarapi.infrastructure.clients.BaseHttpClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheProtocolAdapter implements BaseAdapter{
    private final BaseHttpClient<TheProtocolResponse.TheProtocolOffer> theProtocolHttpClient;
    @Override
    public List<OfferCreateDto> getOffers() {
        var theProtocolJobOffers = theProtocolHttpClient.getRequest();
        return List.of();
    }
}
