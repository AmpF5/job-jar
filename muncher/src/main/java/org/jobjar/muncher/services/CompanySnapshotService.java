package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobjar.muncher.mappers.CompanySnapshotMapper;
import org.jobjar.muncher.models.dtos.CompanySnapshotCreateDto;
import org.jobjar.muncher.repositories.CompanySnapshotRepository;
import org.jobjar.muncher.utils.TimeConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanySnapshotService {
    private final CompanySnapshotRepository companySnapshotRepository;

    public void bulkSaveCompanySnapshots(List<CompanySnapshotCreateDto> snapshots) {
        var start = System.nanoTime();

        companySnapshotRepository
                .saveAllAndFlush(snapshots
                        .stream()
                        .map(CompanySnapshotMapper::toEntity)
                        .toList());

        var end = System.nanoTime();
        log.info("Added {} company snapshot in {}ms .", snapshots.size(), TimeConverter.getElapsedTime(start, end));
    }
}
