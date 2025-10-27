package org.jobjar.feeder.persistance.repositories;


import models.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByCompanyId(UUID companyId);

    Optional<Company> findByName(String name);
}
