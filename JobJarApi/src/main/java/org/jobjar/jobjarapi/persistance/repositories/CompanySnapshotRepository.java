package org.jobjar.jobjarapi.persistance.repositories;

import org.jobjar.jobjarapi.domain.models.entities.CompanySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanySnapshotRepository extends JpaRepository<CompanySnapshot, UUID> {

}
