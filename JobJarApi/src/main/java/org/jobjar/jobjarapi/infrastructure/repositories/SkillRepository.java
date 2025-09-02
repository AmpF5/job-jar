package org.jobjar.jobjarapi.infrastructure.repositories;

import org.jobjar.jobjarapi.domain.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findBySkillId(Long skillId);

    Optional<Skill> findByName(String name);

}
