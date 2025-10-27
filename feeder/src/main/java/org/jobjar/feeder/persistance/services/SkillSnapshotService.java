package org.jobjar.feeder.persistance.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mappers.SkillSnapshotMapper;
import models.dtos.SkillSnapshotCreateDto;
import org.jobjar.feeder.persistance.repositories.SkillSnapshotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkillSnapshotService {
    private final SkillSnapshotRepository skillSnapshotRepository;

    @Transactional
    public void bulkSaveSkillSnapshots(List<SkillSnapshotCreateDto> skillSnapshots) {
        skillSnapshotRepository
                .saveAllAndFlush(skillSnapshots
                        .stream()
                        .map(SkillSnapshotMapper::toEntity)
                        .toList());
        log.info("Added {} skill snapshot to the repository", skillSnapshots.size());
    }
}
