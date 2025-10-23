package models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CompanySnapshotCreateDto {
    private String name;
    private Set<UUID> offerIds;
}
