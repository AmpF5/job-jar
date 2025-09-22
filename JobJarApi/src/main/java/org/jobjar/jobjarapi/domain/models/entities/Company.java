package org.jobjar.jobjarapi.domain.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Companies")
public class Company {
    @Id
    private UUID companyId;

}
