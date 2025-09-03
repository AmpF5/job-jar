package org.jobjar.jobjarapi.persistance.helpers;

import org.jobjar.jobjarapi.domain.models.entities.Offer;
import org.jobjar.jobjarapi.persistance.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SkillHelper {
    private final SkillRepository skillRepository;

    public SkillHelper(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void handleRequiredSkills(Offer offer, List<String> requiredSkills) {
        var requiredSkillsToAdd = requiredSkills
                .stream()
                .map(x -> skillRepository
                        .findByVariant(x)
                        .orElseGet(() -> {
//                             TODO: Add log of new skill variant
                              System.out.println("No skill found for variant " + x);
                              return null;
                }))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        offer.setRequiredSkills(requiredSkillsToAdd);
    }
}
