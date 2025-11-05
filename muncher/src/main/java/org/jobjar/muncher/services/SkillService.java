package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import org.jobjar.muncher.models.dtos.OfferCreateDto;
import org.jobjar.muncher.models.entities.Skill;
import org.jobjar.muncher.models.generics.Tuple;
import org.jobjar.muncher.repositories.SkillRepository;
import org.jobjar.muncher.utils.StringNormalizer;
import org.jobjar.muncher.utils.TimeConverter;
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
    private final static Logger logger = LoggerFactory.getLogger(SkillService.class);
    private final SkillRepository skillRepository;

    public Tuple<Set<Skill>, Set<String>> getRequiredSkillsAndSnapshots(OfferCreateDto offerCreateDto) {
        var snapshots = new HashSet<String>();
        var start = System.nanoTime();
        var requiredSkills = offerCreateDto.requiredSkills()
                .stream()
                .map(x -> skillRepository
                        .findByVariant(normalizeSkillName(x))
                        .orElseGet(() -> {
                            logger.info("No skill {} found", x);
                            snapshots.add(x);
                            return null;
                        }))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        var end = System.nanoTime();
        logger.info("Required skills and snapshots took {} ms", TimeConverter.getElapsedTime(start, end));

        return new Tuple<>(requiredSkills, snapshots);
    }

    private String normalizeSkillName(String skillName) {
        return StringNormalizer.removePunctuations(skillName.toLowerCase());
    }
}
