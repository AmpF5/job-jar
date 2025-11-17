package org.jobjar.muncher.repositories;

import org.jobjar.muncher.models.entities.SkillSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SkillSnapshotRepository extends JpaRepository<SkillSnapshot, UUID> {
    @Query(value = "SELECT * FROM skill_snapshots WHERE name in :names", nativeQuery = true)
    List<SkillSnapshot> findByNames(Set<String> names);

    Optional<SkillSnapshot> findByName(String name);
}
