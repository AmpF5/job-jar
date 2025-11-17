package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobjar.muncher.mappers.CompanySnapshotMapper;
import org.jobjar.muncher.models.dtos.CompanySnapshotCreateDto;
import org.jobjar.muncher.models.entities.CompanySnapshot;
import org.jobjar.muncher.repositories.CompanySnapshotRepository;
import org.jobjar.muncher.utils.TimeConverter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanySnapshotService {
    private final CompanySnapshotRepository companySnapshotRepository;

    public void handleCompaniesSnapshots(HashMap<String, Set<UUID>> companiesSnapshots) {
        var start = System.nanoTime();

        var existingCompaniesSnapshots = companySnapshotRepository.getByCompanySnapshotNames(companiesSnapshots.keySet()).stream().collect(Collectors.toMap(CompanySnapshot::getName, x -> x));

        var companySnapshotsToAdd = new ArrayList<CompanySnapshotCreateDto>();

        for (var companySnapshot : companiesSnapshots.entrySet()) {
            var existingCompanySnapshot = existingCompaniesSnapshots.get(companySnapshot.getKey());
            if (existingCompanySnapshot != null) {
                existingCompanySnapshot.addOfferIds(companySnapshot.getValue());
            } else {
                companySnapshotsToAdd.add(new CompanySnapshotCreateDto(companySnapshot.getKey(), companySnapshot.getValue()));
            }
        }

        bulkSaveCompanySnapshots(companySnapshotsToAdd);

        companySnapshotRepository.flush();

        var end = System.nanoTime();
        log.info("Added {} company snapshot in {}ms .", companySnapshotsToAdd.size(), TimeConverter.getElapsedTime(start, end));
    }

    public void bulkSaveCompanySnapshots(List<CompanySnapshotCreateDto> snapshots) {
        companySnapshotRepository
                .saveAll(snapshots
                        .stream()
                        .map(CompanySnapshotMapper::toEntity)
                        .toList());
    }
}
