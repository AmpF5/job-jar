package org.jobjar.jobjarapi.adapters;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.jobjar.jobjarapi.domain.models.entities.Offer;
import org.jobjar.jobjarapi.domain.models.entities.Skill;
import org.jobjar.jobjarapi.domain.models.responses.JustJoinItResponse;
import org.jobjar.jobjarapi.infrastructure.clients.BaseHttpClient;
import org.jobjar.jobjarapi.infrastructure.clients.JustJoinItHttpClient;
import org.jobjar.jobjarapi.persistance.helpers.SkillHelper;
import org.jobjar.jobjarapi.persistance.mappers.JustJoinItMapper;
import org.jobjar.jobjarapi.persistance.mappers.SkillMapper;
import org.jobjar.jobjarapi.persistance.repositories.OfferRepository;
import org.jobjar.jobjarapi.persistance.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JustJoinItAdapter implements BaseAdapter {
    private final BaseHttpClient<JustJoinItResponse.JustJoinItJob> baseClient;
    private final SkillHelper skillHelper;

    @Override
    public void getOffers() {
        var justJoinItJobs= baseClient.getRequest();
        var offers = prepareEntityData(justJoinItJobs);
    }

    private List<Offer> prepareEntityData(List<JustJoinItResponse.JustJoinItJob> resp) {
       var pairs = resp
               .stream()
               .map(x -> new Pair<>(JustJoinItMapper.toOffer(x), x))
               .toList();

        pairs.forEach(x -> {
           skillHelper.handleRequiredSkills(x.a, x.b.getRequiredSkills());
        });

        return pairs
                .stream()
                .map(x -> x.a)
                .toList();
    }
}