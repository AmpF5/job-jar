package org.jobjar.jobjarapi.persistance.mappers;

import org.jobjar.jobjarapi.domain.dtos.CompanySnapshotCreateDto;
import org.jobjar.jobjarapi.domain.models.entities.CompanySnapshot;

public final class CompanySnapshotMapper {
    public static CompanySnapshot toEntity(CompanySnapshotCreateDto dto) {
        return new CompanySnapshot(dto.getName(), dto.getOfferIds());
    }
}
