package models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "skills")
public class Skill {

    @Id
    private UUID skillId;

    @Column(unique = true)
    private String name;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    private List<String> variants;

    @ManyToMany(mappedBy = "requiredSkills", fetch = FetchType.LAZY)
    private Set<Offer> offers;

    public Skill(String name) {
        this.name = name;
    }

}