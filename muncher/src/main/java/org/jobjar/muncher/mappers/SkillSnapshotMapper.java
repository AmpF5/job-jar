package org.jobjar.muncher.mappers;


import org.jobjar.muncher.models.dtos.SkillSnapshotCreateDto;
import org.jobjar.muncher.models.entities.SkillSnapshot;

public final class SkillSnapshotMapper {
    public static SkillSnapshot toEntity(SkillSnapshotCreateDto dto) {
        return new SkillSnapshot(dto.getName(), dto.getOfferIds());
    }
}
