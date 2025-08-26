package org.jobjar.jobjarapi.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_seq")
    private Long offerId;

    private Float minimalWage;

    private Float maximalWage;

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Company company;

}
