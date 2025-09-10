package org.jobjar.jobjarapi.adapters;

import lombok.RequiredArgsConstructor;
import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;
import org.jobjar.jobjarapi.domain.models.responses.JustJoinItResponse;
import org.jobjar.jobjarapi.infrastructure.clients.BaseHttpClient;
import org.jobjar.jobjarapi.persistance.mappers.OfferMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class JustJoinItAdapter implements BaseAdapter {
    private final BaseHttpClient<JustJoinItResponse.JustJoinItJob> baseClient;

    @Override
    public List<OfferCreateDto> getOffers() {
        var justJoinItJobs= baseClient.getRequest();
        return prepareEntityData(justJoinItJobs);
    }

    private List<OfferCreateDto> prepareEntityData(List<JustJoinItResponse.JustJoinItJob> resp) {
        return resp
                .stream()
                .map(OfferMapper::toOfferCreateDto)
                .toList();
    }


}