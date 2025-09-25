package org.jobjar.jobjarapi.domain.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "company_snapshots")
public class CompanySnapshot {
    @Id
    @UuidGenerator
    private UUID companySnapshotId;

    private String name;

    private Set<UUID> offerIds;

    public CompanySnapshot(String name, Set<UUID> offerIds) {
        this.name = name;
        this.offerIds = offerIds;
    }
}
