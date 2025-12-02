package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobjar.muncher.mappers.OfferMapper;
import org.jobjar.muncher.models.dtos.OfferCreateDto;
import org.jobjar.muncher.models.entities.*;
import org.jobjar.muncher.repositories.OfferRepository;
import org.jobjar.muncher.utils.TimeConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OfferService {
    private final OfferRepository offerRepository;
    private final SkillService skillService;
    private final CompanyService companyService;
    private final SkillSnapshotService skillSnapshotService;
    private final CompanySnapshotService companySnapshotService;

    public void handleOffers(List<OfferCreateDto> offers) {
        var existingOffers = offerRepository
                .findByExternalIds(offers
                        .stream()
                        .map(x -> UUID.fromString(x.externalId()))
                        .collect(Collectors.toSet()))
                .stream()
                .collect(Collectors.toMap(Offer::getExternalId, x -> x));

        var newOffersToAdd = new ArrayList<OfferCreateDto>();

        for (var newOffer : offers) {
            var existingOffer = existingOffers.get(UUID.fromString(newOffer.externalId()));
            if (existingOffer != null) {
                OfferMapper.updateOffer(existingOffer, newOffer);
            } else {
                newOffersToAdd.add(newOffer);
            }
        }

        bulkSaveOffers(newOffersToAdd);
    }

    public void bulkSaveOffers(List<OfferCreateDto> offers) {
        var companiesFromOffers = new HashSet<String>();
        var skillsFromOffers = new HashSet<String>();

        offers.forEach(x -> {
            companiesFromOffers.add(x.companyName());
            skillsFromOffers.addAll(x.requiredSkills());
        });

        var start = System.nanoTime();

        // Get existing Skills and Companies
        var existingSkills = skillService.getSkills(skillsFromOffers);
        var existingSkillsLookup = existingSkills.stream()
                .map(Skill::getName)
                .collect(Collectors.toSet());

        var existingCompanies= companyService.getCompanies(companiesFromOffers);
        var existingCompaniesLookup = existingCompanies.stream()
                .map(Company::getName)
                .collect(Collectors.toSet());

        // Get existing SkillsSnapshot
        var skillSnapshots = skillsFromOffers.stream()
                .filter(x -> !existingSkillsLookup.contains(x))
                .collect(Collectors.toSet());

        var existingSkillsSnapshot = skillSnapshotService.getSkillSnapshotsByNames(skillSnapshots);
        var existingSkillsSnapshotsLookup = existingSkillsSnapshot.stream()
                .map(SkillSnapshot::getName)
                .collect(Collectors.toSet());

        // Get existing CompaniesSnapshot
        var companiesSnapshots = companiesFromOffers.stream()
                .filter(x -> !existingCompaniesLookup.contains(x))
                .collect(Collectors.toSet());

        var existingCompaniesSnapshot = companySnapshotService.findAllInNames(companiesSnapshots);
        var existingCompaniesSnapshotsLookup = existingCompaniesSnapshot.stream()
                .map(CompanySnapshot::getName)
                .collect(Collectors.toSet());

        // Create new SkillsSnapshot that aren't present in Skills nor SkillSnapshots
        var skillSnapshotsToAdd = skillsFromOffers.stream()
                .filter(x -> !existingSkillsLookup.contains(x)
                        && !existingSkillsSnapshotsLookup.contains(x))
                .collect(Collectors.toMap(x -> x, x -> new HashSet<UUID>()));

        // Create new CompaniesSnapshot that aren't present in Skills nor SkillSnapshots
        var companySnapshotsToAdd = companiesFromOffers.stream()
                .filter(x -> !existingCompaniesLookup.contains(x)
                        && !existingCompaniesSnapshotsLookup.contains(x))
                .collect(Collectors.toMap(x -> x, x -> new HashSet<UUID>()));

        // Handle adding new Offer and associated data
        offerRepository.saveAll(offers.stream().map(offerCreateDto -> {
            var offer = OfferMapper.toEntity(offerCreateDto);

            // Handle assigning Offer's Skills to one of
            // - Already existing Skills
            // - Already existing SkillsSnapshot
            // - New SkillsSnapshot not present in database yet
            offerCreateDto.requiredSkills().forEach(offerSkill -> {
              if(existingSkillsLookup.contains(offerSkill)) {
                existingSkills.stream()
                        .filter(x -> Objects.equals(offerSkill, x.getName()))
                        .findFirst()
                        .ifPresent(offer::addRequiredSkill);
              } else if (existingSkillsSnapshotsLookup.contains(offerSkill)) {
                  existingSkillsSnapshot.stream()
                          .filter(x -> Objects.equals(offerSkill, x.getName()))
                          .findFirst()
                          .ifPresent(x -> x.appendNewOfferId(offer.getOfferId()));
              } else if (skillSnapshotsToAdd.containsKey(offerSkill)) {
                  skillSnapshotsToAdd.get(offerSkill).add(offer.getOfferId());
              } else {
                  log.error("Cannot find {} skill in any lists", offerSkill);
              }
            });

            // Handle assigning Offer's company to one of
            // - Already existing Companies
            // - Already existing CompanySnapshot
            // - New CompaniesSnapshot not present in database yet
            var company = existingCompanies.stream()
                    .filter(y -> offerCreateDto
                            .companyName()
                            .equals(y.getName()))
                    .findFirst();

            if(company.isPresent()) {
               offer.setCompany(company.get());
            } else if (existingCompaniesSnapshotsLookup.contains(offerCreateDto.companyName())) {
                existingCompaniesSnapshot.stream()
                        .filter(x -> Objects.equals(x.getName(), offerCreateDto.companyName()))
                        .findFirst()
                        .ifPresent(x -> x.addOfferId(offer.getOfferId()));
            } else if (companySnapshotsToAdd.containsKey(offerCreateDto.companyName())) {
                companySnapshotsToAdd.get(offerCreateDto.companyName()).add(offer.getOfferId());
            } else {
                log.error("Cannot find {} company in any lists", offerCreateDto.companyName());
            }

            return offer;
        }).toList());

        var end = System.nanoTime();

        log.info("Inserted {} offers in {} ms.", offers.size(), TimeConverter.getElapsedTime(start, end));

        // Add newly founded SkillsSnapshots
        skillSnapshotService.handleSkillSnapshots(skillSnapshotsToAdd);

        // Add newly founded CompaniesSnapshots
        companySnapshotService.handleCompaniesSnapshots(companySnapshotsToAdd);
    }
}
