package com.sriharyi.skillsail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanTakeTest {
    private String skillId;
    private String freelancerId;
    private String SkillName;
    private boolean canTake;
}
