package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobjar.muncher.mappers.CompanySnapshotMapper;
import org.jobjar.muncher.models.dtos.CompanySnapshotCreateDto;
import org.jobjar.muncher.repositories.CompanySnapshotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanySnapshotService {
    private final CompanySnapshotRepository companySnapshotRepository;

    public void bulkSaveCompanySnapshots(List<CompanySnapshotCreateDto> snapshots) {
        companySnapshotRepository
                .saveAllAndFlush(snapshots
                        .stream()
                        .map(CompanySnapshotMapper::toEntity)
                        .toList());
        log.info("Added {} company snapshot", snapshots.size());
    }
}
