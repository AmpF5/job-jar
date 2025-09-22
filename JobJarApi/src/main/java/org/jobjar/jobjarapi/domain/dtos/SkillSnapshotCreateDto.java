package org.jobjar.jobjarapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillSnapshotCreateDto {
    public String name;
    public Set<UUID> offerIds;
}
