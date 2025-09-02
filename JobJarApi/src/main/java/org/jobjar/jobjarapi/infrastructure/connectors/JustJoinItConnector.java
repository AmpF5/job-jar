package org.jobjar.jobjarapi.infrastructure.connectors;

import org.antlr.v4.runtime.misc.Pair;
import org.jobjar.jobjarapi.domain.entity.Offer;
import org.jobjar.jobjarapi.domain.entity.Skill;
import org.jobjar.jobjarapi.domain.responses.JustJoinItResponse;
import org.jobjar.jobjarapi.infrastructure.clients.BaseHttpClient;
import org.jobjar.jobjarapi.infrastructure.clients.JustJoinItHttpClient;
import org.jobjar.jobjarapi.infrastructure.mappers.JustJoinItMapper;
import org.jobjar.jobjarapi.infrastructure.mappers.SkillMapper;
import org.jobjar.jobjarapi.infrastructure.repositories.OfferRepository;
import org.jobjar.jobjarapi.infrastructure.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class JustJoinItConnector implements BaseConnector {
    private final BaseHttpClient<JustJoinItResponse.JustJoinItJob> baseClient;

    private final OfferRepository offerRepository;

    private final SkillRepository skillRepository;
    public JustJoinItConnector(JustJoinItHttpClient client, OfferRepository offerRepository, SkillRepository skillRepository) {
        baseClient = client;
        this.offerRepository = offerRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public void getOffers() {
        var justJoinItJobs= baseClient.getRequest();
    }

    private List<Offer> prepareEntityData(List<JustJoinItResponse.JustJoinItJob> resp) {
       var pairs = resp
               .stream()
               .map(x -> new Pair<>(JustJoinItMapper.toOffer(x), x))
               .toList();

        pairs.forEach(x ->  {
            mapSkills(x);
            mapCompanies(x);
        });

        return pairs
                .stream()
                .map(x -> x.a)
                .toList();
    }

    public void mapSkills(Pair<Offer, JustJoinItResponse.JustJoinItJob> pair) {
        var normalizedSkills= pair.b
                .getRequiredSkills()
                .stream()
                .map(SkillMapper::normalize)
                .collect(Collectors.toSet());

        var skills = normalizedSkills
                .stream()
                .map(x -> skillRepository
                        .findByName(x)
                        .orElse(skillRepository.save(new Skill(x)))
                )
                .collect(Collectors.toSet());

        pair.a.setRequiredSkills(skills);
    }

    public void mapCompanies(Pair<Offer, JustJoinItResponse.JustJoinItJob> pair) {}
}