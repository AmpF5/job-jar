package org.jobjar.feeder.adapters;

import lombok.RequiredArgsConstructor;
import org.jobjar.feeder.infrastructure.clients.BaseHttpClient;
import org.jobjar.feeder.mappers.OfferMapper;
import org.jobjar.feeder.models.generics.OfferCreateDto;
import org.jobjar.feeder.models.responses.JustJoinItResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class JustJoinItAdapter implements BaseAdapter {
    private final BaseHttpClient<JustJoinItResponse.JustJoinItJob> baseClient;

    @Override
    public List<OfferCreateDto> getOffers() {
        var justJoinItJobs = baseClient.getRequest();
        return justJoinItJobs.stream()
                .map(OfferMapper::toOfferCreateDto)
                .toList();
    }
}
