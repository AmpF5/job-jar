package org.jobjar.muncher.repositories;


import org.jobjar.muncher.models.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByCompanyId(UUID companyId);

    Optional<Company> findByName(String name);

    @Query(value = "SELECT * FROM companies WHERE name IN :names", nativeQuery = true)
    List<Company> findAll(Set<String> names);


}
