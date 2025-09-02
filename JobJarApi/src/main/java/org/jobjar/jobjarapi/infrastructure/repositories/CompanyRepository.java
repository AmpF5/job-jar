package org.jobjar.jobjarapi.infrastructure.repositories;

import org.jobjar.jobjarapi.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyId(Long companyId);

}
