package mappers;

import models.dtos.CompanySnapshotCreateDto;
import models.entities.CompanySnapshot;

public final class CompanySnapshotMapper {
    public static CompanySnapshot toEntity(CompanySnapshotCreateDto dto) {
        return new CompanySnapshot(dto.getName(), dto.getOfferIds());
    }
}
