package org.jobjar.muncher.repositories;

import org.jobjar.muncher.models.entities.CompanySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanySnapshotRepository extends JpaRepository<CompanySnapshot, UUID> {

}
