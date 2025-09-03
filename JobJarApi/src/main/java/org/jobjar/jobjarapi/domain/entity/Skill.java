package org.jobjar.jobjarapi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
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

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    private List<String> variants;

    @ManyToMany(mappedBy = "requiredSkills")
    private Set<Offer> offers;

    public Skill(String name) {
        this.name = name;
    }

}