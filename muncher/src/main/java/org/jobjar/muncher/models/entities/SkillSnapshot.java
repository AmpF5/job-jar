package org.jobjar.muncher.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    public SkillSnapshot(String name, Set<UUID> offerIds) {
        this.skillSnapshotId = UUID.randomUUID();
        this.name = name;
        this.offerIds = offerIds;
    }

    public void appendNewOfferId(UUID offerId) {
        this.offerIds.add(offerId);
    }

    public void appendNewOfferIds(Set<UUID> offerIds) {
        this.offerIds.addAll(offerIds);
    }
}
