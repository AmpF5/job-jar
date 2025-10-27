package models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.enums.ExperienceLevel;
import models.enums.JobSite;
import models.enums.OfferStatus;
import models.enums.WorkplaceType;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "offers")
public class Offer {
    @Id
    private final UUID offerId;

    private final String guid;

    private final String title;

    private final String slug;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "offer_requiredSkills",
            joinColumns = @JoinColumn(name = "offer_Id"),
            inverseJoinColumns = @JoinColumn(name = "skill_Id")
    )
    private Set<Skill> requiredSkills;

    @Enumerated(EnumType.STRING)
    private final WorkplaceType workplaceType;

    @Enumerated(EnumType.STRING)
    private final ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    private final JobSite jobSite;

    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;

    private final Float minimalWage;

    private final Float maximalWage;

    private final Date publishedAt;

    private final Date expiredAt;

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
