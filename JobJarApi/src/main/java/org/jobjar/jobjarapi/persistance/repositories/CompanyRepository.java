package org.jobjar.jobjarapi.persistance.repositories;

import org.jobjar.jobjarapi.domain.models.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByCompanyId(UUID companyId);

}
