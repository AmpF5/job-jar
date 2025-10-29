package org.jobjar.muncher.mappers;

import lombok.experimental.ExtensionMethod;
import models.entities.Skill;
import models.entities.SkillSnapshot;

@ExtensionMethod({Skill.class, SkillSnapshot.class})
public final class SkillMapper {

    public static Skill toSkill(String skillName) {
        // @TODO: Fix this actual logic
        return new Skill(skillName);
    }


}
