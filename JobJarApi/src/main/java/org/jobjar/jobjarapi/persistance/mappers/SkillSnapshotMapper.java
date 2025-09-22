package org.jobjar.jobjarapi.persistance.mappers;


import org.jobjar.jobjarapi.domain.dtos.SkillSnapshotCreateDto;
import org.jobjar.jobjarapi.domain.models.entities.SkillSnapshot;

public final class SkillSnapshotMapper {
    public static SkillSnapshot toEntity(SkillSnapshotCreateDto dto) {
        return new SkillSnapshot(dto.getName(), dto.getOfferIds());
    }
}
