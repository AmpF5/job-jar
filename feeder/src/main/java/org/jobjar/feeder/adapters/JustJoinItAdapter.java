package org.jobjar.feeder.adapters;

import lombok.RequiredArgsConstructor;
import mappers.OfferMapper;
import models.dtos.OfferCreateDto;
import models.responses.JustJoinItResponse;
import org.jobjar.feeder.infrastructure.clients.BaseHttpClient;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class JustJoinItAdapter implements BaseAdapter {
    private final BaseHttpClient<JustJoinItResponse.JustJoinItJob> baseClient;

    @Override
    public List<OfferCreateDto> getOffers() {
        var justJoinItJobs = baseClient.getRequest();
        return prepareEntityData(justJoinItJobs);
    }

    private List<OfferCreateDto> prepareEntityData(List<JustJoinItResponse.JustJoinItJob> resp) {
        return resp
                .stream()
                .map(OfferMapper::toOfferCreateDto)
                .toList();
    }
}