package org.jobjar.feeder.persistance.repositories;

import models.entities.SkillSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SkillSnapshotRepository extends JpaRepository<SkillSnapshot, UUID> {
    Optional<SkillSnapshot> findByName(String name);
}
