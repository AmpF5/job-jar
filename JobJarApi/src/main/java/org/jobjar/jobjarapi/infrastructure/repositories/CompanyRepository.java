package org.jobjar.jobjarapi.infrastructure.repositories;

import org.jobjar.jobjarapi.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {


    List<Company> getAll();
    Optional<Company> findByCompanyId(Long companyId);

}
