package org.jobjar.jobjarapi.domain.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jobjar.jobjarapi.domain.enums.ExperienceLevel;
import org.jobjar.jobjarapi.domain.enums.JobSite;
import org.jobjar.jobjarapi.domain.enums.OfferStatus;
import org.jobjar.jobjarapi.domain.enums.WorkplaceType;

import java.time.Instant;
import java.util.Date;
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
            joinColumns = @JoinColumn(name = "offer_Id"),
            inverseJoinColumns = @JoinColumn(name = "skill_Id")
    )
    private Set<Skill> requiredSkills;

    @Enumerated(EnumType.STRING)
    private WorkplaceType workplaceType;

    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    private JobSite jobSite;

    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;

    private Float minimalWage;

    private Float maximalWage;

    private Date publishedAt;

    private Date expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Offer(String guid,
                 String title,
                 String slug,
                 WorkplaceType workplaceType,
                 ExperienceLevel experienceLevel,
                 Float minimalWage,
                 Float maximalWage,
                 Date publishedAt,
                 Date expiredAt,
                 JobSite jobSite
    ) {
        this.offerId = UUID.randomUUID();
        this.guid = guid;
        this.title = title;
        this.slug = slug;
        this.workplaceType = workplaceType;
        this.experienceLevel = experienceLevel;
        this.minimalWage = minimalWage;
        this.maximalWage = maximalWage;
        this.publishedAt = publishedAt;
        this.expiredAt = expiredAt;
        this.jobSite = jobSite;
    }
}
