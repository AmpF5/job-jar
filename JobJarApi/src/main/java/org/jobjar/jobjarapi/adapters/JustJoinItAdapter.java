package org.jobjar.jobjarapi.adapters;

import org.antlr.v4.runtime.misc.Pair;
import org.jobjar.jobjarapi.domain.models.entities.Offer;
import org.jobjar.jobjarapi.domain.models.entities.Skill;
import org.jobjar.jobjarapi.domain.models.responses.JustJoinItResponse;
import org.jobjar.jobjarapi.infrastructure.clients.BaseHttpClient;
import org.jobjar.jobjarapi.infrastructure.clients.JustJoinItHttpClient;
import org.jobjar.jobjarapi.persistance.mappers.JustJoinItMapper;
import org.jobjar.jobjarapi.persistance.mappers.SkillMapper;
import org.jobjar.jobjarapi.persistance.repositories.OfferRepository;
import org.jobjar.jobjarapi.persistance.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class JustJoinItAdapter implements BaseAdapter {
    private final BaseHttpClient<JustJoinItResponse.JustJoinItJob> baseClient;

    private final OfferRepository offerRepository;

    private final SkillRepository skillRepository;
    public JustJoinItAdapter(JustJoinItHttpClient client, OfferRepository offerRepository, SkillRepository skillRepository) {
        baseClient = client;
        this.offerRepository = offerRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public void getOffers() {
        var justJoinItJobs= baseClient.getRequest();

        var skill = skillRepository.findByVariant("jAVa");
        System.out.println(skill.toString());
    }

    private List<Offer> prepareEntityData(List<JustJoinItResponse.JustJoinItJob> resp) {
       var pairs = resp
               .stream()
               .map(x -> new Pair<>(JustJoinItMapper.toOffer(x), x))
               .toList();

        pairs.forEach(x ->  {
            mapSkills(x);
//            mapCompanies(x);
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

}