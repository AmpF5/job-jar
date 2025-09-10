package org.jobjar.jobjarapi.persistance.services;

import org.jobjar.jobjarapi.adapters.listeners.SkillSnapshotListener;
import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;
import org.jobjar.jobjarapi.domain.models.entities.Skill;
import org.jobjar.jobjarapi.domain.models.entities.SkillSnapshot;
import org.jobjar.jobjarapi.persistance.repositories.SkillRepository;
import org.jobjar.jobjarapi.utils.StringNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkillService {
    private final SkillRepository skillRepository;
    private final OfferService offerService;
    private final static Logger log = LoggerFactory.getLogger(SkillService.class);

    public SkillService(SkillRepository skillRepository, OfferService offerService) {
        this.skillRepository = skillRepository;
        this.offerService = offerService;
    }

    public Set<Skill> handleRequiredSkills(OfferCreateDto offer, List<String> requiredSkills) {
        return requiredSkills
                .stream()
                .map(x -> skillRepository
                        .findByVariant(normalizeSkillName(x))
                        .orElseGet(() -> {
                            log.info("No skill {} found", x);
                            // TODO: Add handling to some sort of queue that should be handled later after offer is created
                            handleMissingSkill(x, offer);
                            return null;
                }))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private String normalizeSkillName(String skillName) {
        return StringNormalizer.removePunctuations(skillName.toLowerCase());
    }

    private void handleMissingSkill(String skillName, OfferCreateDto offer) {
        var skillSnapshot = new SkillSnapshot(skillName);

//        offerService.offerEventManager.subscribe(offer, new SkillSnapshotListener(skillSnapshot));
    }
}
