package org.jobjar.muncher.mappers;

import lombok.experimental.ExtensionMethod;
import org.jobjar.muncher.models.entities.Skill;
import org.jobjar.muncher.models.entities.SkillSnapshot;

@ExtensionMethod({Skill.class, SkillSnapshot.class})
public final class SkillMapper {

    public static Skill toSkill(String skillName) {
        // @TODO: Fix this actual logic
        return new Skill(skillName);
    }


}
