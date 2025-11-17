package org.jobjar.muncher.repositories;

import org.jobjar.muncher.models.entities.CompanySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CompanySnapshotRepository extends JpaRepository<CompanySnapshot, UUID> {
    @Query(value = "SELECT * FROM company_snapshots WHERE name in :companiesNames", nativeQuery = true)
    List<CompanySnapshot> getByCompanySnapshotNames(Set<String> companiesNames);

}
