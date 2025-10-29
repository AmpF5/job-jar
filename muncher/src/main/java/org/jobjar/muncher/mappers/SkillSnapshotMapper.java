package org.jobjar.muncher.mappers;


import models.dtos.SkillSnapshotCreateDto;
import models.entities.SkillSnapshot;

public final class SkillSnapshotMapper {
    public static SkillSnapshot toEntity(SkillSnapshotCreateDto dto) {
        return new SkillSnapshot(dto.getName(), dto.getOfferIds());
    }
}
