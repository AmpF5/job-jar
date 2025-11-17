package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobjar.muncher.mappers.SkillSnapshotMapper;
import org.jobjar.muncher.models.dtos.SkillSnapshotCreateDto;
import org.jobjar.muncher.models.entities.SkillSnapshot;
import org.jobjar.muncher.repositories.SkillSnapshotRepository;
import org.jobjar.muncher.utils.TimeConverter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkillSnapshotService {
    private final SkillSnapshotRepository skillSnapshotRepository;

    public void handleSkillSnapshots(HashMap<String, Set<UUID>> skillSnapshots) {
        var start = System.nanoTime();

        var existingSkillSnapshots = skillSnapshotRepository.findByNames(skillSnapshots.keySet()).stream().collect(Collectors.toMap(SkillSnapshot::getName, x -> x));

        var skillSnapshotsToAdd = new ArrayList<SkillSnapshotCreateDto>();

        for (var skillSnapshot : skillSnapshots.entrySet()) {
            var existingSkillSnapshot = existingSkillSnapshots.get(skillSnapshot.getKey());
            if (existingSkillSnapshot != null) {
                existingSkillSnapshot.appendNewOfferIds(skillSnapshot.getValue());
            } else {
                skillSnapshotsToAdd.add(new SkillSnapshotCreateDto(skillSnapshot.getKey(), skillSnapshot.getValue()));
            }
        }

        bulkSaveSkillSnapshots(skillSnapshotsToAdd);

        skillSnapshotRepository.flush();

        var end = System.nanoTime();
        log.info("Added {} skill snapshot in {} ms.", skillSnapshotsToAdd.size(), TimeConverter.getElapsedTime(start, end));
    }

    public void bulkSaveSkillSnapshots(List<SkillSnapshotCreateDto> skillSnapshots) {
        skillSnapshotRepository
                .saveAll(skillSnapshots
                        .stream()
                        .map(SkillSnapshotMapper::toEntity)
                        .toList());
    }
}
