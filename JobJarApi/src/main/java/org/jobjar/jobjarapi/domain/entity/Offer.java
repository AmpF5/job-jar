package org.jobjar.jobjarapi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_seq")
    private Long offerId;

    private String guid;

    private String title;

    private String slug;

    @ManyToMany
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

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Company company;

    public Offer(String guid,
                 String title,
                 String slug,
                 Float minimalWage,
                 Float maximalWage,
                 Instant publishedAt,
                 Instant expiredAt) {
        this.guid = guid;
        this.title = title;
        this.slug = slug;
        this.minimalWage = minimalWage;
        this.maximalWage = maximalWage;
        this.publishedAt = publishedAt;
        this.expiredAt = expiredAt;
    }
}
