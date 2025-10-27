package org.jobjar.feeder.persistance.repositories;

import models.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {
    Optional<Skill> findBySkillId(UUID skillId);

    Optional<Skill> findByName(String name);

    @Query(value = "SELECT * FROM skills WHERE :variant = Any (variants) LIMIT 1", nativeQuery = true)
    Optional<Skill> findByVariant(@Param("variant") String variant);

}
