package models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "companies")
public class Company {
    @Id
    private UUID companyId;

    private String name;

    @OneToMany(mappedBy = "company")
    private Set<Offer> offers;

}
