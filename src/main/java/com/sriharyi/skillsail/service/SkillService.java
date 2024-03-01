package com.sriharyi.skillsail.service;

import com.sriharyi.skillsail.dto.QuestionDto;
import com.sriharyi.skillsail.dto.SkillDto;

import java.util.List;

public interface SkillService   {
    SkillDto createSkill(SkillDto skillDto);

    List<SkillDto> getAllSkills();

    SkillDto getSkillById(String id);

    SkillDto updateSkill(String id, SkillDto skillDto);

    void deleteSkill(String id);

    QuestionDto createQuestion(String id, QuestionDto questionDto);
}
