package org.jobjar.jobjarapi.persistance.services;

import lombok.RequiredArgsConstructor;
import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;
import org.jobjar.jobjarapi.domain.models.entities.Skill;
import org.jobjar.jobjarapi.domain.models.generics.Tuple;
import org.jobjar.jobjarapi.persistance.repositories.SkillRepository;
import org.jobjar.jobjarapi.persistance.repositories.SkillSnapshotRepository;
import org.jobjar.jobjarapi.utils.StringNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final static Logger log = LoggerFactory.getLogger(SkillService.class);
    private final SkillRepository skillRepository;

    public Tuple<Set<Skill>, Set<String>> getRequiredSkillsAndSnapshots(OfferCreateDto offerCreateDto) {
        var snapshots = new HashSet<String>();
        var requiredSkills = offerCreateDto.getRequiredSkills()
                .stream()
                .map(x -> skillRepository
                        .findByVariant(normalizeSkillName(x))
                        .orElseGet(() -> {
                            log.info("No skill {} found", x);
                            snapshots.add(x);
                            return null;
                        }))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return new Tuple<>(requiredSkills, snapshots);
    }

    private String normalizeSkillName(String skillName) {
        return StringNormalizer.removePunctuations(skillName.toLowerCase());
    }
}
