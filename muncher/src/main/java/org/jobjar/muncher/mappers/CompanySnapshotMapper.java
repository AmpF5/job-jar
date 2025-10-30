package org.jobjar.muncher.mappers;


import org.jobjar.muncher.models.dtos.CompanySnapshotCreateDto;
import org.jobjar.muncher.models.entities.CompanySnapshot;

public final class CompanySnapshotMapper {
    public static CompanySnapshot toEntity(CompanySnapshotCreateDto dto) {
        return new CompanySnapshot(dto.getName(), dto.getOfferIds());
    }
}
