package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobjar.muncher.models.dtos.OfferCreateDto;
import org.jobjar.muncher.models.entities.Company;
import org.jobjar.muncher.repositories.CompanyRepository;
import org.jobjar.muncher.utils.TimeConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Optional<Company> getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }

    public List<Company> getCompanies(Set<String> companiesName) {
        var start = System.nanoTime();

        var existingCompanies = companyRepository.findAll(companiesName);

        var end =  System.nanoTime();
        log.info("Found all companies in {} ms", TimeConverter.getElapsedTime(start, end));

        return existingCompanies;
    }
}
