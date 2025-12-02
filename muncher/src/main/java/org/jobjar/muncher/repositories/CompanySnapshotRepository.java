package org.jobjar.muncher.repositories;

import org.jobjar.muncher.models.entities.CompanySnapshot;
import org.jobjar.muncher.models.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CompanySnapshotRepository extends JpaRepository<CompanySnapshot, UUID> {
    @Query(value = "SELECT * FROM company_snapshots WHERE name in :companiesNames", nativeQuery = true)
    List<CompanySnapshot> getByCompanySnapshotNames(@Param("companiesNames") Set<String> companiesNames);

    @Query(value = "SELECT * FROM company_snapshots WHERE name in :names", nativeQuery = true)
    List<CompanySnapshot> findAllInNames(@Param("names") Set<String> names);
}
