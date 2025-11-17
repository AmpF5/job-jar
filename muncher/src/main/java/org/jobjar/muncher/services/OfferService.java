package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobjar.muncher.mappers.OfferMapper;
import org.jobjar.muncher.models.dtos.CompanySnapshotCreateDto;
import org.jobjar.muncher.models.dtos.OfferCreateDto;
import org.jobjar.muncher.models.dtos.SkillSnapshotCreateDto;
import org.jobjar.muncher.models.entities.Offer;
import org.jobjar.muncher.repositories.OfferRepository;
import org.jobjar.muncher.utils.TimeConverter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final SkillService skillService;
    private final CompanyService companyService;
    private final SkillSnapshotService skillSnapshotService;
    private final CompanySnapshotService companySnapshotService;

    public void handleOffers(List<OfferCreateDto> offers) {
        var allNewOffers = offers.stream().collect(Collectors.toMap(x -> UUID.fromString(x.externalId()), x -> x));
        var existingOffers = offerRepository.findByExternalIds(allNewOffers.keySet()).stream().collect(Collectors.toMap(Offer::getExternalId, x -> x));

        var newOffersToAdd = new ArrayList<OfferCreateDto>();

        for (var newOffer : allNewOffers.entrySet()) {
            var existingOffer = existingOffers.get(newOffer.getKey());
            if (existingOffer != null) {
                OfferMapper.updateOffer(existingOffer, newOffer.getValue());
            } else {
                newOffersToAdd.add(newOffer.getValue());
            }
        }

        bulkSaveOffers(newOffersToAdd);

        offerRepository.flush();
    }

    public void bulkSaveOffers(List<OfferCreateDto> offers) {
        var start = System.nanoTime();
        // TODO: Take off limiter
//        offers = offers.stream().limit(10).toList();
        var skillSnapshots = new HashMap<String, Set<UUID>>();
        var companiesSnapshots = new HashMap<String, Set<UUID>>();

        offerRepository.saveAll(offers.stream().map(x -> {
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
            var company = companyService.getCompanyByName(x.companyName());
            company.ifPresentOrElse(
                    offer::setCompany,
                    () -> companiesSnapshots
                            .computeIfAbsent(x.companyName(), v -> new HashSet<>())
                            .add(offer.getOfferId()));

            return offer;
        }).toList());

        var end = System.nanoTime();

        log.info("Inserted {} offers in {} ms.", offers.size(), TimeConverter.getElapsedTime(start, end));

        skillSnapshotService.handleSkillSnapshots(skillSnapshots);

        companySnapshotService.handleCompaniesSnapshots(companiesSnapshots);
    }
}
