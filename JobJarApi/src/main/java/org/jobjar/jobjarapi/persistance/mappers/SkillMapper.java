package org.jobjar.jobjarapi.persistance.mappers;

import lombok.experimental.ExtensionMethod;
import org.jobjar.jobjarapi.domain.models.entities.Skill;
import org.jobjar.jobjarapi.domain.models.entities.SkillSnapshot;

@ExtensionMethod({Skill.class, SkillSnapshot.class})
public final class SkillMapper {
    public static SkillSnapshot toUnlinkedSkill(String skillName) {
        return new SkillSnapshot(skillName);
    }

    public static Skill toSkill(String skillName) {
        // @TODO: Fix this actual logic
        return new Skill(skillName);
    }


}
