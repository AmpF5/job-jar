package org.jobjar.jobjarapi.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jobjar.jobjarapi.domain.responses.JustJoinItResponse;

import java.time.Instant;
import java.util.List;

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

    private List<Skill> requiredSkills;

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
