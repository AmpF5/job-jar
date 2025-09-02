package org.jobjar.jobjarapi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_seq")
    private Long skillId;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "requiredSkills")
    private Set<Offer> offers;

    public Skill(String name) {
        this.name = name;
    }

}