package org.jobjar.jobjarapi.persistance.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobjar.jobjarapi.domain.dtos.CompanySnapshotCreateDto;
import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;
import org.jobjar.jobjarapi.domain.dtos.SkillSnapshotCreateDto;
import org.jobjar.jobjarapi.persistance.mappers.OfferMapper;
import org.jobjar.jobjarapi.persistance.repositories.CompanyRepository;
import org.jobjar.jobjarapi.persistance.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final SkillService skillService;
    private final SkillSnapshotService skillSnapshotService;
    private final CompanyRepository companyRepository;
    private final CompanySnapshotService companySnapshotService;

    @Transactional
    public void bulkSaveOffers(List<OfferCreateDto> offers) {
        // TODO: Take off limiter
        offers = offers.stream().limit(10).toList();
        var skillSnapshots = new HashMap<String, Set<UUID>>();
        var companiesSnapshots = new HashMap<String, Set<UUID>>();

        offerRepository.saveAllAndFlush(offers.stream().map(x -> {
            // Handling offer data
            var offer = OfferMapper.toEntity(x);
            var skillsAndSnapshots = skillService.getRequiredSkillsAndSnapshots(x);
            offer.setRequiredSkills(skillsAndSnapshots.t1());

            // Handling snapshots
            // Skills
            skillsAndSnapshots
                    .t2()
                    .forEach(y -> skillSnapshots
                            .computeIfAbsent(y, v -> new HashSet<>())
                            .add(offer.getOfferId()));
            // Company
            var company = companyRepository.findByName(x.getCompanyName());
            company.ifPresentOrElse(
                    offer::setCompany,
                    () -> companiesSnapshots
                            .computeIfAbsent(x.getCompanyName(), v -> new HashSet<>())
                            .add(offer.getOfferId()));

            return offer;
        }).toList());

        skillSnapshotService
                .bulkSaveSkillSnapshots(skillSnapshots
                        .entrySet()
                        .stream()
                        .map(x -> new SkillSnapshotCreateDto(x.getKey(), x.getValue()))
                        .toList());

        companySnapshotService
                .bulkSaveCompanySnapshots(companiesSnapshots
                        .entrySet()
                        .stream()
                        .map(x -> new CompanySnapshotCreateDto(x.getKey(), x.getValue()))
                        .toList());
    }
}
