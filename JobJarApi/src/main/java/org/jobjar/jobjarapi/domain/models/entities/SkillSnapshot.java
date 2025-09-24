package org.jobjar.jobjarapi.domain.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "skill_snapshots")
public class SkillSnapshot {
    @Id
    private UUID skillSnapshotId;

    private String name;

    private Set<UUID> offerIds;

    public SkillSnapshot(String name,  Set<UUID> offerIds) {
        this.skillSnapshotId = UUID.randomUUID();
        this.name = name;
        this.offerIds = offerIds;
    }
}
