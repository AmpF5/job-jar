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
@Table(name = "company_snapshots")
public class CompanySnapshot {
    @Id
    private UUID companySnapshotId;

    private String name;

    private Set<UUID> offerIds;

    public CompanySnapshot(String name, Set<UUID> offerIds) {
        this.companySnapshotId = UUID.randomUUID();
        this.name = name;
        this.offerIds = offerIds;
    }

    public void addOfferId(UUID offerId) {
        this.offerIds.add(offerId);
    }

    public void addOfferIds(Set<UUID> offerIds) {
        this.offerIds.addAll(offerIds);
    }
}
