package org.jobjar.jobjarapi.persistance.services;

import lombok.RequiredArgsConstructor;
import org.jobjar.jobjarapi.domain.dtos.CompanySnapshotCreateDto;
import org.jobjar.jobjarapi.domain.models.entities.CompanySnapshot;
import org.jobjar.jobjarapi.persistance.mappers.CompanySnapshotMapper;
import org.jobjar.jobjarapi.persistance.repositories.CompanySnapshotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompanySnapshotService {
    private final CompanySnapshotRepository companySnapshotRepository;

    public void bulkSaveCompanySnapshots(List<CompanySnapshotCreateDto> snapshots) {
        companySnapshotRepository
                .saveAllAndFlush(snapshots
                        .stream()
                        .map(CompanySnapshotMapper::toEntity)
                        .toList());
    }
}
