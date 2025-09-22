package org.jobjar.jobjarapi.domain.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.jobjar.jobjarapi.domain.enums.ExperienceLevel;
import org.jobjar.jobjarapi.domain.enums.WorkplaceType;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "offers")
public class Offer {
    @Id
    private UUID offerId;

    private String guid;

    private String title;

    private String slug;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "offer_requiredSkills",
            joinColumns = @JoinColumn(name = "offerId"),
            inverseJoinColumns = @JoinColumn(name = "skillId")
    )
    private Set<Skill> requiredSkills;

    private WorkplaceType workplaceType;

    private ExperienceLevel experienceLevel;

    private Float minimalWage;

    private Float maximalWage;

    private Instant publishedAt;

    private Instant expiredAt;

//    @JoinColumn(name = "company_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Company company;

    public Offer(String guid,
                 String title,
                 String slug,
                 Float minimalWage,
                 Float maximalWage,
                 Instant publishedAt,
                 Instant expiredAt) {
        this.offerId = UUID.randomUUID();
        this.guid = guid;
        this.title = title;
        this.slug = slug;
        this.minimalWage = minimalWage;
        this.maximalWage = maximalWage;
        this.publishedAt = publishedAt;
        this.expiredAt = expiredAt;
    }
}
