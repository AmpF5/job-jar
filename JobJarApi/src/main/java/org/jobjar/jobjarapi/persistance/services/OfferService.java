package org.jobjar.jobjarapi.persistance.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;
import org.jobjar.jobjarapi.domain.dtos.SkillSnapshotCreateDto;
import org.jobjar.jobjarapi.persistance.mappers.OfferMapper;
import org.jobjar.jobjarapi.persistance.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final SkillService skillService;
    private final SkillSnapshotService skillSnapshotService;

    @Transactional
    public void bulkSaveOffers(List<OfferCreateDto> offers) {
        // TODO: Take off limiter
        offers = offers.stream().limit(10).toList();
        var skillSnapshots = new HashMap<String, Set<UUID>>();

        offerRepository.saveAllAndFlush(offers.stream().map(x -> {
            // Handling offer data
            var offer = OfferMapper.toEntity(x);
            var skillsAndSnapshots = skillService.getRequiredSkillsAndSnapshots(x);
            offer.setRequiredSkills(skillsAndSnapshots.t1());

            // Handling snapshots
            skillsAndSnapshots.t2().forEach(y -> {
                if (skillSnapshots.containsKey(y)) {
                    skillSnapshots.get(y).add(offer.getOfferId());
                } else {
                    skillSnapshots.put(y, new HashSet<>(List.of(offer.getOfferId())));
                }
            });

            return offer;
        }).toList());

        skillSnapshotService
                .bulkSaveSkillSnapshots(skillSnapshots
                        .entrySet()
                        .stream()
                        .map(x -> new SkillSnapshotCreateDto(x.getKey(), x.getValue()))
                        .toList());
    }
}
