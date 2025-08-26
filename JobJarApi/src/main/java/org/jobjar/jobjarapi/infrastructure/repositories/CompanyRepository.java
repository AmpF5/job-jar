package org.jobjar.jobjarapi.infrastructure.repositories;

import org.jobjar.jobjarapi.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
