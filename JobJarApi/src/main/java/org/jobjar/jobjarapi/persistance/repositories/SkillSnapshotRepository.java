package org.jobjar.jobjarapi.persistance.repositories;

import org.jobjar.jobjarapi.domain.models.entities.SkillSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SkillSnapshotRepository extends JpaRepository<SkillSnapshot, UUID> {
   Optional<SkillSnapshot> findByName(String name);
}
